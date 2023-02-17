package MNKgame;

import java.util.List;

public class Game {
    private final List<Player> players;

    private final int contenders;

    private boolean[] inGame;

    private int playersAlive;

    private final boolean log;

    public Game(List<Player> players, boolean log) {
        this.players = players;
        this.contenders = players.size();
        this.inGame = new boolean[contenders];
        for (int i = 0; i < contenders; i++) {
            inGame[i] = true;
        }
        this.playersAlive = contenders;
        this.log = log;
    }

    public int play(final Board board) {
        int current = 0;
        while (true) {
            final int result = makeMove(board, players.get(current), current + 1);
            if (result >= 0) {
                return result;
            }
            current = nextPlayer(current);
        }
    }

    private int nextPlayer(final int current) {
        for (int i = 1; i < contenders; i++) {
            if (inGame[(current + i) % contenders]) {
                return (current + i) % contenders;
            }
        }
        throw new RuntimeException("Broken players");
    }

    private int makeMove(Board board, Player player, int no) {
        if (playersAlive == 1) {
            System.out.printf("Winner is player #%d%n", no);
            return no;
        }
        Move move = player.move(board.getPosition());
        Result result = board.makeMove(move, inGame);
        if (log) {
            System.out.printf(
                    "Player #%d, move: (%d, %d, %s)%n",
                    no, move.getRow() + 1, move.getCol() + 1, move.getCell()
                    );
            System.out.println(board);
        }
        if (result == Result.WIN) {
            System.out.printf("Winner is player #%d%n", no);
            return no;
        } else if (result == Result.DRAW) {
            return 0;
        } else if (result == Result.LOSE) {
            playersAlive--;
            inGame[no - 1] = false;
            writeAlive();
            return -2;
        } else {
            return -1;
        }
    }

    private void writeAlive() {
        System.out.printf("%d alive players:%n", playersAlive);
        for (int i = 0; i < contenders; i++) {
            if (inGame[i]) {
                System.out.printf("Player #%d%n", i + 1);
            }
        }
    }
}
