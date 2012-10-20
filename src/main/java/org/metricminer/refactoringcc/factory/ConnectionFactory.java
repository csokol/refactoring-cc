package org.metricminer.refactoringcc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public Connection openConnection(String url) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url,
                    "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
