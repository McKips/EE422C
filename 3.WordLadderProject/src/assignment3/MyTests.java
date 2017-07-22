package assignment3;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
/**
 * This is the sample test cases for students
 * @author lisahua
 *
 */
public class MyTests {
    private static Set<String> dict;
    private static ByteArrayOutputStream outContent;

    @BeforeClass
    public static void setUp() {
        Main.initialize();
        dict = Main.makeDictionary();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    private boolean verifyLadder(ArrayList<String> ladder) {
        String prev = null;
        if (ladder == null)
            return true;
        for (String word : ladder) {
            if (!dict.contains(word.toUpperCase()) && !dict.contains(word.toLowerCase())) {
                return false;
            }
            if (prev != null && !differByOne(prev, word))
                return false;
            prev = word;
        }
        return true;
    }

    private static boolean differByOne(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && diff++ > 1) {
                return false;
            }
        }

        return true;
    }

    /** Has Word Ladder **/
    @Test(timeout = 30000)
    public void myTestBFS1() {
        ArrayList<String> res = Main.getWordLadderBFS("start", "rises");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }

    @Test(timeout = 30000)
    public void myTestBFS2() {
        ArrayList<String> res = Main.getWordLadderBFS("apple", "fangs");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }
    @Test(timeout = 30000)
    public void myTestBFS3() {
        ArrayList<String> res = Main.getWordLadderBFS("veils", "bails");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }

    @Test(timeout = 30000)
    public void myTestBFS4() {
        ArrayList<String> res = Main.getWordLadderBFS("cheek", "smile");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }

    @Test(timeout = 30000)
    public void myTestBFS5() {
        ArrayList<String> res = Main.getWordLadderBFS("shiny", "teeth");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }

    @Test(timeout = 30000)
    public void myTestBFS6() {
        ArrayList<String> res = Main.getWordLadderBFS("cylix", "yugas");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestBFS7() {
        ArrayList<String> res = Main.getWordLadderBFS("wasps", "death");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }

    @Test(timeout = 30000)
    public void myTestBFS8() {
        ArrayList<String> res = Main.getWordLadderBFS("drink", "urine");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }


    @Test(timeout = 30000)
    public void myTestBFS9() {
        ArrayList<String> res = Main.getWordLadderBFS("diner", "feast");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }

    @Test(timeout = 30000)
    public void myTestBFS10() {
        ArrayList<String> res = Main.getWordLadderBFS("craal", "yugas");

        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 20);
    }

    @Test(timeout = 30000)
    public void myTestDFS1() {
        ArrayList<String> res = Main.getWordLadderDFS("start", "rises");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);

    }

    @Test(timeout = 30000)
    public void myTestDFS2() {
        ArrayList<String> res = Main.getWordLadderDFS("apple", "fangs");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS3() {
        ArrayList<String> res = Main.getWordLadderDFS("veils", "bails");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS4() {
        ArrayList<String> res = Main.getWordLadderDFS("cheek", "smile");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS5() {
        ArrayList<String> res = Main.getWordLadderDFS("shiny", "teeth");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS6() {
        ArrayList<String> res = Main.getWordLadderDFS("cylix", "yugas");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS7() {
        ArrayList<String> res = Main.getWordLadderDFS("wasps", "death");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS8() {
        ArrayList<String> res = Main.getWordLadderDFS("drink", "urine");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS9() {
        ArrayList<String> res = Main.getWordLadderDFS("diner", "feast");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test(timeout = 30000)
    public void myTestDFS10() {
        ArrayList<String> res = Main.getWordLadderDFS("craal", "yugas");
        if (res != null) {
            HashSet<String> set = new HashSet<>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }


    @Test(timeout = 30000)
    public void myTestPrintLadder1() {
        ArrayList<String> res = Main.getWordLadderBFS("zebra", "after");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between zebra and after", str);
    }

    @Test(timeout = 30000)
    public void myTestPrintLadder2() {
        ArrayList<String> res = Main.getWordLadderBFS("helix", "zebra");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between helix and zebra", str);
    }

    @Test(timeout = 30000)
    public void myTestPrintLadder3() {
        ArrayList<String> res = Main.getWordLadderBFS("bigot", "zuzim");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between bigot and zuzim", str);
    }

    @Test(timeout = 30000)
    public void myTestPrintLadder4() {
        ArrayList<String> res = Main.getWordLadderBFS("twixt", "zowie");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between twixt and zowie", str);
    }

    @Test(timeout = 30000)
    public void myTestPrintLadder5() {
        ArrayList<String> res = Main.getWordLadderBFS("cylix", "yugas");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between cylix and yugas", str);
    }
}
