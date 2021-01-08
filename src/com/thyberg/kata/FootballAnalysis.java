package com.thyberg.kata;

import java.util.List;
import java.util.stream.Collectors;

public class FootballAnalysis implements SpreadAnalysis {
    public static final String FILE_NAME = "data/football.dat";

    public String getFilename() {
        return FILE_NAME;
    }

    public String getOutputString() {
        return "Team with smallest goal spread is %s, spread is only %d goal(s)";
    }

    public List<SpreadData> makeSpreadData(List<String[]> partList) {
        return partList.stream()
                .map(parts -> new SpreadData(parts[1],
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[8])))
                .collect(Collectors.toList());
    }


}

