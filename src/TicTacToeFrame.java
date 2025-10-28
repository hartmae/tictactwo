import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private static final int ROW = 3;
    private static final int COL = 3;
    private Board board;
    private TicTacToeButton[][] buttons = new TicTacToeButton[ROW][COL];

    private Player playerX;
    private Player playerO;
    private Player currentPlayer;
    private Score score;
    private Result result;

    private JPanel boardPanel;
    private JPanel controlPanel;
    private JButton quitButton;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        board = new Board();
        playerX = new Player("X");
        playerO = new Player("O");
        currentPlayer = playerX;
        score = new Score();
        result = new Result();

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
        board.clear();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        currentPlayer = playerX;
        score.reset();
        result.reset();
    }


    private boolean isValidMove(int row, int col) {
        return board.isValidMove(row, col);
    }

    private boolean isWin(String player) {
        return board.isWin(player);
    }

    private boolean isTie() {
        return score.isTie();
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

            board.setMove(row, col, currentPlayer.getSymbol());
            button.setText(currentPlayer.getSymbol());
            score.incrementMoves();

            if (score.getMoveCount() >= 5) {
                if (isWin(currentPlayer.getSymbol())) {
                    disableAllButtons();
                    result.setWin(currentPlayer);
                    promptPlayAgain(result.getMessage());
                    return;
                }
            }

            if (isTie()) {
                disableAllButtons();
                result.setTie();
                promptPlayAgain(result.getMessage());
                return;
            }

            currentPlayer = currentPlayer.getSymbol().equals("X") ? playerO : playerX;
        }
    }
}
