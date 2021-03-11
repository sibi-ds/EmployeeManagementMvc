package com.ideas2it.sessionfactoy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * this is a singleton class
 * used to hold database connection object
 *
 * @author  sibi
 * @created 2021-03-11
 */
public class DatabaseConnection {

    private static DatabaseConnection databaseConnection = null;

    private DatabaseConnection() {
    }

    /**
     * method used to create connection object
     *
     * @return    connection object
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/employeedb";
        Connection connection = DriverManager.getConnection(url,"root","root");
        return connection;
    }

    public static DatabaseConnection getConnectionObject() {
        if (null == databaseConnection) {
            databaseConnection = new DatabaseConnection();
        }
        return databaseConnection;
    } 
}