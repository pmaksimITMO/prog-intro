package MNKgame;

public class SequentialPlayer implements Player {
    @Override
    public Move move(Position position) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
