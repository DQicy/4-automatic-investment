package crawl;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class DateTransfer {
    public static String currentTime() {
        Date date = new Date();
        return toString(date);
    }

    // 日期转化为字符串形式
    public static String toString(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date).split(" ")[0].replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(currentTime());
        String insertQuery = "INSERT INTO pe_total("
                + "Name, Value, time) VALUES "
                + "('shangzheng50'," + "22" + ", '"
                + DateTransfer.currentTime() + "')";
        System.out.println(insertQuery);
    }
}
