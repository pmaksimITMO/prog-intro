package MNKgame;

import java.util.List;

public class MultiPlayerGame {
    private final List<Player> players;

    private final int m, n, k;

    private final List<PairInt> regret;

    public MultiPlayerGame(List<Player> players, int m, int n, int k, List<PairInt> regret) {
        this.players = players;
        this.m = m;
        this.n = n;
        this.k = k;
        this.regret = regret;
        if (k > n || k > m) {
            throw new RuntimeException("Invalid board values");
        }
    }

    public int playNewGame() {
        MNKBoard mnkBoard = new MNKBoard(m, n, k);
        mnkBoard.regretCells(regret);
        int result = new Game(
                players,
                true
        ).play(mnkBoard);
        if (result >= 0) {
            return result;
        }
        throw new AssertionError("Unknown result " + result);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
