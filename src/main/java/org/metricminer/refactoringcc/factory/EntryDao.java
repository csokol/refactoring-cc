package org.metricminer.refactoringcc.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EntryDao {
    private Connection connection;

    public EntryDao(Connection connection) {
        this.connection = connection;
    }

    public List<String[]> list() {
        try {
            ArrayList<String[]> res = new ArrayList<String[]>();
            Statement st = connection.createStatement();
            String sql = "select * from entry";
            ResultSet rs = st.executeQuery(sql);
            int j = 0;
            while (rs.next()) {
                String[] row = new String[6];
                for (int i = 0; i < row.length; i++) {
                    row[i] = rs.getString(i + 1);
                }
                j++;
                res.add(row);
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
