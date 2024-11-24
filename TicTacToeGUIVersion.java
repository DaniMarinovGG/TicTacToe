package paket4e;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUIVersion {
    private int rows, cols, winLength, currentPlayer;
    private String[] playerSymbols;
    private JButton[][] boardButtons;
    private JFrame frame;
    private boolean gameOver = false;

    public TicTacToeGUIVersion(int rows, int cols, int winLength, int playerCount, String[] symbols) {
        this.rows = rows;
        this.cols = cols;
        this.winLength = winLength;
        this.playerSymbols = symbols;
        this.currentPlayer = 0;
        initializeBoard();
    }

    private void initializeBoard() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(rows, cols));
        boardButtons = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardButtons[i][j] = new JButton(" ");
                boardButtons[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                boardButtons[i][j].setFocusPainted(false);
                boardButtons[i][j].addActionListener(new ButtonClickListener(i, j));
                boardPanel.add(boardButtons[i][j]);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    private void makeMove(int x, int y) {
        if (boardButtons[x][y].getText().equals(" ") && !gameOver) {
            boardButtons[x][y].setText(playerSymbols[currentPlayer]);
            if (checkWin(x, y)) {
                gameOver = true;
                JOptionPane.showMessageDialog(frame, "Player " + (currentPlayer + 1) + " (" + playerSymbols[currentPlayer] + ") wins!");
                resetGame();
            } else if (checkDraw()) {
                gameOver = true;
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer + 1) % playerSymbols.length;
            }
        }
    }

    private boolean checkWin(int x, int y) {
        String symbol = playerSymbols[currentPlayer];
        return checkDirection(x, y, 1, 0, symbol) || checkDirection(x, y, 0, 1, symbol) || checkDirection(x, y, 1, 1, symbol) || checkDirection(x, y, 1, -1, symbol);
    }

    private boolean checkDirection(int x, int y, int dx, int dy, String symbol) {
        int count = 1;
        count += countMatches(x, y, dx, dy, symbol);
        count += countMatches(x, y, -dx, -dy, symbol);
        return count >= winLength;
    }

    private int countMatches(int x, int y, int dx, int dy, String symbol) {
        int count = 0;
        int i = x + dx, j = y + dy;
        while (i >= 0 && i < rows && j >= 0 && j < cols && boardButtons[i][j].getText().equals(symbol)) {
            count++;
            i += dx;
            j += dy;
        }
        return count;
    }

    private boolean checkDraw() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (boardButtons[i][j].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        int option = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    boardButtons[i][j].setText(" ");
                }
            }
            gameOver = false;
            currentPlayer = 0;
        } else {
            System.exit(0);
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int x, y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            makeMove(x, y);
        }
    }

    public static void main(String[] args) {
        int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows: "));
        int cols = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns: "));
        int winLength = Integer.parseInt(JOptionPane.showInputDialog("Enter the win length: "));
        int playerCount = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of players: "));

        String[] symbols = new String[playerCount];
        for (int i = 0; i < playerCount; i++) {
            symbols[i] = JOptionPane.showInputDialog("Enter symbol for player " + (i + 1) + ": ");
        }

        new TicTacToeGUIVersion(rows, cols, winLength, playerCount, symbols);
    }
}
