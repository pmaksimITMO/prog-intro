package md2html;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) throws IOException {
        Parser solve = new Parser(args[0]);
        String result = "";
        try {
            result = solve.parse();
        } finally {
            solve.close();
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(args[1], StandardCharsets.UTF_8))) {
            writer.write(result);
        } catch (FileNotFoundException e) {
            System.out.println("Output file error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
