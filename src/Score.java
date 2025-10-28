public class Score {
    private int moveCount;

    public Score() {
        this.moveCount = 0;
    }

    public void incrementMoves() {
        moveCount++;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void reset() {
        moveCount = 0;
    }

    public boolean isTie() {
        return moveCount >= 9;
    }
}
