package com.luxoft.olshevchenko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileAnalyzerTest {
    FileAnalyzer fileAnalyzer = new FileAnalyzer();
    String path = "src/test/resources/story.txt";

    @Test
    @DisplayName("Test FindWordEntryCount method")
    void testFindWordEntryCount() {
        String word = "lion";
        int expected = 8;
        int actual = fileAnalyzer.findWordEntryCount(path, word);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test FindWordEntryCount method if path specified is invalid")
    void testFindWordEntryCountIfInvalidPath() {
        String word = "lion";
        Assertions.assertThrows(RuntimeException.class, () -> fileAnalyzer.findWordEntryCount(path + "-", word));
    }

    @Test
    @DisplayName("Test FindSentencesWithGivenWord method")
    void testFindSentencesWithGivenWord() {
        String word = "mouse";
        int expected = 4;
        int actual = fileAnalyzer.findSentencesWithGivenWord(path, word);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test FindSentencesWithGivenWord method if path specified is invalid")
    void testFindSentencesWithGivenWordIfInvalidPath() {
        String word = "lion";
        Assertions.assertThrows(RuntimeException.class, () -> fileAnalyzer.findSentencesWithGivenWord(path + "-", word));
    }


}