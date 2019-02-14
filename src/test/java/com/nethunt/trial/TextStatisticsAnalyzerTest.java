package com.nethunt.trial;

import com.nethunt.trial.data.TextItemStatistics;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TextStatisticsAnalyzerTest {
    private final static String[] TEXT = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ornare rutrum sodales. ",
            "Aliquam nec scelerisque dui,vel molestie mi.Aliquam a neque et leo mattis maximus vel ut eros.",
            "Suspendisse sed vulputate ipsum. Cras vitae ultricies magna.",
            "Pellentesque lacinia quam et risus bibendum, sodales dapibus est lobortis. Proin vestibulum metus nunc.",
            "Mauris lacinia placerat massa at posuere. Duis fermentum est vitae ligula ultrices, id pretium mi lobortis.",
            "Proin ultrices ipsum vitae lectus elementum, in dignissim ligula vulputate."
    };
    private final static HashMap<String, Integer> FREQUENCY_TABLE = new HashMap<String, Integer>() {
        {
            put(",", 5);
            put(".", 11);
            put("aliquam", 2);
            put("cras", 1);
            put("duis", 1);
            put("lorem", 1);
            put("mauris", 1);
            put("pellentesque", 1);
            put("proin", 2);
            put("suspendisse", 1);
            put("vivamus", 1);
            put("a", 1);
            put("adipiscing", 1);
            put("amet", 1);
            put("at", 1);
            put("bibendum", 1);
            put("consectetur", 1);
            put("dapibus", 1);
            put("dignissim", 1);
            put("dolor", 1);
            put("dui", 1);
            put("elementum", 1);
            put("elit", 1);
            put("eros", 1);
            put("est", 2);
            put("et", 2);
            put("fermentum", 1);
            put("id", 1);
            put("in", 1);
            put("ipsum", 3);
            put("lacinia", 2);
            put("lectus", 1);
            put("leo", 1);
            put("ligula", 2);
            put("lobortis", 2);
            put("magna", 1);
            put("massa", 1);
            put("mattis", 1);
            put("maximus", 1);
            put("metus", 1);
            put("mi", 2);
            put("molestie", 1);
            put("nec", 1);
            put("neque", 1);
            put("nunc", 1);
            put("ornare", 1);
            put("placerat", 1);
            put("posuere", 1);
            put("pretium", 1);
            put("quam", 1);
            put("risus", 1);
            put("rutrum", 1);
            put("scelerisque", 1);
            put("sed", 1);
            put("sit", 1);
            put("sodales", 2);
            put("ultrices", 2);
            put("ut", 1);
            put("vel", 2);
            put("vestibulum", 1);
            put("vitae", 3);
            put("vulputate", 2);
            put("ultricies", 1);
        }
    };
    private static final double TOTAL_WORD_COUNT = 93;

    private TextStatisticsAnalyzer analyzer = new TextStatisticsAnalyzer();

    @Test
    public void testRunAnalysis() {
        List<TextItemStatistics> result = analyzer.runAnalysis(Stream.of(TEXT));

        assertEquals(FREQUENCY_TABLE.size(), result.size());
        result.forEach(item -> {
            assertEquals(0, Math.round((FREQUENCY_TABLE.get(item.getTextItem()).doubleValue() / TOTAL_WORD_COUNT- item.getFrequency()) * 1000), "Text element assertion: " + item.getTextItem());
        });
    }

    @Test
    public void testSplitLine() {
        Stream<String> result = analyzer.splitLine("Aliquam nec scelerisque dui,vel molestie mi.Aliquam a neque et leo mattis maximus vel ut eros.");
        String[] words = result.toArray(String[]::new);

        assertEquals(20, words.length);
        assertEquals(",", words[19]);
        assertEquals(".", words[18]);
        assertEquals(".", words[17]);
        assertEquals("eros", words[16]);
    }
}