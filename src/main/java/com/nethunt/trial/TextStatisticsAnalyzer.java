package com.nethunt.trial;

import com.nethunt.trial.data.TextItemStatistics;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextStatisticsAnalyzer {
    private static final char[] ADDITIONAL_WORDS_SEPARATORS = {'.', ',', ';'};
    private static final char[] MAIN_WORDS_SEPARATORS = {' '};
    private static final String WORDS_SEPARATORS = new String(ArrayUtils.addAll(MAIN_WORDS_SEPARATORS, ADDITIONAL_WORDS_SEPARATORS));

    /**
     * Analyze text stream and return text elements with statistics how frequent this item can be met in the text
     * @param textStream input text stream
     * @return ordered List of TextItemStatistics, sorted by text items frequency. Most frequent is on the first place, less on the last.
     */
    public List<TextItemStatistics> runAnalysis(Stream<String> textStream) {
        HashMap<String, Integer> primaryStatistics = textStream.parallel().flatMap(this::splitLine)
                .map(word -> word.toLowerCase()).collect(HashMap::new, this::addWordToHash, this::joinMaps);
        final double totalCount = primaryStatistics.entrySet().stream().parallel()
                .reduce(0, (total, entry) -> total + entry.getValue(), (total1, total2) -> total1 + total2).doubleValue();
        List<TextItemStatistics> statisticsList = primaryStatistics.entrySet().stream().parallel()
                .map(entry -> new TextItemStatistics(entry.getKey(), entry.getValue().doubleValue() / totalCount))
                .sorted((textItem1, textItem2) -> {return -Double.compare(textItem1.getFrequency(), textItem2.getFrequency());})
                .collect(Collectors.toList());

        return statisticsList;
    }

    void addWordToHash(Map<String, Integer> map, String word) {
        Integer count = map.get(word);
        if (count != null) {
            map.put(word, count + 1);
        } else {
            map.put(word, 1);
        }
    }

    void joinMaps(Map<String, Integer> map1, Map<String, Integer> map2) {
        map2.entrySet().stream().forEach(entry -> {
            Integer currentValue = map1.putIfAbsent(entry.getKey(), entry.getValue());
            if (currentValue != null) {
                map1.put(entry.getKey(), entry.getValue() + currentValue);
            }
        });
    }

    Stream<String> splitLine(String line) {
        String[] words = StringUtils.split(line, WORDS_SEPARATORS);
        for (char symbol : ADDITIONAL_WORDS_SEPARATORS) {
            int matchesCount = StringUtils.countMatches(line, symbol);
            if (matchesCount > 0) {
                String[] additions = new String[matchesCount];
                Arrays.fill(additions, Character.toString(symbol));
                words = ArrayUtils.addAll(words, additions);
            }
        }
        return Arrays.stream(words);
    }
}