package omok.model;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class controllerTest extends JPanel {
    Board board;
    BoardPanel panel;

    public controllerTest(){
        var frame = new JFrame("Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setBackground(new Color(183, 174, 124));
    }


    @Override
    protected void paintComponent(Graphics g){
        panel.paintComponent(g);
    }
    public static void main(String [] args){
        SwingUtilities.invokeLater(controllerTest::new);
    }
}
