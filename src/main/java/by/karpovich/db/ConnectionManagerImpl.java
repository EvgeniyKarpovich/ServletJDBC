package by.karpovich.db;

import by.karpovich.exception.IncorrectDataException;
import by.karpovich.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class ConnectionManagerImpl {

    private static  String PASSWORD_KEY = "db.password";
    private static  String USERNAME_KEY = "db.username";
    private static  String URL_KEY = "db.url";

    static {
        loadDriver();
    }

    private ConnectionManagerImpl() {
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new IncorrectDataException("Wrong url/username/password");
        }
    }
}
