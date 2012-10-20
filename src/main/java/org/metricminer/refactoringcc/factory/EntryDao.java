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
        ArrayList<String[]> res = new ArrayList<String[]>();
        ResultSet rs = executeQuery("select * from entry");
        int j = 0;
        while (nextResult(rs)) {
            String[] row = new String[6];
            for (int i = 0; i < row.length; i++) {
                row[i] = getString(rs, i + 1);
            }
            j++;
            res.add(row);
        }
        return res;
    }


    public List<String> projects() {
        ArrayList<String> res = new ArrayList<String>();
        ResultSet rs = executeQuery("select distinct(project_name) from entry");
        while(nextResult(rs)) {
            res.add(getString(rs, 1));
        }
        return res;
    }

    private String getString(ResultSet rs, int i) {
        try {
            return rs.getString(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean nextResult(ResultSet rs) {
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet executeQuery(String sql) {
        Statement st;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
