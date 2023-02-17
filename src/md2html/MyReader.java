package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyReader {

    private Reader reader;

    private char current, prev;

    private String separator;

    private int position;

    public MyReader(String name) {
        try{
            reader = new BufferedReader(new FileReader(name, StandardCharsets.UTF_8));
            current = '\n';
            prev = '\n';
            separator = System.lineSeparator();
            position = 0;
        } catch (IOException e) {
            System.out.println("Input file error:" + e.getMessage());
        }
    }

    public void close() throws IOException {
        reader.close();
    }

    public void readChar() throws IOException {
        prev = current;
        position++;
        if (!hasNext()) {
            return;
        }
        int now = reader.read();
        if (separator.length() == 2 && now != -1 && (char)now == '\r') {
            readChar();
        }
        current = (now == -1 ? '\0' : (char)now);
    }

    public char getChar() {
        return current;
    }

    public char getPrev() {
        return prev;
    }

    public int getPosition() {
        return position;
    }

    public boolean hasNext() {
        return current != '\0';
    }

}
