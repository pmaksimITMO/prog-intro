package wordStat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class WordStatInput {

    public static String[] words;

    public static int pos;

    private static void add(String val) {
        if (pos >= words.length) {
            words = Arrays.copyOf(words, Math.max(1, 2 * pos));
        }
        words[pos] = val;
        pos++;
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
                try {
                    Map<String, Integer> count = new TreeMap<>();
                    StringBuilder x = new StringBuilder();
                    char[] chars = new char[1000];
                    words = new String[1];
                    pos = 0;
                    while (true) {
                        int len = reader.read(chars);
                        if (len == -1) {
                            if (!x.isEmpty()) {
                                count.merge(x.toString().toLowerCase(), 1, Integer::sum);
                                add(x.toString().toLowerCase());
                            }
                            break;
                        }
                        int l = 0;
                        for (int i = 0; i < len; i++) {
                            if (!Character.isLetter(chars[i])
                                    && Character.getType(chars[i]) != Character.DASH_PUNCTUATION
                                    && !Character.toString(chars[i]).equals("'")) {
                                x.append(chars, l, i - l);
                                if (!x.isEmpty()) {
                                    count.merge(x.toString().toLowerCase(), 1, Integer::sum);
                                    add(x.toString().toLowerCase());
                                    x.delete(0, x.length());
                                }
                                l = i + 1;
                            }
                        }
                        if (l != len) x.append(chars, l, len - l);
                    }
                    for (int i = 0; i < pos; i++) {
                        int col = count.get(words[i]);
                        if (col != 0) {
                            writer.write(words[i] + " " + col);
                            writer.newLine();
                            count.put(words[i], 0);
                        }
                    }
                } finally {
                    writer.close();
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Input file reading error: " + e.getMessage());
        }
    }
}