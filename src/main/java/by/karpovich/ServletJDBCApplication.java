package by.karpovich;

import by.karpovich.db.ConnectionManagerImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ServletJDBCApplication {

    public static void main(String[] args) throws SQLException, IOException {

        Connection connection = ConnectionManagerImpl.getConnection();
        System.out.println(connection.getClientInfo());
    }
}
