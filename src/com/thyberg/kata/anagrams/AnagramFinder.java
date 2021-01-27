package com.thyberg.kata.anagrams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnagramFinder {
    private static final String FILE_NAME = "data/wordlist.txt";

    public static void main(String[] args) {
        List<String> result = AnagramFinder.readFileToLines(FILE_NAME);
        Map<String,List<String>> anagrams = AnagramFinder.getAnagramList(result);
        anagrams.forEach((s, strings) -> System.out.printf("%s -> %s\n", s, String.join(", ", strings)));
        getMostAnagrams(anagrams).ifPresent(e -> System.out.printf("The longest list of anagrams belongs to %s -> %s\n",
                e.getKey(), String.join(", ", e.getValue())));
        getLongestAnagrams(anagrams).ifPresent(e -> System.out.printf("The longest anagrams are %s -> %s",
                e.getKey(), String.join(", ", e.getValue())));
    }

    public static Map<String, List<String>> getAnagramList(List<String> result) {
        Map<String, List<String>> wordMap = new HashMap<>();
        for (String word : result) {
            String sorted = AnagramFinder.sortLetters(word);
            wordMap.putIfAbsent(sorted, new ArrayList<>());
            wordMap.get(sorted).add(word);
        }
        return wordMap.entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Optional<Map.Entry<String, List<String>>> getMostAnagrams(Map<String, List<String>> anagrams) {
        return anagrams.entrySet()
                .stream()
                .max(Comparator.comparingInt(o -> o.getValue().size()));
    }

    public static Optional<Map.Entry<String, List<String>>> getLongestAnagrams(Map<String, List<String>> anagrams) {
        return anagrams.entrySet()
                .stream()
                .max(Comparator.comparingInt(o -> o.getKey().length()));
    }

    private static String sortLetters(String word) {
        return Arrays.stream(word.split("")).sorted().collect(Collectors.joining());
    }

    public static List<String> readFileToLines(String file_name) {
        List<String> result = Collections.emptyList();
        try (Stream<String> lines = Files.lines(Paths.get(file_name), StandardCharsets.ISO_8859_1)) {
            result = lines.map(String::trim).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}
