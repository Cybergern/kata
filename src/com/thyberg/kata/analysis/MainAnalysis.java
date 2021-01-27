package com.thyberg.kata.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainAnalysis {
    public static void main(String[] args) {
        MainAnalysis.runAnalysis(new FootballAnalysis(), new WeatherAnalysis());
    }

    public static void runAnalysis(SpreadAnalysis... analysises) {
        for (SpreadAnalysis a: analysises) {
            List<String> result = MainAnalysis.readFileToLines(a.getFilename());
            List<String[]> partList = MainAnalysis.getParts(result);
            List<SpreadData> spreadData = a.makeSpreadData(partList);
            SpreadData smallestSpread = MainAnalysis.getSmallestSpread(spreadData);
            System.out.printf(a.getOutputString(), smallestSpread.getName(),
                    smallestSpread.getSpread());
            System.out.println();

        }
    }

    public static List<String> readFileToLines(String file_name) {
        List<String> result = Collections.emptyList();
        try (Stream<String> lines = Files.lines(Paths.get(file_name))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String[]> getParts(List<String> lines) {
        return lines.stream()
                .filter(row -> Pattern.matches("^\\d+.*", row.trim()))
                .map(row -> row.trim().split("\\s+"))
                .collect(Collectors.toList());
    }

    public static SpreadData getSmallestSpread(List<SpreadData> data) {
        return data.stream()
                .min(Comparator.comparing(SpreadData::getSpread))
                .orElseThrow(NoSuchElementException::new);
    }

}

