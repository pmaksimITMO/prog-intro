package DisAsm;

public class Section {
    private String name;
    private int offset, size, entSize, addr, type;
    private char[] chars;

    public String getName() {
        return name;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public int getEntSize() {
        return entSize;
    }

    public int getAddr() {
        return addr;
    }

    public int getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getChar(int position) {
        return chars[position];
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSize(int size) {
        this.size = size;
        this.chars = new char[size];
    }

    public void setEntSize(int entSize) {
        this.entSize = entSize;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setChar(int id, char value) {
        chars[id] = value;
    }
}
