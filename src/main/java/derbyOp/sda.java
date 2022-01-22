package derbyOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class sda {
    //插入，更新逻辑测试
    public static void main(String args[]) throws Exception {
        //Registering the driver
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        //Getting the Connection object
        String URL = "jdbc:derby:sampleDB;create=true";
        Connection conn = DriverManager.getConnection(URL);

        //Creating the Statement object
        Statement stmt = conn.createStatement();

        //Executing the query
        String createQuery = "CREATE TABLE Employees( "
                + "Id INT NOT NULL GENERATED ALWAYS AS IDENTITY, "
                + "Name VARCHAR(255), "
                + "Salary INT NOT NULL, "
                + "Location VARCHAR(255), "
                + "PRIMARY KEY (Id))";

        stmt.execute(createQuery);
        System.out.println("Table created");
        System.out.println(" ");

        //Executing the query
        String insertQuery = "INSERT INTO Employees("
                + "Name, Salary, Location) VALUES "
                + "('Amit', 30000, 'Hyderabad'), "
                + "('Kalyan', 40000, 'Vishakhapatnam'), "
                + "('Renuka', 50000, 'Delhi'), "
                + "('Archana', 15000, 'Mumbai'), "
                + "('Trupti', 45000, 'Kochin')";

        stmt.execute(insertQuery);
        System.out.println("Values inserted");
        System.out.println(" ");

        //Executing the query
        String selectQuery = "SELECT * FROM Employees";
        ResultSet rs = stmt.executeQuery(selectQuery);
        System.out.println("Contents of the table after inserting the table");
        while(rs.next()) {
            System.out.println("Id: "+rs.getString("Id"));
            System.out.println("Name: "+rs.getString("Name"));
            System.out.println("Salary: "+rs.getString("Salary"));
            System.out.println("Location: "+rs.getString("Location"));
        }
        System.out.println(" ");

        //Altering the table
        stmt.execute("ALTER TABLE Employees ADD COLUMN Age INT");
        stmt.execute("ALTER TABLE Employees ADD COLUMN Phone_No BigINT");
        stmt.execute("ALTER TABLE Employees " + "ADD CONSTRAINT New_Constraint UNIQUE(Phone_No)");

        stmt.execute("INSERT INTO Employees "
                + "(Name, Salary, Location, Age, Phone_No) "
                + "VALUES ('Amit', 30000, 'Hyderabad', 30, 9848022338)");
        ResultSet alterResult = stmt.executeQuery("Select * from Employees");
        System.out.println("Contents of the table after altering "
                + "the table and inserting values to it: ");
        while(alterResult.next()) {
            System.out.println("Id: "+alterResult.getString("Id"));
            System.out.println("Name: "+alterResult.getString("Name"));
            System.out.println("Salary: "+alterResult.getString("Salary"));
            System.out.println("Location: "+alterResult.getString("Location"));
            System.out.println("Age: "+alterResult.getString("Age"));
            System.out.println("Phone_No: "+alterResult.getString("Phone_No"));
        }
    }
}