package HW4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUIPart extends NoApplet {

    final int sizeOfBoard = 15; //This can be added else where if the option to create different size boards is given.
    boolean isWhiteTurn = false; // Track whose turn it is (white starts)
    Point lastStonePosition;

    public static void main(String[] args) {
        new GUIPart(new String[] {"width=592", "height=592"}).run();
    }
    public GUIPart(String[] params) {
        super(params);
    }
    public void paintComponent(Graphics g){
        drawBoard(g);

    }
    public void drawBoard(Graphics g){
        g.setColor(Color.orange);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
        for (int i = 0; i < 16; i++){
            g.drawLine(i*(getHeight()/16),0,i*(getHeight()/16),getHeight());
        }
        for (int j = 0; j < 16; j++){
            g.drawLine(0,j*(getWidth()/(sizeOfBoard+1)),getWidth(),j*(getWidth()/(sizeOfBoard+1)));
        }
    }
    void whitePiece(Graphics g, int x, int y) {
        g.setColor(new Color(255, 255, 255));
        g.fillOval(x - 16, y - 16, 32, 32);
        g.setColor(new Color(47, 127, 141));
        g.fillOval(x - 8, y - 8, 16, 16);
    }
    void blackPiece(Graphics g, int x, int y){
        g.setColor(new Color(0, 0, 0));
        g.fillOval(x - 16, y - 16, 32, 32);
        g.setColor(new Color(108, 0, 0));
        g.fillOval(x - 8, y - 8, 16, 16);
    }

}
