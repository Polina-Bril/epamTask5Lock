package com.epam.task5.parser;

import java.util.ArrayList;
import java.util.List;

public class DataParser {
    private static final String DELIMITER = "\\s";

    public List<Integer> parseLineToNumberList(String line) {
        List<Integer> numbers = new ArrayList<>();
        String[] numbersString = line.split(DELIMITER);
        for (String numberString : numbersString) {
            numbers.add(Integer.parseInt(numberString));
        }
        return numbers;
    }
}
