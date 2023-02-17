package wspp;

import scanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {

    private static int add(Map<String, IntList> words, List<String> items, int pos, String x) {
        if (x.isEmpty()) {
            return 0;
        }
        IntList cur = words.get(x.toLowerCase());
        if (cur == null) {
            cur = new IntList(1, 0);
            items.add(x.toLowerCase());
            words.put(x.toLowerCase(), cur);
        }
        cur.add(pos + 1);
        return 1;
    }

    public static void main(String[] args) {
        try{
            MyScanner reader = MyScanner.newScanner(args[0]);
            Map<String, IntList> words = new HashMap<>();
            List<String> items = new ArrayList<>();
            int pos = 0;
            String word;
            while (reader.hasNext()) {
                word = reader.readWord();
                pos += add(words, items, pos, word);
            }
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                for (String item : items) {
                    writer.write(item + " " + words.get(item).getLength() + " " + words.get(item));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}