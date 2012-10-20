package org.metricminer.refactoringcc.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MeuCSVReader {

    private BufferedReader reader;

    public MeuCSVReader(InputStreamReader inputStreamReader) {
        reader = new BufferedReader(inputStreamReader);
    }

    public String[] readNext() throws IOException {
        String line = reader.readLine();
        if (line == null)
            return null;
        String[] fields = line.split(";");
        
        return sanitize(fields);
    }

    private String[] sanitize(String[] fields) {
        String[] res = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String s = fields[i];
            String trim = s.trim();
            res[i] = removeQuotes(trim);
        }
        return res;
    }

    private String removeQuotes(String trim) {
        if (trim.length() > 2) {
            trim = trim.charAt(0) == '"' ? trim.substring(1) : trim;
            trim = trim.charAt(trim.length() - 1) == '"' ? trim.substring(0, trim.length() - 1) : trim;
        }
        return trim;
    }

    public void close() throws IOException {
        reader.close();
    }

}
