package wordStat;

import scanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class WordStatWordsShingles {

    public static Map<String, Integer> count;

    private static void addAll(String x) {
        if (x.isEmpty()) {
            return;
        }
        if (x.length() < 3) {
            count.merge(x.toLowerCase(), 1, Integer::sum);
        }
        for (int i = 3; i <= x.length(); i++) {
            count.merge(x.substring(i - 3, i).toLowerCase(), 1, Integer::sum);
        }
    }

    public static void main(String[] args) {
        try {
            MyScanner reader = MyScanner.newScanner(args[0]);
            count = new TreeMap<>();
            while (reader.hasNext()) {
                String word = reader.readWord();
                addAll(word);
            }
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                for (Map.Entry<String, Integer> i : count.entrySet()) {
                    writer.write(i.getKey() + " " + i.getValue());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Input file reading error: " + e.getMessage());
        }
    }
}