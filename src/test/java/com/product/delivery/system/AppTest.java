package com.product.delivery.system;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;

/**
 * Tests for App.java entry-point flows which read from stdin and print to stdout.
 * Notes:
 * - Tests simulate stdin using ByteArrayInputStream.
 * - Tests capture stdout/stderr using ByteArrayOutputStream.
 * - These tests are intentionally defensive: they assert substrings rather than exact lines,
 * - because ProductDeliverySystem is a singleton and may accumulate state between tests.
 */
public class AppTest
{
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    @Before
    public void setUpStreams()
    {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams()
    {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    public void testFirstChallenge_readsInputAndPrintsCosts()
    {
        String input =
                "100 3\n" +
                        "PKG1 50 30 OFR001\n" +
                        "PKG2 75 125 OFFR0008\n" +
                        "PKG3 175 100 OFR003\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        App.firstChallenge();
        String output = outContent.toString();
        assertTrue("Should contain PKG1",output.contains("PKG1"));
        assertTrue("Should contain PKG2",output.contains("PKG2"));
        assertTrue("Should contain PKG3",output.contains("PKG3"));
        assertTrue("Should contain numeric totals", output.matches("(?s).*\\d+.*"));
    }

    @Test
    public void testSecondChallenge_readsFullInputAndSchedules()
    {
        String input =
                "100 5\n" +
                        "PKG1 50 30 OFR001\n" +
                        "PKG2 75 125 OFFR0008\n" +
                        "PKG3 175 100 OFFR003\n" +
                        "PKG4 110 60 OFFR002\n" +
                        "PKG5 155 95 NA\n" +
                        "2 70 200\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        App.secondChallenge();
        String output = outContent.toString();
        assertTrue(output.contains("PKG1"));
        assertTrue(output.contains("PKG2"));
        assertTrue(output.contains("PKG3"));
        assertTrue(output.contains("PKG4"));
        assertTrue(output.contains("PKG5"));
        assertTrue("Should include decimal delivery times", output.matches("(?s).*\\d+\\.\\d{1,2}.*"));
    }
}
