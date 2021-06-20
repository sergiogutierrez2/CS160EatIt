package net.codejava;

import java.sql.*;

public class SQLiteDemo {
    public static void main(String[] args) throws SQLException {
        String jdbcUrl= "jdbc:sqlite:/C:\\Users\\Thomas Home Laptop\\Desktop\\SJSU\\sqlite-tools-win32-x86-3360000\\usersdb.db";
        String jdbcUrl2= "jdbc:sqlite:/C:\\Users\\Thomas Home Laptop\\Desktop\\SJSU\\sqlite-tools-win32-x86-3360000\\contacts2.db";
        Connection connection2 = DriverManager.getConnection(jdbcUrl2);
        String sql2 = "create table contacts2 (firstname varchar(20), lastname varchar(20))";
        Statement statement2= connection2.createStatement();
        statement2.executeUpdate(sql2);
        System.out.println("Table Created"); 
       
        String sql3 = "insert into contacts2 values ('Ravi','Kumar')";
        int rows = statement2.executeUpdate(sql3);
        if (rows > 0)
        	System.out.println("row created");
        String sql4 = "select rowid, * from contacts2";
        ResultSet result = statement2.executeQuery(sql4);
        while (result.next()) {
        	int rowId = result.getInt("rowid");
        	String firstname = result.getString("firstname");
        	String lastname = result.getString("lastname");
        	
        	System.out.println(rowId + "|" + firstname + "|" + lastname );
        	
        }
        
       try {

            Connection connection = DriverManager.getConnection(jdbcUrl);
            String sql = "SELECT * FROM users";
            
            Statement statement = connection.createStatement();
            ResultSet result2 = statement.executeQuery(sql);
            while( result2.next()) {
                String name = result2.getString("name");
                String email = result2.getString("email");
                System.out.println(name + "|" + email);
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to Database");
            e.printStackTrace();
        }
    }
}
