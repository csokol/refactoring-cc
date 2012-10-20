package org.metricminer.refactoringcc.factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.metricminer.refactoringcc.model.SourceCodeData;

public class SourceCodeDataDBFactory {
    
    private EntryDao dao;
    
    public SourceCodeDataDBFactory(EntryDao dao) {
        this.dao = dao;
    }

    public List<SourceCodeData> build() {
        ArrayList<SourceCodeData> result = new ArrayList<SourceCodeData>();
        List<String[]> list = dao.list();
        for (String[] row : list) {
            result.add(buildData(row));
        }
        return result;
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
