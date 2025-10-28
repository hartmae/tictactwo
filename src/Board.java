public class Board {
    private static final int ROW = 3;
    private static final int COL = 3;
    private String[][] board = new String[ROW][COL];

    public Board() {
        clear();
    }

    public void clear() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = "";
            }
        }
    }

    public boolean isValidMove(int row, int col) {
        return board[row][col].equals("");
    }

    public void setMove(int row, int col, String player) {
        board[row][col] = player;
    }

    public String getMove(int row, int col) {
        return board[row][col];
    }

    public boolean rowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (board[row][0].equals(player) &&
                board[row][1].equals(player) &&
                board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean colWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (board[0][col].equals(player) &&
                board[1][col].equals(player) &&
                board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean diagonalWin(String player) {
        if ((board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
            (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player))) {
            return true;
        }
        return false;
    }

    public boolean isWin(String player) {
        return colWin(player) || rowWin(player) || diagonalWin(player);
    }
}
