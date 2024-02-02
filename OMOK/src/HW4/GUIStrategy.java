package HW4;

import HW2.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GUIStrategy extends GUIPart{
    private final int constant = 592/16;
    public GUIStrategy(String[] params) {
        super(params);
        HW2.Board board = new Board(15);
        board.populateBoard();
    }

    @Override
    public void paintComponent(Graphics g){
        drawBoard(g);
        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //mc stands for Mouse Clicked
                int mcX = (int) findClosestIntersection(e.getX(), e.getY()).getY();
                int mcY = (int) findClosestIntersection(e.getX(), e.getY()).getX();
                // Check if the user clicked on the intersection.
                if (e.getX() > 0 && e.getX() < 592 && e.getY() > 0 && e.getY() < 592) {
                    // Draw a "stone" at the intersection.
                    if (isWhiteTurn) {
                        whitePiece(getGraphics(), constant*mcX, constant * mcY);
                    } else {
                        blackPiece(getGraphics(), constant*mcX, constant * mcY);
                    }

                    // Change the turn.
                    isWhiteTurn = !isWhiteTurn;

                    // Set the lastStonePosition.
                    lastStonePosition = new Point(0, 0);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) { }
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) { }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) { }
        };
        addMouseListener(mouseListener);
    }
    public Point findClosestIntersection(int x, int y) {
        // Calculate the distance from the user's click to each intersection.
        ArrayList<Double> distances = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int intersectionX = i * 37;
                int intersectionY = j * 37;
                double distance = Math.sqrt((x - intersectionX) * (x - intersectionX) + (y - intersectionY) * (y - intersectionY));
                distances.add(distance);
            }
        }

        // Find the intersection with the smallest distance.
        int minIndex = 0;
        double minDistance = distances.get(0);
        for (int i = 1; i < distances.size(); i++) {
            if (distances.get(i) < minDistance) {
                minIndex = i;
                minDistance = distances.get(i);
            }
        }

        // Return the coordinates of the nearest intersection.
        return new Point(minIndex % 16, minIndex / 16);
    }
    public static void main(String[] args) {
        new GUIStrategy(new String[] {"width=592", "height=592"}).run();
    }
}



// After this there is ideas I had
// Ask TA's



//This is the idea I had for the button, but instead of only one, I creat a list
//with a button for every intersection.


//JButton placeStoneButton = new JButton("Place Stone");
//var panel = new JPanel();
//panel.add(placeStoneButton);
//button.addMouseListener(new java.awt.event.MouseAdapter() {
//public void mouseEntered(java.awt.event.MouseEvent evt) {
//button.setBackground(Color.GREEN);
//}

//public void mouseExited(java.awt.event.MouseEvent evt) {
//button.setBackground(UIManager.getColor("key"));
//}
//});

