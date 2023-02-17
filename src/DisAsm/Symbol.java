package DisAsm;

public class Symbol {
    private String name, bind, type, vis, index;
    private int value, size, offset;

    public String getName() {
        return name;
    }

    public String getBind() {
        return bind;
    }

    public String getType() {
        return type;
    }

    public String getVis() {
        return vis;
    }

    public String getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public int getOffset() {
        return offset;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBind(int info) {
        switch (info >> 4) {
            case 0 -> bind = "LOCAL";
            case 1 -> bind = "GLOBAL";
            case 2 -> bind = "WEAK";
            case 13 -> bind = "LOPROC";
            case 15 -> bind = "HIPROC";
            default -> throw new IllegalArgumentException("Unexpected bind value: " + (info >> 4));
        }
    }

    public void setType(int info) {
        switch (info & 0xf) {
            case 0 -> type = "NOTYPE";
            case 1 -> type = "OBJECT";
            case 2 -> type = "FUNC";
            case 3 -> type = "SECTION";
            case 4 -> type = "FILE";
            case 13 -> type = "LOPROC";
            case 15 -> type = "HIPROC";
            default -> throw new IllegalArgumentException("Unexpected type value: " + (info & 0xf));
        }
    }

    public void setVis(int info) {
        switch (info & 0x3) {
            case 0 -> vis = "DEFAULT";
            case 1 -> vis = "INTERNAL";
            case 2 -> vis = "HIDDEN";
            case 3 -> vis = "PROTECTED";
            default -> throw new IllegalArgumentException("Unexpected vis value: " + (info & 0x3));
        }
    }

    public void setIndex(int info) {
        switch (info) {
            case 0 -> index = "UNDEF";
            case 0xff00 -> index = "BEFORE";
            case 0xff01 -> index = "AFTER";
            case 0xff02 -> index = "AMD64_LCOMMON";
            case 0xff1f -> index = "HIPROC";
            case 0xff20 -> index = "LOOS";
            case 0xff3f -> index = "HIOS";
            case 0xfff1 -> index = "ABS";
            case 0xfff2 -> index = "COMMON";
            case 0xffff -> index = "XINDEX";
            default -> index = Integer.toString(info);
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
