package org.metricminer.refactoringcc.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        while (nextResult(rs)) {
            String[] row = nextEntry(rs);
            res.add(row);
        }
        return res;
    }

    public List<String> projects() {
        ArrayList<String> res = new ArrayList<String>();
        ResultSet rs = executeQuery("select distinct(project_name) from entry");
        while (nextResult(rs)) {
            res.add(getString(rs, 1));
        }
        return res;
    }

    public List<String[]> entriesFromProject(String project) {
        ArrayList<String[]> res = new ArrayList<String[]>();
        ResultSet rs = executeQuery("SELECT * FROM  entry WHERE project_name = \"" + project + "\"");
        while (nextResult(rs)) {
            String[] row = nextEntry(rs);
            res.add(row);
        }
        return res;
    }

    private ResultSet executeQuery(String sql, String param) {
        PreparedStatement st;
        try {
            st = connection.prepareStatement(sql);
            st.setString(1, param);
            System.out.println(st);
            ResultSet rs = st.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] nextEntry(ResultSet rs) {
        String[] row = new String[6];
        for (int i = 0; i < row.length; i++) {
            row[i] = getString(rs, i + 1);
        }
        return row;
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
        PreparedStatement st;
        try {
            st = connection.prepareStatement(sql);
            System.out.println(st);
            ResultSet rs = st.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
