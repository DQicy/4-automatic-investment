package crawl;

import derbyOp.DerbyUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class shangzheng50 implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(5)
            .setSleepTime(1000)
            .setCharset("utf-8")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");


    public void process(Page page) {
        //抓取上证50市盈率，存到Derby
        page.putField("shangzheng50PE", page.getHtml().xpath("//*[@id=\"data-description\"]/table/tbody/tr[5]/td[2]/text()").toString());

        Connection conn = DerbyUtils.getConnection();

        //Creating the Statement object
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //存储进derby
        //将市盈率插入至pe_total表中
        String insertQuery = "INSERT INTO pe_total("
                + "Name, Value, time) VALUES "
                + "('shangzheng50'," + page.getHtml().xpath("//*[@id=\"data-description\"]/table/tbody/tr[5]/td[2]/text()").toString() + ", '"
                + DateTransfer.currentTime() + "')";

        try {
            stmt.execute(insertQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Values inserted");
        System.out.println(" ");
    }

    public Site getSite() {

        return site;
    }



    public static void main(String[] args) {
//        Spider.create(new BaiduPageProcessor())
//                .addUrl("https://legulegu.com/stockdata/sz50-ttm-lyr")
//                .addPipeline(new ConsolePipeline())
////                .addPipeline(new JsonFilePipeline("/Users/qmp/myproject/WebMagicSpider"))
//                .thread(1)
//                .run();
    }
}
