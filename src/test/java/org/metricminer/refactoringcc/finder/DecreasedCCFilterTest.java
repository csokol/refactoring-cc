package org.metricminer.refactoringcc.finder;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.metricminer.refactoringcc.builder.CommitBuilder;
import org.metricminer.refactoringcc.model.Commit;

public class DecreasedCCFilterTest {

    @Test
    public void shouldFilterOne() {
        DecreasedCCFilter decreasedCCFilter = new DecreasedCCFilter();
        
        Commit first = new CommitBuilder().withTotalCC(10).build();
        Commit decrease1 = new CommitBuilder().withPriorCommit(first).withTotalCC(7).build();
        
        List<Commit> commits = Arrays.asList(first, decrease1);
        
        List<Commit> filtered = decreasedCCFilter.filter(commits);
        
        assertEquals(1, filtered.size());
        
    }
    
}
