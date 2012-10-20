package org.metricminer.refactoringcc.factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.metricminer.refactoringcc.model.SourceCodeData;

public abstract class SourceCodeDataFactory {

    protected SourceCodeData buildData(String[] line) {
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

    protected Calendar parseDate(String dateString) throws ParseException {
        //2000-01-13 08:42:41.0
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = format.parse(dateString);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance;
    }

}