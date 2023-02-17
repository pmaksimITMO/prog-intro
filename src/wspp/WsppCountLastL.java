package wspp;

import scanner.MyScanner;
import wspp.IntList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppCountLastL {
    private static void add(final Map <String, IntList> words, final int pos, final String word, final int curLine, final int curMeet) {
        IntList cur = words.get(word.toLowerCase());
        if (cur == null) {
            cur = new IntList(1, curLine);
            cur.add(pos, curLine, curMeet);
            words.put(word.toLowerCase(), cur);
        }
        if (curLine == cur.getLine()) {
            cur.replaceLast(pos);
        } else {
            cur.add(pos, curLine, curMeet);
        }
    }

    public static void main(final String[] args) {
        try {
            final MyScanner reader = MyScanner.newScanner(args[0]);
            final Map<String, IntList> words = new HashMap<>();
            String nextWord;
            int pos = 0, countLines = 0, countMeets = 0;
            while (reader.hasNext()) {
                nextWord = reader.readWord();
                if (reader.getCountLines() != countLines) {
                    countLines++;
                    pos = 0;
                }
                if (nextWord.isEmpty()) {
                    break;
                }
                add(words, ++pos, nextWord, countLines, countMeets++);
            }
            try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
                final List<String> items = new ArrayList<>();
                for (final Map.Entry<String, IntList> entry : words.entrySet()) {
                    items.add(entry.getKey());
                }
                items.sort(new Comparator<String>() {
                    @Override
                    public int compare(final String s1, final String s2) {
                        final IntList l1 = words.get(s1);
                        final IntList l2 = words.get(s2);
                        if (l1.getCountAll() == l2.getCountAll()) {
                            return l1.getFirstMeet() - l2.getFirstMeet();
                        }
                        return l1.getCountAll() - l2.getCountAll();
                    }
                });
                for (String item : items) {
                    writer.write(item + " " + words.get(item).getCountAll() + " " + words.get(item));
                    writer.newLine();
                }
            }
        } catch (final IOException e) {
            System.out.println("Input/output file error" + e.getMessage());
        }
    }
}
