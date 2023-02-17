package MNKgame;

public class BoardPosition implements Position{
    private final Position position;

    public BoardPosition(Position position) {
        this.position = position;
    }

    @Override
    public Cell getTurn() {
        return position.getTurn();
    }

    @Override
    public int getM() {
        return position.getM();
    }

    @Override
    public int getN() {
        return position.getN();
    }

    @Override
    public boolean isValid(Move move) {
        return position.isValid(move);
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
