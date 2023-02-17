package DisAsm;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ElfFile {
    private final List<Section> sections;
    private final List<Symbol> symtab;
    private int shoff, shnum, shstrndx, symbols, shstrTableOffset, stringTableOffset;

    public ElfFile() {
        this.sections = new ArrayList<>();
        this.symtab = new ArrayList<>();
    }

    public void printSymtab(PrintWriter out) {
        out.printf("%s %-15s %7s %-8s %-8s %-8s %6s %s\n", "Symbol", "Value", "Size", "Type", "Bind", "Vis", "Index", "Name");
        for (int i = 0; i < symbols; i++) {
            Symbol symbol = symtab.get(i);
            out.printf("[%4d] 0x%-15x %5d %-8s %-8s %-8s %6s %s\n",
                    i, symbol.getValue(), symbol.getSize(), symbol.getType(), symbol.getBind(), symbol.getVis(), symbol.getIndex(), symbol.getName());
        }
    }

    public void setShoff(int shoff) {
        this.shoff = shoff;
    }

    public void setShnum(int shnum) {
        this.shnum = shnum;
    }

    public void setShstrndx(int shstrndx) {
        this.shstrndx = shstrndx;
    }

    public void setSymbols(int symbols) {
        this.symbols = symbols;
    }

    public int getShoff() {
        return shoff;
    }

    public int getShnum() {
        return shnum;
    }

    public int getShstrndx() {
        return shstrndx;
    }

    public int getSymbols() {
        return symbols;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public Section getSection(int id) {
        return sections.get(id);
    }

    public Symbol getSymbol(int id) {
        return symtab.get(id);
    }

    public void setShstrTableOffset(int offset) {
        shstrTableOffset = offset;
    }

    public int getShstrTableOffset() {
        return shstrTableOffset;
    }

    public void setStringTableOffset(int offset) {
        stringTableOffset = offset;
    }

    public int getStringTableOffset() {
        return stringTableOffset;
    }

    public void addSymbol(Symbol symbol) {
        symtab.add(symbol);
        symbols++;
    }
}
