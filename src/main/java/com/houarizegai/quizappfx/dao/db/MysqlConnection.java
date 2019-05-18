
package com.houarizegai.quizappfx.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    private static Connection con;
    
    public MysqlConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/quiz_app_db", "root", "");
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error msg: " + ex.getMessage());
            con = null;
        }
    }
    
    public Connection getConnection() {
        return con;
    }
}
