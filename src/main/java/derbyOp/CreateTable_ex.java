package derbyOp;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable_ex {
    //创建指数表https://www.gingerdoc.com/apache_derby/apache_derby_create_table
    public static void main(String args[]) throws Exception {
        Connection conn = DerbyUtils.getConnection();

        //Creating the Statement object
        Statement stmt = conn.createStatement();

        //Executing the query
        String query = "CREATE TABLE ex_total( "
                + "Id INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
                + "Name VARCHAR(255), "
                + "Value INT NOT NULL, "
                + "time VARCHAR(255), "
                + "cost INT NOT NULL,"
                + "standard1 INT NOT NULL,"
                + "standard2 INT NOT NULL,"
                + "PRIMARY KEY (Id))";
        stmt.execute(query);
        System.out.println("Table created");
    }
}
