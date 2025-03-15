package paket;

import java.util.*;

public class TicTacToe4Dabove {
    private int dimensions;
    private int[] lengths;
    private int players;
    private char[] symbols;
    private int winningLength;
    private char[] board;
    private int totalCells;

    public TicTacToe4Dabove(int dimensions, int[] lengths, int players, char[] symbols, int winningLength) {
        this.dimensions = dimensions;
        this.lengths = lengths;
        this.players = players;
        this.symbols = symbols;
        this.winningLength = winningLength;
        this.totalCells = 1;
        for (int len : lengths) {
            this.totalCells *= len;
        }
        this.board = new char[this.totalCells];
        Arrays.fill(this.board, '\0');
    }

    private int toIndex(int[] coords) {
        int index = 0;
        int multiplier = 1;
        for (int i = dimensions - 1; i >= 0; i--) {
            index += coords[i] * multiplier;
            multiplier *= lengths[i];
        }
        return index;
    }

    private int[] fromIndex(int index) {
        int[] coords = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            coords[i] = index % lengths[i];
            index /= lengths[i];
        }
        return coords;
    }

    private boolean isWinningLine(int[] start, int[] direction, char symbol) {
        int[] pos = Arrays.copyOf(start, dimensions);
        for (int i = 0; i < winningLength; i++) {
            int index = toIndex(pos);
            if (index < 0 || index >= totalCells || board[index] != symbol) {
                return false;
            }
            for (int d = 0; d < dimensions; d++) {
                pos[d] += direction[d];
                if (pos[d] < 0 || pos[d] >= lengths[d]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin(int lastMoveIndex) {
        int[] start = fromIndex(lastMoveIndex);
        char symbol = board[lastMoveIndex];

        for (int i = 0; i < (1 << dimensions); i++) {
            int[] direction = new int[dimensions];
            for (int d = 0; d < dimensions; d++) {
                direction[d] = (i & (1 << d)) != 0 ? 1 : 0;
            }
            if (Arrays.stream(direction).sum() == 0) continue;
            if (isWinningLine(start, direction, symbol)) {
                return true;
            }
        }
        return false;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameWon = false;
        int moves = 0;
        while (!gameWon && moves < totalCells) {
            for (int p = 0; p < players; p++) {
                System.out.println("Player " + (p + 1) + " (" + symbols[p] + "), it's your turn.");
                int[] coords = new int[dimensions];
                for (int d = 0; d < dimensions; d++) {
                    System.out.print("Enter coordinate for dimension " + (d + 1) + ": ");
                    coords[d] = scanner.nextInt();
                    if (coords[d] < 0 || coords[d] >= lengths[d]) {
                        System.out.println("Invalid coordinate. Try again.");
                        d--;
                    }
                }

                int index = toIndex(coords);
                if (board[index] != '\0') {
                    System.out.println("Cell is already occupied. Try again.");
                    p--;
                    continue;
                }

                board[index] = symbols[p];
                moves++;
                if (checkWin(index)) {
                    System.out.println("Player " + (p + 1) + " (" + symbols[p] + ") wins!");
                    gameWon = true;
                    break;
                }
            }
        }

        if (!gameWon) {
            System.out.println("It's a draw!");
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of dimensions: ");
        int n = scanner.nextInt();

        int[] lengths = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the length for dimension " + (i + 1) + ": ");
            lengths[i] = scanner.nextInt();
        }

        System.out.print("Enter the number of players: ");
        int players = scanner.nextInt();

        char[] symbols = new char[players];
        for (int i = 0; i < players; i++) {
            System.out.print("Enter the symbol for player " + (i + 1) + ": ");
            symbols[i] = scanner.next().charAt(0);
        }

        System.out.print("Enter the winning length: ");
        int k = scanner.nextInt();

        TicTacToe4Dabove game = new TicTacToe4Dabove(n, lengths, players, symbols, k);
        game.play();

        scanner.close();
    }
}
