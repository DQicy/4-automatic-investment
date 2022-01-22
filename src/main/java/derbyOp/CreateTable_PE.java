package derbyOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable_PE {
    //创建PE表https://www.gingerdoc.com/apache_derby/apache_derby_create_table
    public static void main(String args[]) throws Exception {
        Connection conn = DerbyUtils.getConnection();

        //Creating the Statement object
        Statement stmt = conn.createStatement();

        //Executing the query
        String query = "CREATE TABLE pe_total( "
                + "Id INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
                + "Name VARCHAR(255), "
                + "Value INT NOT NULL, "
                + "time VARCHAR(255), "
                + "PRIMARY KEY (Id))";
        stmt.execute(query);
        System.out.println("Table created");
    }
}