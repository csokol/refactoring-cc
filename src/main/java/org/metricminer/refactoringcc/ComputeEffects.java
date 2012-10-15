package org.metricminer.refactoringcc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.factory.SourceCodeDataFactory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class ComputeEffects {
    private static Logger logger = Logger.getLogger(ComputeEffects.class);

    public static void main(String[] args) throws FileNotFoundException {
        InputStream is;
        is = new FileInputStream(
                "/home/csokol/ime/tcc/new-workspace/cc-50-projects.csv");
        SourceCodeDataFactory factory = new SourceCodeDataFactory(is);
        List<SourceCodeData> allSources = factory.build();
        computeEffects(allSources);
    }

    private static void computeEffects(List<SourceCodeData> allSources) {
        RefactoringEffects refactoringEffects = new RefactoringEffects(
                allSources);
        refactoringEffects.compute();
    }
}
