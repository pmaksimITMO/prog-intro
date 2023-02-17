package MNKgame;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    private final PrintStream out;

    public HumanPlayer(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public HumanPlayer() {
        this(new Scanner(System.in), new PrintStream(System.out));
    }

    @Override
    public Move move(Position position) {
        out.println("Current position: ");
        out.println(position);
        Move move;
        Cell cell = position.getTurn();
        while (true) {
            int row = -1, col = -1;
            out.println("Enter row and column");
            if (in.hasNextInt()) {
                row = in.nextInt() - 1;
            } else {
                in.next();
            }
            if (in.hasNextInt()) {
                col = in.nextInt() - 1;
            } else  {
                in.next();
            }
            if (row == -1 || col == -1) {
                out.println(
                        """
                        Invalid input. You have chosen not empty cell or forbidden cell
                        Please, be careful and try again
                        """);
            } else {
                move = new Move(row, col, cell);
                break;
            }
        }
        return move;
    }
}
