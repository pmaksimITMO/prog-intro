package DisAsm;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new AssertionError("Try: java Main <имя_входного_elf_файла> <имя_выходного_файла>");
        }
        ElfFile elfFile;
        try (FileInputStream in = new FileInputStream(args[0])) {
            try {
                elfFile = (new Parser(in.readAllBytes())).parse();
            } catch (Exception e) {
                in.close();
                throw e;
            }
        } catch (FileNotFoundException e) {
            throw new AssertionError("File not found");
        } catch (IOException e) {
            throw new AssertionError("Incorrect input");
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(args[1]))) {
            try {
                Disassembler dis = new Disassembler(elfFile);
                out.println(".text");
                dis.disassembly(out);
                out.println("\n.symtab");
                elfFile.printSymtab(out);
            } catch (Exception e) {
                out.close();
                throw e;
            }
        } catch (FileNotFoundException e) {
            throw new AssertionError("File not found");
        } catch (IOException e) {
            throw new AssertionError("Incorrect input");
        }
    }
}
