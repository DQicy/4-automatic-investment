# 4-automatic-investment

选取上证50（沪深300的缩略版）和中证500，分别选取与大盘指数偏差在5%的lof进行跟踪，

长期持有

eg:

(1)选取A类联接天弘上证50指数A（001548）规模都在5亿以上

(2)选取A类联接南方中证500ETF联接（LOF）A 160119 —> 跟踪误差为0.13



操作流程：

根据等权市盈率PE判断：

<40 低位 批量买入

> 60高位 --> 立即停止

上证50 TTM(滚动市盈率)url:https://legulegu.com/stockdata/sz50-ttm-lyr

中证500 TTM url:https://legulegu.com/stockdata/zz500-ttm-lyr

原则：4% automatic investment

建仓期每当从上一个最低点单次或累计下跌超过4%，就进行一次定投(即：按照净值 * 0.96计算下一个买入点) --> 项目中选取2：50分consider净值估算 as 净值（bias在0.5%左右）--> 将两个指数的净值存到memcache中

在持有期向上脱离成本区间之后，从高点单次或累计下跌4%，重复上述



代码流程：

前提条件： 资金总量N，batch批次为10 线程1

首次买入：

（1）爬取指数的PE，小于40加大定投筹码 N / 10 * (1 + 10%)；40 ~ 55按内心预期持续定投 N / 10；大于60撤销一切操作；

（2）若PE（滚动市盈率）在55以下，当天3点前直接买入；

（3）在下午2：50分爬取url记录净值估算，作为当前净值；维护一个standard1变量；维护一个standard2变量，初始为0（代表脱离成本区间15%以上的高位基准点，初始化为0）

（4）记录首次买入成本，维护cost变量；

days later:(买入时机判断) 线程1

（5）抓取指数PE，若> 60，存在高位泡沫风险，禁止高抛低吸，停止定投，并通过邮件接口给出告警，break；

(6) 判断standard2 == 0

if 等于0：

#执行如下逻辑

若PE < 60, 且cur < cost * (1 + 15%)

分情况讨论：

if 在下午2：50 爬取数据cur 单次或累计下跌超过4%(standard * (1 - 4%) > cur)，发送邮件告知，立即通过蚂蚁财富买入；并在3：00更新standard变量值(standard = cur)；
2. else （可能是下跌不足4%或上涨不到15%）

不做操作， 根据情况决定是否更新standard值（standard = Math.min(standard, cur)

若PE < 60， 且cur > cost * (1 + 15%)

记录新的高位 standard2 = cur;else

#standard2 != 0

继续执行4% investment策略

若PE < 60，且在下午2：50 爬取数据cur 单次或累计下跌超过4%(standard2 * (1 - 4%) > cur)，发送邮件告知，立即通过蚂蚁财富买入；并在3：00更新standard2变量值(standard2 = cur)；

#追加判断cur是否已经低于15%范围

if cur < (1 + 15%) * cost

#以后都不操作了

else

standard2 = cur



days later:（卖出时机判断）线程2

(1)设置止盈threshold = 10% ，防止贪心

(2)情况一：一旦超过成本区间 10%，分批卖出（默认分5次）

if cur > (1 + 10%) * cost

卖出一部分，发邮件告知，立即通过蚂蚁财富卖出一定份额；

情况二：PE > 60， 即刻卖出



设计技术：Java,WebLogic, 定时任务，HTTP，多线程， 磁盘IO， Shell脚本

整体流程：

定时任务程序dingshi（每天定时调用） ---> 调用shell脚本（shell脚本去调上证50净值估算，中证500净值估算，上证50PE， 中证500PE）
---> 在dingshi程序内部执行计算逻辑 --> 调用javaMailSender进行邮件告知

Derby数据表设计：stockDB数据库
两个表：
（1）PE表
CREATE TABLE pe_total (
   Id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   Name VARCHAR(255),  //指数名称
   Value INT NOT NULL,  //指数PE值
   time VARCHAR(255),    //时间（暂定yyyy-MM-dd，还没处理）
   PRIMARY KEY (Id)
);

(2)EX表
CREATE TABLE ex_total (
   Id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   Name VARCHAR(255),  //指数名称
   Value INT NOT NULL,  //指数净值估算
   time VARCHAR(255),    //时间（暂定yyyy-MM-dd，还没处理），
   cost INT NOT NULL, //买入成本（只有第一次买入次才会赋值）
   standard1 INT NOT NULL, //变量值（低位参考值，第二天会更新） 
   standard2 INT NOT NULL, //变量值（高位参考值， 脱离成本区间15%会更新，否则为0）
   PRIMARY KEY (Id)
);
