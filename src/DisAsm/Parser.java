package DisAsm;

public class Parser {
    private final char[] chars;
    private final ElfFile file;

    public Parser(byte[] bytes) {
        chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) (bytes[i] < 0 ? bytes[i] + 256 : bytes[i]);
        }
        file = new ElfFile();
    }

    private int charsToInt(int begin, int len) {
        int result = 0;
        for (int i = len - 1; i >= 0; i--) {
            result = ((result << 8) + chars[begin + i]);
        }
        return result;
    }

    public ElfFile parse() {
        checkForValid();
        file.setShoff(charsToInt(32, 4));
        file.setShnum(charsToInt(48, 2));
        file.setShstrndx(charsToInt(50, 2));
        parseSections();
        return file;
    }

    public void checkForValid() {
        if (chars[0] != 0x7f || chars[1] != 0x45 || chars[2] != 0x4c || chars[3] != 0x46) {
            throw new AssertionError("Not an Elf file.");
        }
        if (chars[4] != 1) {
            throw new AssertionError("Not an 32-bit Elf file.");
        }
        if (chars[5] != 1) {
            throw new AssertionError("Not Little endian configuration");
        }
        if (chars[16] != 2) {
            throw new AssertionError("Не является исполняемым файлом");
        }
        if (chars[18] != (char) 0xF3) {
            throw new AssertionError("No RISC-V configuration");
        }
        if (chars[20] != 1) {
            throw new AssertionError("Incorrect Elf version");
        }
    }

    private void parseSections() {
        setMainInfo();
        setNames();
        for (int i = 0; i < file.getShnum(); i++) {
            Section current = file.getSection(i);
            if (current.getName().equals(".symtab")) {
                parseSymTab(current);
            }
        }
    }

    private void setNames() {
        for (int i = 0; i < file.getShnum(); i++) {
            int begin = file.getShoff() + 40 * i;
            Section current = file.getSection(i);
            current.setName(makeName(charsToInt(begin, 4) + file.getShstrTableOffset()));
            if (current.getType() != 8) {
                for (int j = current.getOffset(); j < current.getSize() + current.getOffset(); j++) {
                    current.setChar(j - current.getOffset(), chars[j]);
                }
            }
            if (current.getName().equals(".strtab")) {
                file.setStringTableOffset(current.getOffset());
            }
        }
    }

    private void setMainInfo() {
        for (int i = 0; i < file.getShnum(); i++) {
            int begin = file.getShoff() + 40 * i;
            Section section = new Section();
            section.setType(charsToInt(begin + 4, 4));
            section.setAddr(charsToInt(begin + 12, 4));
            section.setOffset(charsToInt(begin + 16, 4));
            section.setSize(charsToInt(begin + 20, 4));
            section.setEntSize(charsToInt(begin + 36, 4));
            file.addSection(section);
            if (i == file.getShstrndx()) {
                file.setShstrTableOffset(section.getOffset());
            }
        }
    }

    private void parseSymTab(Section current) {
        for (int i = 0; i < current.getSize() / 16 ; i++) {
            int begin = current.getOffset() + 16 * i;
            Symbol symbol = new Symbol();
            symbol.setOffset(begin);
            symbol.setName(makeName(charsToInt(begin, 4) + file.getStringTableOffset()));
            symbol.setValue(charsToInt(begin + 4, 4));
            symbol.setSize(charsToInt(begin + 8, 4));
            symbol.setBind(charsToInt(begin + 12, 1));
            symbol.setType(charsToInt(begin + 12, 1));
            symbol.setVis(charsToInt(begin + 13, 1));
            symbol.setIndex(charsToInt(begin + 14, 2));
            file.addSymbol(symbol);
        }
    }

    private String makeName(int position) {
        StringBuilder result = new StringBuilder();
        while (chars[position] != 0) {
            result.append(chars[position++]);
        }
        return result.toString();
    }
}
