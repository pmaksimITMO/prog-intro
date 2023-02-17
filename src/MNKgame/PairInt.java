package MNKgame;

public class PairInt {
    private final int first;

    private final int second;

    public PairInt(int x, int y) {
        this.first = x;
        this.second = y;
    }

    public PairInt() {
        this(0, 0);
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
