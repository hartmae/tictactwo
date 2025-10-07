import javax.swing.*;
import java.awt.*;
public class TicTacToeButton extends JButton {
    private int row;
    private int col;

    public TicTacToeButton(int row, int col) {
        super("");
        this.row = row;
        this.col = col;
        setFont(new Font("Arial", Font.BOLD, 60));
        setFocusPainted(false);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
