package MNKgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s;
        System.out.println(
                """
                Hello, dear mnk enjoyer.
                If you want to start new game enter "yes" and integers m, n, k.
                If you want to stop, enter "no"
                """
        );
        while (true) {
            System.out.println("Would you like to play new game?\n");
            s = in.next();
            if (s.equals("no")) {
                break;
            }
            final int m = in.nextInt();
            final int n = in.nextInt();
            final int k = in.nextInt();
            System.out.println(
                    """
                    Would you like to forbid some cells?
                    If yes than enter "yes", numbers of forbidden cells and then enter pairs of integers(each on new line): row, column
                    If no than enter "no"
                    """
            );
            s = in.next();
            List<PairInt> regret = new ArrayList<>();
            if (s.equals("yes")) {
                int all = in.nextInt();
                for (int i = 0; i < all; i++) {
                    regret.add(new PairInt(in.nextInt(), in.nextInt()));
                }
            }
            System.out.println(
                    """
                    If you want to start tournament enter T.
                    If you want to start ordinary game enter G.
                    """
            );
            s = in.next();
            if (s.equals("T"))  {
                Tournament play = new Tournament(m, n, k, regret);
                play.start();
                System.out.println(play.giveResults());
            } else {
                MultiPlayerGame game = new MultiPlayerGame(new ArrayList<>(), m, n, k, regret);
                addPlayers(game, in);
                game.playNewGame();
            }
        }
    }

    private static void addPlayers(MultiPlayerGame game, Scanner in) {
        System.out.println(
                """
                Enter players. R - random, H - human, S - sequential.
                To finish adding enter start.
                """
        );
        String s;
        int count = 0;
        while (true) {
            s = in.next();
            count++;
            switch (s) {
                case "R" -> game.addPlayer(new RandomPlayer());
                case "H" -> game.addPlayer(new HumanPlayer());
                case "S" -> game.addPlayer(new SequentialPlayer());
                case "start" -> {
                    return;
                }
                default -> count--;
            }
            if (count > 4) {
                throw new UnsupportedOperationException("Too many players");
            }
        }
    }
}
