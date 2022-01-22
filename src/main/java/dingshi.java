import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//4% automatic investment
public class dingshi {

    //存储不同指数对应程序的classname
    //ArrayList初始化方式：https://blog.csdn.net/u011523796/article/details/79537055
     static List<String> classname = new ArrayList<String>(){
        {
            add("crawl.shangzheng50");
            add("zhonzgheng500");
        }
    };
    //调用shell脚本参考：https://www.jianshu.com/p/462647582abe
     private static String SHELL_FILE_DIR = "Users/apple/Desktop/fund-demo/";

    public static void main(String[] args) throws ParseException, IOException, InterruptedException {
        String bashCommand = "chmod 777 /Users/apple/Desktop/fund-demo/text.sh";  //①
        Runtime runtime = Runtime.getRuntime();
        Process pro = runtime.exec(bashCommand);  //②
        int status = pro.waitFor();  //③
        if (status != 0){  //④
            System.out.println("执行脚本出错");
            return;
        }
        System.out.println("执行脚本成功");

        System.out.println("----------");
        System.out.println("开始分析...");


//        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);
//        for (int i = 0; i < classname.size(); ++i) {
//            executor.schedule(new Runnable() {
//                @Override
//                public void run() {
////                    System.out.println(Thread.currentThread().getName() + " run ");
//                    //各个指数依次爬取
//                    //调用脚本进行处理
//
//                    ProcessBuilder pb = new ProcessBuilder("./" + "game");
//                    pb.directory(new File(SHELL_FILE_DIR));
//                    int runningStatus = 0;
//                    String s = null;
//                    try {
//                        Process p = pb.start();
//                        try {
//                            runningStatus = p.waitFor();
//                        } catch (InterruptedException e) {
//                        }
//
//                    } catch (IOException e) {
//                    }
//                    if (runningStatus != 0) {
//                    }
//                    return;
//                }
//            } , 0 , TimeUnit.SECONDS);
//        }
//        executor.shutdown();


//        计算一天的毫秒数
        long dayS = 24 * 60 * 60 * 1000;
// 每天的08:30:00执行任务
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd '08:30:00'");
// 首次运行时间
        Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
// 如果已过当天设置时间，修改首次运行时间为明天
        if(System.currentTimeMillis() > startTime.getTime()){
            startTime = new Date(startTime.getTime() + dayS);
        }

        Timer t = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                // 要执行的代码
                System.err.println("xxxxxxxxx");
            }
        };


    }
}
