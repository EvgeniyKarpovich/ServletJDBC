package by.karpovich.db;

import by.karpovich.exception.IncorrectDataException;
import by.karpovich.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImpl {
    private final String password = "db.password";
    private final String username = "db.username";
    private final String url = "db.url";


    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(url),
                    PropertiesUtil.get(username),
                    PropertiesUtil.get(password));
        } catch (SQLException e) {
            throw new IncorrectDataException("Wrong url/username/password");
        }
    }

}
