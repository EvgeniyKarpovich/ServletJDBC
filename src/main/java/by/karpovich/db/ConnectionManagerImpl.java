package by.karpovich.db;

import by.karpovich.exception.IncorrectDataException;
import by.karpovich.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerImpl {
    private  String password = "db.password";
    private  String username = "db.username";
    private  String url = "db.url";

    public ConnectionManagerImpl() {
    }

    public ConnectionManagerImpl(String password, String username, String url) {
        this.password = password;
        this.username = username;
        this.url = url;
    }

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
