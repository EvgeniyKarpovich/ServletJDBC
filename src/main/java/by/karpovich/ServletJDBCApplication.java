package by.karpovich;

import by.karpovich.db.ConnectionManagerImpl;

public class ServletJDBCApplication {

    public static void main(String[] args) {

        ConnectionManagerImpl connectionManager = new ConnectionManagerImpl();
    }
}
