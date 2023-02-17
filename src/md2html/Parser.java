package md2html;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    static List<ParagraphAble> items;

    static StringBuilder result;

    static MyReader in;

    private static Map<String, Integer> tags;

    public Parser(String input) {
        items = new ArrayList<>();
        result = new StringBuilder();
        in = new MyReader(input);
        tags = new HashMap<>();
    }

    public static List<ParagraphAble> readText(String stop) throws IOException {
        List<ParagraphAble> items = new ArrayList<>();
        while (true) {
            if (in.getChar() == '\n' || in.getChar() == '\r') {
                in.readChar();
                if (in.getChar() == '\n' || in.getChar() == '\r' || !in.hasNext()) {
                    return items;
                }
                items.add(new Text("\n"));
            }
            String token = "" + in.getChar();
            if (!in.hasNext()) {
                in.readChar();
                return items;
            }
            switch (in.getChar()) {
                case '<' -> {
                    items.add(new Text("&lt;"));
                    in.readChar();
                }
                case '>' -> {
                    items.add(new Text("&gt;"));
                    in.readChar();
                }
                case '&' -> {
                    items.add(new Text("&amp;"));
                    in.readChar();
                }
                case '\\' -> {
                    in.readChar();
                    items.add(new Text("" + in.getChar()));
                    in.readChar();
                }
                case '`' -> {
                    in.readChar();
                    if (token.equals(stop)) {
                        return items;
                    } else {
                        if (tags.getOrDefault(token, 0) != 0) {
                            throw new IllegalArgumentException("Incorrect text");
                        }
                        tags.put(token, in.getPosition());
                        items.add(new Code(readText(token)));
                        tags.put(token, 0);
                    }
                }
                case '-' -> {
                    in.readChar();
                    if (in.getChar() == '-') {
                        in.readChar();
                        token = "--";
                        if (token.equals(stop)) {
                            tags.put(token, 0);
                            return items;
                        }
                        if (tags.getOrDefault(token, 0) != 0) {
                            throw new IllegalArgumentException("Incorrect text");
                        }
                        tags.put(token, in.getPosition());
                        items.add(new Strikeout(readText(token)));
                        tags.put(token, 0);
                    } else {
                        items.add(new Text(token));
                    }
                }
                case '*', '_' -> {
                    char prev = in.getPrev();
                    boolean doubleTag = checkDoubleTag(in.getChar());
                    if (doubleTag) {
                        in.readChar();
                        token = token + token;
                        if (token.equals(stop)) {
                            tags.put(token, 0);
                            return items;
                        }
                        if (tags.getOrDefault(token, 0) != 0) {
                            throw new IllegalArgumentException("Incorrect text");
                        }
                        tags.put(token, in.getPosition());
                        items.add(new Strong(readText(token)));
                        tags.put(token, 0);
                    } else {
                        if (Character.isWhitespace(in.getChar()) && Character.isWhitespace(prev)) {
                            items.add(new Text(token));
                        } else {
                            if (token.equals(stop)) {
                                tags.put(token, 0);
                                return items;
                            }
                            if (tags.getOrDefault(token, 0) != 0) {
                                throw new IllegalArgumentException("Incorrect text");
                            }
                            tags.put(token, in.getPosition());
                            List<ParagraphAble> next = readText(token);
                            if (tags.getOrDefault(token, 0) != 0) {
                                items.add(new Text(token));
                                items.addAll(next);
                            } else {
                                items.add(new Emphasis(next));
                            }
                            tags.put(token, 0);
                        }
                    }
                }
                case '[' -> {
                    in.readChar();
                    tags.put(token, in.getPosition());
                    List<ParagraphAble> text = readText("]");
                    if (tags.getOrDefault(token, 0) != 0) {
                        items.add(new Text("["));
                        items.addAll(text);
                    } else {
                        if (in.getChar() == '(') {
                            in.readChar();
                            StringBuilder link = new StringBuilder();
                            while (in.hasNext() && in.getChar() != ')') {
                                link.append(in.getChar());
                                in.readChar();
                            }
                            in.readChar();
                            items.add(new Link(link.toString(), text));
                        }
                        else {
                            items.add(new Text("["));
                            items.addAll(text);
                            items.add(new Text("]"));
                        }
                    }
                    tags.put(token, 0);
                }
                case ']' -> {
                    in.readChar();
                    if (tags.getOrDefault("[", 0) != 0) {
                        int lBound = tags.get("[");
                        tags.put("[", 0);
                        boolean isOk = true;
                        for (Map.Entry<String, Integer> item: tags.entrySet()) {
                            if (lBound <= item.getValue() && item.getValue() < in.getPosition()) {
                                System.err.println(item.getKey() + " " + item.getValue());
                                isOk = false;
                                break;
                            }
                        }
                        if (!isOk) {
                            StringBuilder x = new StringBuilder();
                            for (ParagraphAble item : items) {
                                item.toHtml(x);
                            }
                            System.err.println(x);
                            throw new IllegalArgumentException("Incorrect text");
                        }
                        return items;
                    }
                    items.add(new Text(token));
                }
                default -> {
                    items.add(new Text(token));
                    in.readChar();
                }
            }
        }
    }

    static boolean checkDoubleTag(char tag) throws IOException {
        in.readChar();
        return in.getChar() == tag;
    }

    public String parse() throws IOException {
        in.readChar();
        while (in.hasNext()) {
            while (in.hasNext() && (in.getChar() == '\n' || in.getChar() == '\r')) {
                in.readChar();
            }
            if (!in.hasNext()) {
                break;
            }
            int countHead = 0;
            while (in.getChar() == '#') {
                countHead++;
                in.readChar();
            }
            String tag = "p";
            Part newPart = new Part(new ArrayList<>());
            if (countHead > 0 && Character.isWhitespace(in.getChar())) {
                while (in.hasNext() && Character.isWhitespace(in.getChar())) {
                    in.readChar();
                }
                tag = "h" + countHead;
            } else {
                newPart.add(new Text("#".repeat(countHead)));
            }
            tags = new HashMap<>();
            newPart.addAll(readText("\0"));
            result.append(String.format("<%s>", tag));
            newPart.toHtml(result);
            result.append(String.format("</%s>\n", tag));
        }
        return result.toString();
    }

    public void close() throws IOException {
        in.close();
    }
}
