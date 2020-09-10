package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Get a connection to the persisting repository.
 */
public class ConnectionFactory {

    private static ConnectionFactory connFactory = new ConnectionFactory();

    private Properties props = new Properties();

    /**
     * Connection Factory Constructor
     */
    private ConnectionFactory() {
        try {

            props.load(new FileReader("./src/main/resources/application.properties"));
            if (props == null) {
                props.load(new FileReader("./application.properties"));
            }
        } catch (IOException e) {
            System.err.println("Could not get a connection!");
        }
    }

    //Get the connection factory
    public static ConnectionFactory getConnFactory() {
        return connFactory;
    }

    /**
     * Get the connection to the database
     * @return
     */
    public Connection getConnection() {

        Connection conn = null;

        //attempt to log into the database
        try {

            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password")
            );

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getStackTrace());
            System.err.println("Cannot find the proper schema referenced!");
        }

        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                        System.getenv("url"),
                        System.getenv("username"),
                        System.getenv("password")
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return conn;

    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}

