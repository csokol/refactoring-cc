package org.metricminer.refactoringcc.model;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;

public class ProjectHistoryTest {

    private ProjectHistory history;
    private Calendar secondDate;
    private Calendar firstDate;

    @Before
    public void setUp() throws ParseException {
        List<SourceCode> sources = new ArrayList<SourceCode>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        firstDate = Calendar.getInstance();
        secondDate = Calendar.getInstance();
        firstDate.setTime(simpleDateFormat.parse("2012-11-1"));
        secondDate.setTime(simpleDateFormat.parse("2012-12-1"));
        
        sources.add(new SourceCode("message", firstDate, "NEW", 1, "class.java", "metricminer"));
        sources.add(new SourceCode("message", firstDate, "NEW", 1, "class2.java", "metricminer"));
        sources.add(new SourceCode("message", secondDate, "MODIFICATION", 1, "class2.java", "metricminer"));
        
        ProjectHistoryFactory factory = new ProjectHistoryFactory();
        history = factory.build(sources);
    }
    
    @Test
    public void shouldGroupVersionDates() throws Exception {
        List<Calendar> versionDates = history.getVersionDates();
        assertEquals(2, versionDates.size());
    }
    
    @Test
    public void shouldGetSourcesFromDate() throws Exception {
        List<SourceCode> sourcesFromFirstDate = history.getSourcesFrom(firstDate);
        List<SourceCode> sourcesFromSecondDate = history.getSourcesFrom(secondDate);
        
        assertEquals(2, sourcesFromFirstDate.size());
        assertEquals("class.java", sourcesFromFirstDate.get(0).getClassName());
        assertEquals("class2.java", sourcesFromFirstDate.get(1).getClassName());
        assertEquals(1, sourcesFromSecondDate.size());
        assertEquals("class2.java", sourcesFromSecondDate.get(0).getClassName());
    }


}
