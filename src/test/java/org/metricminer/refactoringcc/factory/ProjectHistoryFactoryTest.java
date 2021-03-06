package org.metricminer.refactoringcc.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.metricminer.refactoringcc.model.ArtifactHistory;
import org.metricminer.refactoringcc.model.ProjectHistory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class ProjectHistoryFactoryTest {

    @Test
    public void shouldBuildArtifactHistory() {
        List<SourceCodeData> sources = new ArrayList<SourceCodeData>();
        sources.add(new SourceCodeData("message", Calendar.getInstance(), "NEW", 1, "class.java", "metricminer"));
        sources.add(new SourceCodeData("message", Calendar.getInstance(), "NEW", 1, "class2.java", "metricminer"));
        sources.add(new SourceCodeData("message", Calendar.getInstance(), "MODIFICATION", 1, "class2.java", "metricminer"));
        
        ProjectHistoryFactory factory = new ProjectHistoryFactory();
        ProjectHistory build = factory.build(sources);
        List<ArtifactHistory> artifacts = build.getArtifacts();
        
        assertEquals(2, artifacts.size());
        assertEquals("class.java", artifacts.get(0).getName());
        assertEquals("class2.java", artifacts.get(1).getName());
    }
    
}
