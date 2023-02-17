package MNKgame;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    @Override
    public Move move(Position position) {
        final Move move = new Move(
            random.nextInt(position.getM()),
            random.nextInt(position.getN()),
            position.getTurn()
        );
        return move;
    }
}
