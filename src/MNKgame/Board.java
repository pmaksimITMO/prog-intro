package MNKgame;

public interface Board {
    Position getPosition();

    Result makeMove(Move move, boolean[] inGame);
}
