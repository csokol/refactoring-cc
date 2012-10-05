package org.metricminer.refactoringcc.finder;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IsRefactoringTest {

    @Test
    public void test() {
        shouldBeRefactor("Refactoring");
        shouldBeRefactor("refactoring");
        shouldBeRefactor("refactor that");
        shouldBeRefactor("refactored this");
        shouldBeRefactor("REFACTOR this");
    }

    private void shouldBeRefactor(String message) {
        assertTrue(new IsRefactoring().isRefactoring(message));
    }

}
