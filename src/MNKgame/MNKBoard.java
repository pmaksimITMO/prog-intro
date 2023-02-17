package MNKgame;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> CELL_TO_STRING = Map.of(
            Cell.X, 'X',
            Cell.O, '0',
            Cell.Y, '-',
            Cell.Z, '|',
            Cell.E, '.',
            Cell.FORBIDDEN, 'I'
    );

    private final Cell[] turns;
    private final int n;
    private final int m;
    private final int k;
    private int empties;
    private final Cell[][] field;
    private Cell turn;
    private final int[] splitLine;

    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.empties = m * n;
        field = new Cell[m][n];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        this.turns = new Cell[]{Cell.X, Cell.O, Cell.Y, Cell.Z};
        splitLine = new int[Math.max(m, n)];
        for (int i = 1; i <= Math.max(m, n); i++) {
            int cnt = 0, j = i;
            while (j != 0) {
                cnt++;
                j /= 10;
            }
            splitLine[i - 1] = cnt;
        }
    }

    public void regretCells(List<PairInt> regret) {
        for (PairInt cell : regret) {
            field[cell.getFirst() - 1][cell.getSecond() - 1] = Cell.FORBIDDEN;
        }
    }

    private boolean inField(int row, int col) {
        return
                0 <= row && row < m &&
                        0 <= col && col < n;
    }

    private boolean isWin(int positionInRow, int positionInCol, int shiftRow, int shiftCol) {
        return (calculate(positionInRow, positionInCol, shiftRow, shiftCol) +
                calculate(positionInRow, positionInCol, -shiftRow, -shiftCol) - 1) >= k;
    }

    private int calculate(int positionInRow, int positionInCol, int shiftRow, int shiftCol) {
        int count = 1;
        final Cell cell = field[positionInRow][positionInCol];
        for (int i = 1; i < k; i++) {
            int row = positionInRow + i * shiftRow, col = positionInCol + i * shiftCol;
            if (inField(row, col) && field[row][col] == cell) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    private Result checkResult(final int row, final int col) {
        boolean res = false;
        int[] shiftRow = new int[]{1, 1, -1, 0};
        int[] shiftCol = new int[]{1, 0, 1, 1};
        for (int i = 0; i < 4; i++) {
            res |= isWin(row, col, shiftRow[i], shiftCol[i]);
        }
        if (res) {
            return Result.WIN;
        }
        if (empties == 0) {
            return Result.DRAW;
        }
        return Result.UNKNOWN;
    }

    public boolean isValid(final Move move) {
        return
                inField(move.getRow(), move.getCol()) &&
                        field[move.getRow()][move.getCol()] == Cell.E &&
                        turn == move.getCell();
    }

    private Cell makeTurn(boolean[] inGame) {
        int id = 0;
        for (int i = 0; i < turns.length; i++) {
            if (turns[i] == turn) {
                id = i;
                break;
            }
        }
        for (int i = 1; i < inGame.length; i++) {
            if (inGame[(id + i) % inGame.length]) {
                return turns[(id + i) % inGame.length];
            }
        }
        throw new RuntimeException();
    }

    @Override
    public int getM() {
        return this.m;
    }

    @Override
    public int getN() {
        return this.n;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return new BoardPosition(this);
    }

    @Override
    public Result makeMove(Move move, boolean[] inGame) {
        final int row = move.getRow();
        final int col = move.getCol();
        final Cell newTurn = makeTurn(inGame);
        if (!isValid(move)) {
            turn = newTurn;
            System.out.print("Wrong move, this player has lost.\n\\\n \\->");
            return Result.LOSE;
        }
        turn = newTurn;
        field[row][col] = move.getCell();
        empties--;
        return checkResult(row, col);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(splitLine[n - 1] + 1));
        for (int i = 1; i <= n; i++) {
            sb.append(i).append(' ');
        }
        sb.append('\n');
        for (int r = 0; r < m; r++) {
            sb.append(" ".repeat(splitLine[n - 1] - splitLine[r])).append(r + 1).append(' ');
            for (int c = 0; c < n; c++) {
                sb.append(
                        " ".repeat((splitLine[c] - 1) / 2)
                ).append(
                        CELL_TO_STRING.get(field[r][c])
                ).append(
                        " ".repeat(splitLine[c] / 2 + 1)
                );
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
