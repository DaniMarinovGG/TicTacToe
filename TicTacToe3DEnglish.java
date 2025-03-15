package paket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TicTacToe3DEnglish {
    private int rows, cols, layers;
    private char[][][] board;
    private String[] players;
    private HashMap<Character, Color> playerColors;
    private int currentPlayerIndex;
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JLabel statusLabel;

    public TicTacToe3DEnglish(int rows, int cols, int layers, String[] players, HashMap<Character, Color> playerColors) {
        this.rows = rows;
        this.cols = cols;
        this.layers = layers;
        this.board = new char[layers][rows][cols];
        this.players = players;
        this.playerColors = playerColors;
        this.currentPlayerIndex = 0;
        initializeBoard();
        createAndShowGUI();
    }

    private void initializeBoard() {
        for (int l = 0; l < layers; l++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    board[l][r][c] = '-';
                }
            }
        }
    }

    private void createAndShowGUI() {
        frame = new JFrame("3D Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        tabbedPane = new JTabbedPane();
        for (int l = 0; l < layers; l++) {
            JPanel layerPanel = createLayerPanel(l);
            tabbedPane.addTab("Layer " + (l + 1), layerPanel);
        }

        statusLabel = new JLabel("Player " + players[currentPlayerIndex] + "'s turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createLayerPanel(int layer) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, cols));

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JButton button = new JButton(" ");
                button.setFont(new Font("Arial", Font.BOLD, 24));
                button.addActionListener(new ButtonClickListener(layer, r, c, button));
                panel.add(button);
            }
        }

        return panel;
    }

    private class ButtonClickListener implements ActionListener {
        private int layer, row, col;
        private JButton button;

        public ButtonClickListener(int layer, int row, int col, JButton button) {
            this.layer = layer;
            this.row = row;
            this.col = col;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[layer][row][col] != '-') {
                JOptionPane.showMessageDialog(frame, "Cell already occupied!", "Invalid Move", JOptionPane.WARNING_MESSAGE);
                return;
            }

            char currentSymbol = players[currentPlayerIndex].charAt(0);
            board[layer][row][col] = currentSymbol;
            button.setText(String.valueOf(currentSymbol));
            button.setForeground(playerColors.get(currentSymbol));

            if (checkWin()) {
                statusLabel.setText("Player " + players[currentPlayerIndex] + " wins!");
                disableAllButtons();
            } else if (isBoardFull()) {
                statusLabel.setText("It's a draw!");
            } else {
                switchPlayer();
                statusLabel.setText("Player " + players[currentPlayerIndex] + "'s turn");
            }
        }
    }

    private boolean checkWin() {
        return checkRows() || checkColumns() || checkLayers() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int l = 0; l < layers; l++) {
            for (int r = 0; r < rows; r++) {
                if (isWinningLine(l, r, 0, 0, 0, 1)) return true; // Обхождане на редовете
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int l = 0; l < layers; l++) {
            for (int c = 0; c < cols; c++) {
                if (isWinningLine(l, 0, c, 0, 1, 0)) return true; // Обхождане на колоните
            }
        }
        return false;
    }

    private boolean checkLayers() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isWinningLine(0, r, c, 1, 0, 0)) return true; // Обхождане на слоевете
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        // диагоналите на слоевете
        for (int l = 0; l < layers; l++) {
            if (isWinningLine(l, 0, 0, 0, 1, 1)) return true;
            if (isWinningLine(l, 0, cols - 1, 0, 1, -1)) return true;
        }

        // диагоналите, които минават през различни слоеве
        if (isWinningLine(0, 0, 0, 1, 1, 1)) return true;
        if (isWinningLine(0, 0, cols - 1, 1, 1, -1)) return true;
        if (isWinningLine(0, rows - 1, 0, 1, -1, 1)) return true;
        if (isWinningLine(0, rows - 1, cols - 1, 1, -1, -1)) return true;

        return false;
    }

    private boolean isWinningLine(int startLayer, int startRow, int startCol, int dLayer, int dRow, int dCol) {
        char first = board[startLayer][startRow][startCol];
        if (first == '-') return false;

        for (int i = 1; i < layers; i++) {
            int l = startLayer + i * dLayer;
            int r = startRow + i * dRow;
            int c = startCol + i * dCol;
            if (l >= layers || r >= rows || c >= cols || board[l][r][c] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoardFull() {
        for (int l = 0; l < layers; l++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (board[l][r][c] == '-') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    private void disableAllButtons() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            JPanel panel = (JPanel) tabbedPane.getComponentAt(i);
            for (Component component : panel.getComponents()) {
                if (component instanceof JButton) {
                    component.setEnabled(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows (X):"));
                int cols = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns (Y):"));
                int layers = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of layers (Z):"));

                int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of players:"));
                String[] players = new String[numPlayers];
                HashMap<Character, Color> playerColors = new HashMap<>();

                for (int i = 0; i < numPlayers; i++) {
                    players[i] = JOptionPane.showInputDialog("Enter symbol for Player " + (i + 1) + ":");
                    Color color = JColorChooser.showDialog(null, "Choose color for Player " + (i + 1), Color.BLACK);
                    playerColors.put(players[i].charAt(0), color);
                }

                new TicTacToe3D(rows, cols, layers, players, playerColors);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please restart the game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
