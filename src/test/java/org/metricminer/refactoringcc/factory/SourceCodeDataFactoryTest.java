package org.metricminer.refactoringcc.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.metricminer.refactoringcc.model.SourceCode;

public class SourceCodeDataFactoryTest {

    @Test
    public void shouldBuildOneSource() {
        String input = "\"message\";\"date\";\"kind\";\"cc\";\"name\";;;;;;;;\n" +
        		"\"initial checkin\";\"2000-01-13 08:42:41.0\";\"NEW\";2;\"src/main/com/ice/tar/InvalidHeaderException.java\";;;;;;;;";
        InputStream is = new ByteArrayInputStream(input.getBytes());
        SourceCodeDataFactory factory = new SourceCodeDataFactory(is);
        List<SourceCode> sources = factory.build();
        assertEquals(1, sources.size());
    }
    
    @Test
    public void shouldWorkWithRealCSV() throws Exception {
        InputStream is = new FileInputStream("src/main/resources/antcc.csv");
        SourceCodeDataFactory factory = new SourceCodeDataFactory(is);
        List<SourceCode> sources = factory.build();
        assertEquals(28065, sources.size());
        for (SourceCode sourceCode : sources) {
            assertTrue(sourceCode.getClassName().endsWith("java"));
        }
    }

}
