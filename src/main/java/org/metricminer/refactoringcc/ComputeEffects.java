package org.metricminer.refactoringcc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.factory.SourceCodeDataFactory;
import org.metricminer.refactoringcc.model.SourceCodeData;

public class ComputeEffects {
    private static Logger logger = Logger.getLogger(ComputeEffects.class);

    public static void main(String[] args) throws FileNotFoundException {
        List<SourceCodeData> allSources = new ArrayList<SourceCodeData>();
        File[] dataFiles = new File("src/main/resources/data/")
                .listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".csv");
                    }
                });

        List<File> files = Arrays.asList(dataFiles);

        for (File file : files) {
            logger.debug("loading data from: " + file);
            List<SourceCodeData> sourcesData = new SourceCodeDataFactory(
                    new FileInputStream(file)).build();
            allSources.addAll(sourcesData);
        }
    }

    private static void computeEffects(List<SourceCodeData> allSources) {
        RefactoringEffects refactoringEffects = new RefactoringEffects(
                allSources);
        refactoringEffects.compute();
    }
}
