package org.metricminer.refactoringcc.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.metricminer.refactoringcc.model.SourceCodeData;

import au.com.bytecode.opencsv.CSVReader;

public class SourceCodeDataFactory {

    private CSVReader csvReader;

    public SourceCodeDataFactory(InputStream is) {
        csvReader = new CSVReader(new InputStreamReader(is), ';', '"');
    }

    public List<SourceCodeData> build() {
        try {
            List<SourceCodeData> sources = new ArrayList<SourceCodeData>();
            List<String[]> lines = csvReader.readAll();
            for (int i = 1; i < lines.size(); i++) {
                String[] line = lines.get(i);
                String message = line[0];
                String projectName = line[1];
                Calendar date = parseDate(line[2]);
                String kind = line[3];
                String cc = line[4];
                String className = line[5];
                sources.add(new SourceCodeData(message, date, kind, Integer.parseInt(cc), className, projectName));
            }
            return sources;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Calendar parseDate(String dateString) {
        //2000-01-13 08:42:41.0
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            Date date = format.parse(dateString);
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            return instance;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    

}
