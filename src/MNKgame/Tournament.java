package MNKgame;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tournament {
    private final List<Player> players;

    private final List<PairInt> regret;

    private final Scanner in;

    private final PrintStream out;

    private int contenders;

    private final int m, n, k;

    private int[] result;

    public Tournament(int m, int n, int k, List<PairInt> regret) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.regret = regret;
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
        players = new ArrayList<>();
        contenders = 0;
    }

    public void start() {
        out.print("Enter contenders number:\n");
        contenders = in.nextInt();
        result = new int[contenders];
        out.println("""
                Enter players types: R - random, H - human, S - sequential.
                Split each player using whitespace or it will be ignored.
                """);
        String player;
        for (int i = 0; i < contenders; i++) {
            player = in.next();
            switch (player) {
                case "R" -> players.add(new RandomPlayer());
                case "H" -> players.add(new HumanPlayer());
                case "S" -> players.add(new SequentialPlayer());
                default -> {
                    out.println("\nInvalid player name\n");
                    i--;
                }
            }
        }
        for (int i = 0; i < contenders; i++) {
            for (int j = 0; j < contenders; j++) {
                if (i == j) {
                    continue;
                }
                int ending = new MultiPlayerGame(
                        List.of(players.get(i), players.get(j)), m, n, k, regret
                ).playNewGame();
                switch (ending) {
                    case 2 -> result[j] += 3;
                    case 1 -> result[i] += 3;
                    case 0 -> {
                        result[i]++;
                        result[j]++;
                    }
                }
            }
        }
    }

    public String giveResults() {
        StringBuilder x = new StringBuilder();
        for (int j = 0; j < contenders; j++) {
            int pos = 0, points = -1;
            for (int i = 0; i < contenders; i++) {
                if (points < result[i]) {
                    pos = i;
                    points = result[i];
                }
            }
            result[pos] = -1;
            x.append(String.format("Player #%d: %d\n", pos + 1, points));
        }
        return x.toString();
    }
}
