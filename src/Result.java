public class Result {
    public enum GameState {
        WIN,
        TIE,
        ONGOING
    }

    private GameState state;
    private Player winner;

    public Result() {
        this.state = GameState.ONGOING;
        this.winner = null;
    }

    public void setWin(Player winner) {
        this.state = GameState.WIN;
        this.winner = winner;
    }

    public void setTie() {
        this.state = GameState.TIE;
        this.winner = null;
    }

    public void reset() {
        this.state = GameState.ONGOING;
        this.winner = null;
    }

    public boolean isWin() {
        return state == GameState.WIN;
    }

    public boolean isTie() {
        return state == GameState.TIE;
    }

    public boolean isGameOver() {
        return state != GameState.ONGOING;
    }

    public Player getWinner() {
        return winner;
    }

    public String getMessage() {
        if (state == GameState.WIN && winner != null) {
            return "Player " + winner.getSymbol() + " wins!";
        } else if (state == GameState.TIE) {
            return "It's a tie!";
        }
        return "";
    }
}
