package org.metricminer.refactoringcc.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EntryDaoTest {

    private EntryDao dao;

    @Before
    public void setUp() {
        Connection connection = new ConnectionFactory().openConnection("jdbc:mysql://localhost/refactoring-cc-test");
        dao = new EntryDao(connection);
    }
    
    @Test
    public void shouldListEntries() {
        List<String[]> list = dao.list();
        assertEquals(33625, list.size());
    }
    
    @Test
    public void shouldListProjects() {
        List<String> projects = dao.projects();
        assertEquals(2, projects.size());
        assertTrue(projects.contains("Apache Ant"));
        assertTrue(projects.contains("Apache Abdera"));
    }


}
