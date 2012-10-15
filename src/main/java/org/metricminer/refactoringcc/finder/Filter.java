package org.metricminer.refactoringcc.finder;

import java.util.List;

import org.metricminer.refactoringcc.model.Commit;

public interface Filter {

    List<Commit> filter(List<Commit> commits);
    
}
