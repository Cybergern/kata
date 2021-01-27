package com.thyberg.kata.analysis;

import java.util.List;

public interface SpreadAnalysis {
    List<SpreadData> makeSpreadData(List<String[]> partList);
    String getOutputString();
    String getFilename();
}
