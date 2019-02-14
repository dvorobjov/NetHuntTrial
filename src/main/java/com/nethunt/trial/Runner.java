package com.nethunt.trial;

import com.nethunt.trial.data.TextItemStatistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("File path is absent. \nUsage: com.nethunt.trial.Runner ./someDirectory/someFile.txt");
            return;
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("File " + args[0] + " doesn't exists");
            return;
        }

        TextStatisticsAnalyzer analyzer = new TextStatisticsAnalyzer();
        long startTime = System.currentTimeMillis();
        List<TextItemStatistics> textItemStatistics = analyzer.runAnalysis(in.lines());
        long executionTime = System.currentTimeMillis() - startTime;

        printTextStatistics(textItemStatistics);

        System.out.println("Execution time, ms: " + executionTime);
    }


    private static void printTextStatistics(List<TextItemStatistics> textItemStatistics) {
        System.out.println("Text statistics:");
        System.out.println("-------------------------------------------------------");
        textItemStatistics.forEach(item -> System.out.println(item.getTextItem() + " [" + item.getTextItemLength()+ "]: " + item.getFrequency()));
        System.out.println("-------------------------------------------------------");
    }
}
