package org.metricminer.refactoringcc.model;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.metricminer.refactoringcc.factory.ProjectHistoryFactory;

public class ProjectHistoryTest {

    @Test
    public void shouldGroupVersionDates() throws Exception {
        List<SourceCode> sources = new ArrayList<SourceCode>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date1.setTime(simpleDateFormat.parse("2012-11-1"));
        date2.setTime(simpleDateFormat.parse("2012-12-1"));
        
        sources.add(new SourceCode("message", date1, "NEW", "1", "class.java"));
        sources.add(new SourceCode("message", date1, "NEW", "1", "class2.java"));
        sources.add(new SourceCode("message", date2, "MODIFICATION", "1", "class2.java"));
        
        ProjectHistoryFactory factory = new ProjectHistoryFactory();
        ProjectHistory history = factory.build(sources);
        
        List<Calendar> versionDates = history.getVersionDates();
        assertEquals(2, versionDates.size());
        Collections.sort(versionDates);
        assertEquals(date1, versionDates.get(0));
        assertEquals(date2, versionDates.get(1));
    }

}
