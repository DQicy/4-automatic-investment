package derbyOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//参考：https://www.cxymm.net/article/Sunshine_wz/104858418
public class DerbyUtils {

    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String url = "jdbc:derby:stockDB;create=true";
    static {

        Connection conn;
        try {

            Class.forName(driver);
        }catch(Exception e) {

            //
        }finally {

            // DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }
    }
    public static Connection getConnection(){

        Connection conn = null;
        try {

            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 测试， Derby 内嵌数据库的连接
     * @param args
     */
    public static void main(String[] args) {

        Connection conn = DerbyUtils.getConnection();
        System.out.println(conn);
    }
}