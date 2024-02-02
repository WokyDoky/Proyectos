package omok.model;

import javax.swing.*;
import java.awt.*;

class BoardPanel extends JPanel {

    /** Board to be painted. */
    private  Board board;
    protected Dimension dim;

    public BoardPanel(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(182, 152, 114));
        g.fillRect(0,0, dim.width, dim.height);
    }

        // other fields and methods if needed
    }

