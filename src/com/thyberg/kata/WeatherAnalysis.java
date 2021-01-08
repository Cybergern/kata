package com.thyberg.kata;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherAnalysis implements SpreadAnalysis {

    public static final String FILE_NAME = "data/weather.dat";

    public String getFilename() {
        return FILE_NAME;
    }

    public String getOutputString() {
        return "Day with smallest temperature spread is %s, spread is only %d degrees";
    }

    public List<SpreadData> makeSpreadData(List<String[]> partList) {
        return partList.stream()
                .map(parts -> new SpreadData(parts[0],
                        Integer.parseInt(parts[1].replaceAll("\\*", "")),
                        Integer.parseInt(parts[2].replaceAll("\\*", ""))))
                .collect(Collectors.toList());
    }

}

