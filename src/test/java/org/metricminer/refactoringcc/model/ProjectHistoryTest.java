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
        List<SourceCodeData> sources = new ArrayList<SourceCodeData>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        firstDate = Calendar.getInstance();
        secondDate = Calendar.getInstance();
        firstDate.setTime(simpleDateFormat.parse("2012-11-1"));
        secondDate.setTime(simpleDateFormat.parse("2012-12-1"));
        
        sources.add(new SourceCodeData("message1", firstDate, "NEW", 10, "class.java", "metricminer"));
        sources.add(new SourceCodeData("message1", firstDate, "NEW", 5, "class2.java", "metricminer"));
        sources.add(new SourceCodeData("message2", secondDate, "MODIFICATION", 10, "class2.java", "metricminer"));
        sources.add(new SourceCodeData("message2", secondDate, "DELETED", 0, "class.java", "metricminer"));
        
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
        List<SourceCodeData> sourcesFromFirstDate = history.getSourcesFrom(firstDate);
        List<SourceCodeData> sourcesFromSecondDate = history.getSourcesFrom(secondDate);
        
        assertEquals(2, sourcesFromFirstDate.size());
        assertEquals("class.java", sourcesFromFirstDate.get(0).getClassName());
        assertEquals("class2.java", sourcesFromFirstDate.get(1).getClassName());
        assertEquals(2, sourcesFromSecondDate.size());
        assertEquals("class.java", sourcesFromSecondDate.get(0).getClassName());
        assertEquals("class2.java", sourcesFromSecondDate.get(1).getClassName());
    }
    
    @Test
    public void shouldGroupsCommits() throws Exception {
        List<Commit> commits = history.commits();
        
        assertEquals(2, commits.size());
        assertEquals("message1", commits.get(0).getMessage());
        assertEquals(15, commits.get(0).getTotalCC());
        assertEquals("message2", commits.get(1).getMessage());
        assertEquals(10, commits.get(1).getTotalCC());
    }


}
