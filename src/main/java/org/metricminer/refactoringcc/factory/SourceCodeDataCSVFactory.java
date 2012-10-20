package org.metricminer.refactoringcc.factory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.metricminer.refactoringcc.model.SourceCodeData;

import au.com.bytecode.opencsv.CSVReader;

public class SourceCodeDataCSVFactory extends SourceCodeDataFactory {
    private static Logger logger = Logger.getLogger(SourceCodeDataCSVFactory.class);

    private CSVReader csvReader;

    public SourceCodeDataCSVFactory(InputStream is) {
        csvReader = new CSVReader(new InputStreamReader(is), ';', '"');
    }

    public List<SourceCodeData> build() {
        try {
            List<SourceCodeData> sources = new ArrayList<SourceCodeData>();
            csvReader.readNext();
            for (String[] line = csvReader.readNext(); line != null; line = csvReader.readNext()) {
                try {
                    SourceCodeData data = buildData(line);
                    sources.add(data);
                } catch (Exception e) {
                    logger.error("could not parse line: " + Arrays.asList(line), e);
                }
            }
            csvReader.close();
            return sources;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    

}
