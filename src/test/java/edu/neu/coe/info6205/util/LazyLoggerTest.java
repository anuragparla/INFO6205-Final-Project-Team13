package edu.neu.coe.info6205.util;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LazyLoggerTest {

    static class StringEvaluator {

        public boolean isEvaluated() {
            return evaluated;
        }

        boolean evaluated = false;

        String evaluateMessage(String message) {
            System.out.println("Evaluate message: " + message);
            evaluated = true;
            return message;
        }

    }

    public String evaluateMessage(String message) {
        System.out.println("evaluate message: " + message);
        return message;
    }

    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure();
    }

    @Test
    public void testTraceLazy() {
        StringEvaluator se = new StringEvaluator();
        logger.trace(() -> "Hello " + se.evaluateMessage("trace message"));
        assertEquals(logger.isTraceEnabled(), se.isEvaluated());
    }

    @SuppressWarnings("EmptyMethod")
    @Ignore
    public void testTraceLazyException() {
    }

    @Test
    public void testDebugLazy() {
        StringEvaluator se = new StringEvaluator();
        logger.debug(() -> "Hello " + se.evaluateMessage("debug message"));
        assertEquals(logger.isDebugEnabled(), se.isEvaluated());
    }

    @SuppressWarnings("EmptyMethod")
    @Ignore
    public void testDebugLazyException() {
    }

    static final LazyLogger logger = new LazyLogger(LazyLoggerTest.class);
}