package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/usersTable";
    private static final String USERNAME = "root";
    private static final String PASS = "rootroot";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASS);
            System.out.println("Connection OK");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Connection ERROR");
            e.printStackTrace();
        }
        return connection;
    }
}
