package MNKgame;

public interface Position {
    Cell getTurn();
    int getM();
    int getN();

    boolean isValid(Move move);
}
