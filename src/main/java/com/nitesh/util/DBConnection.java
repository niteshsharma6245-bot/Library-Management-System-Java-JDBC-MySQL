package com.nitesh.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    public static Connection getConnection() throws SQLException
    {
        String url = "jdbc:mysql://localhost:3306/library_management" ;
        String username = "root" ;
        String password = "12345" ;

        return DriverManager.getConnection(
                url ,
                username,
                password
        );
    }
}


