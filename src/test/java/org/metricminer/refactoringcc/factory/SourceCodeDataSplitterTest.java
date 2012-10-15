package org.metricminer.refactoringcc.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class SourceCodeDataSplitterTest {

    @Test
    public void shouldSplitSourcesByProject() {
        List<SourceCodeData> sources = new ArrayList<SourceCodeData>();
        sources.add(new SourceCodeData("message", Calendar.getInstance(), "NEW", 1, "class.java", "metricminer"));
        sources.add(new SourceCodeData("message", Calendar.getInstance(), "NEW", 1, "class2.java", "metricminer"));
        sources.add(new SourceCodeData("message", Calendar.getInstance(), "MODIFICATION", 1, "class2.java", "maven"));
        sources.add(new SourceCodeData("message", Calendar.getInstance(), "MODIFICATION", 1, "class3.java", "maven"));
        
        SourceCodeDataSplitter splitter = new SourceCodeDataSplitter();
        List<List<SourceCodeData>> sourcesSplitted = splitter.splitProjects(sources);
        assertEquals(2, sourcesSplitted.size());
        assertEquals(2, sourcesSplitted.get(0).size());
        assertEquals(2, sourcesSplitted.get(1).size());
    }

}
