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

import org.metricminer.refactoringcc.model.SourceCode;

import au.com.bytecode.opencsv.CSVReader;

public class SourceCodeFactory {

    private CSVReader csvReader;

    public SourceCodeFactory(InputStream is) {
        csvReader = new CSVReader(new InputStreamReader(is), ';', '"');
    }

    public List<SourceCode> build() {
        try {
            List<SourceCode> sources = new ArrayList<SourceCode>();
            List<String[]> lines = csvReader.readAll();
            for (int i = 1; i < lines.size(); i++) {
                String[] line = lines.get(i);
                String message = line[0];
                Calendar date = parseDate(line[1]);
                String kind = line[2];
                String cc = line[3];
                String className = line[4];
                sources.add(new SourceCode(message, date, kind, cc, className));
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
