package com.thyberg.kata.anagrams;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AnagramFinderTest {
    public static final String TEST_FILE = "data_test/wordlist.txt";

    @Test
    void checkLetterSorting() {
        List<String> result = AnagramFinder.readFileToLines(TEST_FILE);
        Map<String, List<String>> anagrams = AnagramFinder.getAnagramList(result);
        Optional<Map.Entry<String, List<String>>> longest = AnagramFinder.getLongestAnagrams(anagrams);
        assertTrue(longest.isPresent());
        assertEquals("aceinnorst", longest.get().getKey());
        assertEquals(3, longest.get().getValue().size());
        assertTrue(longest.get().getValue().contains("containers"));
        assertTrue(longest.get().getValue().contains("crenations"));
        assertTrue(longest.get().getValue().contains("sanctioner"));
        Optional<Map.Entry<String, List<String>>> mostAnagrams = AnagramFinder.getMostAnagrams(anagrams);
        assertTrue(mostAnagrams.isPresent());
        assertEquals("aeprstu", mostAnagrams.get().getKey());
        assertEquals(4, mostAnagrams.get().getValue().size());
        assertTrue(mostAnagrams.get().getValue().contains("pasture"));
        assertTrue(mostAnagrams.get().getValue().contains("uprates"));
        assertTrue(mostAnagrams.get().getValue().contains("upstare"));
        assertTrue(mostAnagrams.get().getValue().contains("uptears"));
    }
}