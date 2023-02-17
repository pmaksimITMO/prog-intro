package scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyScanner {
    private Reader reader;

    private int countLines;

    private char current;

    private boolean begin;

    private String separator;

    private void init() {
        countLines = 0;
        separator = System.lineSeparator();
        current = '\\';
        begin = true;
    }

    private MyScanner() {
        reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
        init();
    }

    private MyScanner(String fileIn) {
        try {
            reader = new InputStreamReader(new FileInputStream(fileIn), StandardCharsets.UTF_8);
            init();
        } catch(IOException e) {
            System.out.println("Input file reading error: " + e.getMessage());
        }
    }

    void close() throws IOException{
        reader.close();
    }

    public boolean hasNext() {
        try {
            if (current == '\0') {
                close();
                return false;
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    private boolean isGoodChar(char cur) {
        return Character.isLetter(cur)
                || Character.getType(cur) == Character.DASH_PUNCTUATION
                || cur == '\'';
    }

    public void readChar() {
        try{
            if (!hasNext()) {
                return;
            }
            int now = reader.read();
            begin = false;
            if (separator.length() == 2 && now != -1 && (char)now == '\r') {
                countLines--;
                readChar();
            }
            if (current == '\n' || current == '\r') {
                countLines++;
            }
            current = (now == -1 ? '\0' : (char)now);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String readWord() {
        if (!hasNext()) {
            return "";
        }
        while (hasNext() && !isGoodChar(current)) {
            readChar();
        }
        StringBuilder word = new StringBuilder();
        while (isGoodChar(current)) {
            word.append(current);
            readChar();
        }
        return word.toString();
    }

    public String readLine() {
        if (begin) {
            readChar();
        }
        StringBuilder line = new StringBuilder();
        while (hasNext() && current != '\r' && current != '\n') {
            line.append(current);
            readChar();
        }
        readChar();
        countLines++;
        return line.toString();
    }

    public String nextLineAbc() {
        StringBuilder line = new StringBuilder(readLine());
        for (int i = 0; i < line.length(); i++) {
            if (Character.isLetter(line.charAt(i))) {
                line.setCharAt(i, Character.toLowerCase(line.charAt(i)));
                if ((int) line.charAt(i) - (int) 'a' <= 9) {
                    line.setCharAt(i, (char) ((int) line.charAt(i) - (int) 'a' + (int) '0'));
                }
            }
        }
        return line.toString();
    }

    public int getCountLines() {
        return this.countLines;
    }

    public static MyScanner newScanner(String fileInput) {
        return new MyScanner(fileInput);
    }

    public static MyScanner newScanner() {
        return new MyScanner();
    }

    public static MyScanner newScanner(String s, int id) {
        return new MyScanner(s, id);
    }

    private MyScanner(String s, int id) {
        reader = new StringReader(s);
        init();
    }
}
