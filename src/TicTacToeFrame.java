import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private static final int ROW = 3;
    private static final int COL = 3;
    private String[][] board = new String[ROW][COL];
    private TicTacToeButton[][] buttons = new TicTacToeButton[ROW][COL];

    private String currentPlayer = "X";
    private int moveCount = 0;

    private JPanel boardPanel;
    private JPanel controlPanel;
    private JButton quitButton;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createBoardPanel();
        createControlPanel();

        add(boardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        clearBoard();
        setVisible(true);
    }

    private void createBoardPanel() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(Color.BLACK);
        ActionListener buttonListener = new TicTacToeButtonListener();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col] = new TicTacToeButton(row, col);
                buttons[row][col].addActionListener(buttonListener);
                buttons[row][col].setBackground(Color.WHITE);
                boardPanel.add(buttons[row][col]);
            }
        }
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 20));
        quitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        controlPanel.add(quitButton);
    }

    private void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = "";
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = "X";
        moveCount = 0;
    }


    private boolean isValidMove(int row, int col) {
        return board[row][col].equals("");
    }

    private boolean rowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (board[row][0].equals(player) &&
                board[row][1].equals(player) &&
                board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean colWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (board[0][col].equals(player) &&
                board[1][col].equals(player) &&
                board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean diagonalWin(String player) {
        if ((board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
            (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player))) {
            return true;
        }
        return false;
    }

    private boolean isWin(String player) {
        return colWin(player) || rowWin(player) || diagonalWin(player);
    }

    private boolean isTie() {
        return moveCount >= 9;
    }

    private void disableAllButtons() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void promptPlayAgain(String message) {
        int choice = JOptionPane.showConfirmDialog(
            this,
            message + "\nWould you like to play again?",
            "Game Over",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            clearBoard();
        } else {
            System.exit(0);
        }
    }

    private class TicTacToeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton button = (TicTacToeButton) e.getSource();
            int row = button.getRow();
            int col = button.getCol();

            if (!isValidMove(row, col)) {
                JOptionPane.showMessageDialog(
                    TicTacToeFrame.this,
                    "That square is already taken! Please choose another.",
                    "Invalid Move",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            board[row][col] = currentPlayer;
            button.setText(currentPlayer);
            moveCount++;

            if (moveCount >= 5) {
                if (isWin(currentPlayer)) {
                    disableAllButtons();
                    String playerName = currentPlayer.equals("X") ? "Player X" : "Player O";
                    promptPlayAgain(playerName + " wins!");
                    return;
                }
            }

            if (isTie()) {
                disableAllButtons();
                promptPlayAgain("It's a tie!");
                return;
            }

            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }
}
