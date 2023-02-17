package wspp;

import java.util.Arrays;
public class IntList {
    private int position, coastline, countAll, firstMeet;

    private int[] buf;

    public IntList() {
        this.buf = new int[1];
        this.position = 0;
        this.coastline = 0;
        this.countAll = 0;
        this.firstMeet = -1;
    }

    public IntList(int size, int line) {
        this.buf = new int[size];
        this.position = 0;
        this.coastline = line;
        this.countAll = 0;
        this.firstMeet = -1;
    }

    public void add(int val) {
        if (position == buf.length) {
            buf = Arrays.copyOf(buf, 2 * position);
        }
        buf[position++] = val;
        countAll++;
    }

    public void add(int val, int line, int meet) {
        if (position == buf.length) {
            buf = Arrays.copyOf(buf, 2 * position);
        }
        buf[position++] = val;
        coastline = line;
        countAll++;
        if (firstMeet == -1) {
            firstMeet = meet;
        }
    }

    public void replaceLast(int val) {
        buf[position - 1] = val;
        countAll++;
    }

    public int getLine() {
        return this.coastline;
    }

    public int getLength() {
        return this.position;
    }

    public int getCountAll() {
        return this.countAll - 1;
    }

    public int getFirstMeet() {
        return this.firstMeet;
    }

    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        for (int i = 0; i < position; i++) {
            items.append(buf[i]);
            if (i != position - 1) items.append(' ');
        }
        return items.toString();
    }
}
