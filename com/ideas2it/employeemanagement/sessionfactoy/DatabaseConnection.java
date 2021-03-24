package com.ideas2it.employeemanagement.sessionfactoy;

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
    private static Connection connection = null;

    private DatabaseConnection() {
    }

    /**
     * method used to create connection object
     * if connection is null or closed
     *
     * @return    connection object
     */
    public Connection getConnection() {
        try {
            if ((null == connection) || connection.isClosed()) {
                try {
                    String url = "jdbc:mysql://localhost:3306/employeedb";
                    connection = DriverManager.getConnection(url,"root","root");
                    connection.setAutoCommit(false);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static DatabaseConnection connectDatabase() {
        return ((null == databaseConnection) 
                ? (databaseConnection = new DatabaseConnection())
                : databaseConnection);
    } 
}