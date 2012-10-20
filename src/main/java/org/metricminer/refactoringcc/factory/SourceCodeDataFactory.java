package org.metricminer.refactoringcc.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.model.SourceCodeData;

import au.com.bytecode.opencsv.CSVReader;

public class SourceCodeDataFactory {
    private static Logger logger = Logger.getLogger(SourceCodeDataFactory.class);

    private CSVReader csvReader;

    public SourceCodeDataFactory(InputStream is) {
        csvReader = new CSVReader(new InputStreamReader(is), ';', '"');
    }

    public SourceCodeDataFactory() {
    }

    public List<SourceCodeData> build() {
        try {
            List<SourceCodeData> sources = new ArrayList<SourceCodeData>();
            csvReader.readNext();
            for (String[] line = csvReader.readNext(); line != null; line = csvReader.readNext()) {
                try {
                    SourceCodeData data = buildData(line);
                    sources.add(data);
                } catch (Exception e) {
                    logger.error("could not parse line: " + Arrays.asList(line), e);
                }
            }
            csvReader.close();
            return sources;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private SourceCodeData buildData(String[] line) {
        String message = line[0];
        String projectName = line[1];
        Calendar date;
        try {
            date = parseDate(line[2]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String kind = line[3];
        String cc = line[4];
        String className = line[5];
        SourceCodeData data = new SourceCodeData(message, date, kind, Integer.parseInt(cc), className, projectName);
        return data;
    }

    private Calendar parseDate(String dateString) throws ParseException {
        //2000-01-13 08:42:41.0
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = format.parse(dateString);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }
    
    

}
