package nothingHW4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class boardDraw extends JPanel implements ItemListener {

    //This is technically my main method.
    //The instructions say to name it "main" but I hope this is not an issue.
    final int sizeOfBoard = 15; //Have this in case you want to change the board. I decided to keep it unchangeable.

    JButton start;
    JButton restart;
    HW2.Board board;
    Point lastStonePosition;

    /**
     * Draws the board when it's time to play.
     * @param g Graphics.
     */
    public void drawBoard(Graphics g){
        //g.setColor(Color.orange);
        //Fun Fact: This is UTEP's gray rbg color.
        g.setColor(new Color(177, 179, 179));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
        for (int i = 0; i < 16; i++){
            g.drawLine(i*(getHeight()/16),0,i*(getHeight()/16),getHeight());
        }
        for (int j = 0; j < 16; j++){
            g.drawLine(0,j*(getWidth()/(sizeOfBoard+1)),getWidth(),j*(getWidth()/(sizeOfBoard+1)));
        }
        drawCoords(g);
    }

    /**
     * Helper method of drawBoard to mark the coordinates.
     * @param g Graphics.
     */
    public void drawCoords(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Malgun Gothic", Font.BOLD, 10));

        // Set the spacing between letters
        int letterSpacing = 37;

        // Define the letters to be displayed
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'};

        // Iterate through rows
        for (int col = 0; col < getWidth() / letterSpacing; col++) {
            // Calculate the position for each letter
            int x = col * letterSpacing;
            // Draw the letter at the calculated position
            g.drawString(String.valueOf(letters[col % letters.length]), x+letterSpacing + 2, 10);
        }


        // Iterate through cols
        for (int row = 1; row < getWidth() / letterSpacing; row++) {
            // Calculate the position for each number.
            int x = row * letterSpacing;
            // Draw the number at the calculated position
            g.drawString(String.valueOf(row - 1), 10, x - 2);
        }

    }

    /**
     * We draw the stones.
     * @param g Graphics.
     * @param x where to draw them.
     * @param y where to draw them.
     */
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

    /**
     * draws Graphically who won.
     * @param g Graphics
     * @param line the set of coordinates that won.
     * @param whoWon just to change the color.
     */
    void winingLine(Graphics g, int [][] line, boolean whoWon){
        int circleSize = 36;
        g.setColor(new Color(255, 250, 115, 153));
        if (whoWon)g.setColor(new Color(213, 116, 116, 132));
        for (int i = 0; i < 5; i++){
            g.fillOval(((line[1][i] + 1)*37) - circleSize/2, ((line[0][i] + 1)*37) - circleSize/2, circleSize, circleSize);
        }
    }

    /**
     * Little indicator to show whose turn is it.
     * @param g Graphics
     * @param turn checks whose turn is it.
     */
    void whoIsPlaying(Graphics g, boolean turn){
        int ledSize = 20;
        g.setColor(turn ? (new Color(117, 12, 12, 176) ): (new Color(47, 127, 141, 173)));
        g.fillOval(getWidth() - ledSize - 6, getHeight() - ledSize - 6, ledSize, ledSize);
    }

    /**
     * What happes after we click the screen. We choose where to set the stone.
     * @param x gets the mouse's position.
     * @param y gets the mouse's position.
     * @return closes "correct" position for stone.
     */
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

    /**
     *
     * @param label name of button
     * @param enabled if clickable.
     * @return JButton
     */
    private JButton createButton(String label, boolean enabled) {
        JButton button = new JButton(label);
        button.setFocusable(false);
        button.setEnabled(enabled);
        return button;
    }
    //Next 3 methods are handled in other class.
    public void start(){
        //Override
    }
    public void restart(){
        //Override
    }
    public void cheats(){
        //Override.
    }

    /**
     * Creates the game Panel.
     * @return Jpanel with start button.
     */
    private JPanel createUI() {

        start = createButton("Start Game", false);
        start.addActionListener(e -> {
            start();
        });
        JPanel control = new JPanel();
        control.setLayout(new FlowLayout());
        control.add(start);

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        root.add(control, BorderLayout.NORTH);
        root.add(this, BorderLayout.CENTER);
        return root;
    }

    /**
     * This is how the menuBar is created.
     * @return JMenubar that holds the menu.
     */
    public JMenuBar createMenu(){
        //When creating the toolbar, they can have icons,
        //but I don't know how to upload it to blackboard
        //without losing the reference.

        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Menu");
        gameMenu.setMnemonic(KeyEvent.VK_G); // Alt+G will be the mnemonic

        JMenuItem restartMenuItem = new JMenuItem("Restart");
        restartMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK)); //Accelerator
        restartMenuItem.addActionListener(e -> restart());

        JMenuItem home = new JMenuItem("Go Home");
        home.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.ALT_DOWN_MASK));
        home.addActionListener(e -> start());

        JMenuItem cheat = new JMenuItem("Cheats");
        cheat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
        cheat.addActionListener(e -> cheats());


        gameMenu.add(restartMenuItem);
        gameMenu.add(home);
        gameMenu.add(cheat);
        menuBar.add(gameMenu);

        return menuBar;
    }

    /**
     *
     * @param e the event to be processed
     */
    @Override
    public void itemStateChanged(ItemEvent e) {

    }
    public String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }
    public void run(){
        MyBoardGame gamePanel = new MyBoardGame();
        JFrame frame = new JFrame("My Board Game");
        frame.setJMenuBar(gamePanel.createMenu());
        frame.setResizable(false);
        frame.add(gamePanel);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
