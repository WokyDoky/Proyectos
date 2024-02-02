package nothingHW4;
import HW2.Board;
import HW2.cpuP;
import HW2.humanP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBoardGame extends boardDraw {

    public boolean isWhiteTurn = false;
    public int gameMode = 0;
    private boolean inGame = false;
    private boolean restartConfirmation = false;
    Board errorNotSettingPiecesRight;

    String [][] tablero2CheckError;

    String pid;


    /**
     * Constructor. It sets the size of the board.
     * It creates a new "board" which is what handles the logic behind the scenes.
     * Starts the MouseListener. It is here after IA recommendation. Thank you.
     */
    public MyBoardGame() {
        setPreferredSize(new Dimension(592, 592));
        board = new Board();
        board.populateBoard();
        addMouseListener(new MyMouseListener(this, board)); // Add the MouseListener
        start = new JButton();
        showStartScreen();

    }

    /**
     * This is what happens when you click the "go home" button.
     */
    @Override
    public void start(){
        Object[] options = {"Yes, let me go", "Keep playing"};
        int choice = JOptionPane.showOptionDialog(MyBoardGame.this, "You are about to leave the game.", "Leaving?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == JOptionPane.YES_OPTION){
            inGame = false;
            gameMode = 0;
            showStartScreen();
        }
    }


    /**
     * First things the player sees.
     * You can also get here from the menu bar.
     */
    private void showStartScreen() {
        //Resets everything .
        removeAll();
        resetGame();
        inGame = false;
        //Sets the button to click.
        JButton startButton = new JButton("Start Game");
        //Shows options if clicked.
        startButton.addActionListener(e -> showGameModeOptions());
        add(startButton);
        start.setEnabled(inGame);
        revalidate();
    }
    /**
    Code gets here if the button to restart is clicked and confirmed.
     **/
    private void resetGame() {
        isWhiteTurn = false;
        board.populateBoard();
        removeAll();
        repaint();

        if (gameMode == 3) {
            newBoardHandleEdgeCase();
        }
    }
    /**
    Method that shows game mode options and the initial "play" button.
    It holds 3 Radio buttons that are shown after press play.
    Once you choose something it sets the field gameMode.
    That's how we know what are we playing.
     **/
    private void showGameModeOptions() {
        removeAll();
        start.setEnabled(false);

        //Initializes options.

        if (!inGame) {
            JRadioButton mode1Button = new JRadioButton("Player vs Opponent");
            JRadioButton mode2Button = new JRadioButton("Player vs CPU");
            JRadioButton mode3Button = new JRadioButton("Online");

            ButtonGroup group = new ButtonGroup();
            group.add(mode1Button);
            group.add(mode2Button);
            group.add(mode3Button);

            JButton playButton = new JButton("Play");

            //We say what happens after we choose.
            playButton.addActionListener(e -> {
                if (mode1Button.isSelected()) {
                    gameMode = 1;
                } else if (mode2Button.isSelected()) {
                    gameMode = 2;
                } else if (mode3Button.isSelected()) {
                    gameMode = 3;

                    // Here we can prompt the user for a URL
                    //But it gets tedious to for testing.
                    //The commented method is the full implementation of this.
                    //promptsForString();
                    newBoardHandleEdgeCase();

                }
                //You have to choose something.
                else{
                    showGameModeOptions();
                }

                inGame = true;
                removeAll();
                repaint();
            });

            add(mode1Button);
            add(mode2Button);
            add(mode3Button);
            add(playButton);
        }
        revalidate();
    }

    /**
     * This is method is for completion purposes.
     * Just to show that we can get the link from the user.
     * I hard coded the URL for user-friendliness.
     *
     * @return URL if connection established.
     */
    public String promptsForString (){

        //Prompt user for string...
        String FORMAT = JOptionPane.showInputDialog(this, "Enter the URL for the game:");

        String strategy = chooseLevel();

        try{
            String url = String.format(FORMAT, strategy);
            String response = new MyBoardGame().sendGet(url);
            System.out.println(response);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Invalid URL", "No URL", JOptionPane.INFORMATION_MESSAGE);
            gameMode = 0;
            showStartScreen();
            return null;
        }
        finally {
            System.out.println("Success");
        }


        return FORMAT;
    }

    /**
     * Connects to the server.
     * Checks if it's possible, if not, user is notified.
     */

    public void newBoardHandleEdgeCase (){
        errorNotSettingPiecesRight = new Board();
        tablero2CheckError = errorNotSettingPiecesRight.populateBoard();

        //Prompt user for string...
        //Call promptsForString();
        String FORMAT = "http://omok.atwebpages.com/new/?strategy=%s";

        String strategy = chooseLevel();
        String url = String.format(FORMAT, strategy);
        String response = new MyBoardGame().sendGet(url);


        if (response.contains("\"response\": false") || response.contains("Online")){
            JOptionPane.showMessageDialog(this, "Online mode not available :(", "Offline :(", JOptionPane.INFORMATION_MESSAGE);
            inGame = false;
            gameMode = 0;
            showStartScreen();
        }

        pid = getPID(response);


    }

    /**
     * gets PID number from server.
     * @param serverAnswer answer response from connecting.
     * @return pid number if possible.
     */
    public String getPID(String serverAnswer){
        Pattern pattern = Pattern.compile("\\d(.+)");
        Matcher matcher = pattern.matcher(serverAnswer);

        // Check if the pattern is found
        if (matcher.find()) {
            // Extract the matched substring and exclude the last 2 characters
            String result = matcher.group();
            return result.substring(0, result.length() - 2);
        } else {
            return null; // Return null if no match is found
        }
    }

    /**
     * User is prompted to choose a difficulty.
     * @return String with chosen option.
     */
    public String chooseLevel(){
        Object[] options = {"Smart", "Random"};
        int choice = JOptionPane.showOptionDialog(MyBoardGame.this, "Choose a difficulty.", "Difficulty?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        return choice == JOptionPane.YES_NO_OPTION? "Smart": "Random";
    }

    /**
     * This is the first handshake between server and this code.
     * If possible, we get answer with PID, else we let know the player it wasn't possible.
     * @param urlString url for the server.
     * @return server response.
     */
    public String sendGet(String urlString) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return "Online mode not available";
    }

    /**
    Confirmation method that is called after the restart button is pressed or actuated with accelerator.
     **/
    public void restart(){
        int choice = JOptionPane.showConfirmDialog(MyBoardGame.this, "Are you sure you want to restart the game?", "Confirm Restart", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();  // Reset the game state
            restartConfirmation = false;
        }
    }

    /**
     * Gets the line that won the game and displays it later.
     * @return Beginning and ending point of winning line.
     */
    public Point [] showWinning (boolean whoWin){
        int [][] highlightArray = board.modifyWinCheck();
        winingLine(getGraphics(), highlightArray, whoWin);
        Point start = new Point(highlightArray[0][0], highlightArray[1][0]);
        Point end = new Point(highlightArray[0][highlightArray[0].length-1], highlightArray[1][highlightArray[0].length-1]);
        System.out.println("("+start.x+"," +start.y+")" + " , " +"("+end.x+"," +end.y+")");

        return new Point[]{start, end};
    }


    /**
     * Graphics.
     * @param g the <code>Graphics</code> object to protect
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            if (gameMode == 1 || gameMode == 2) {
                //If here, we know we are playing.
                //What happens after we start playing is not handle here as it may cause bugs.
                if (!restartConfirmation) {
                    restartConfirmation = true;
                    board.populateBoard();

                }
                drawBoard(g);
            } else if (gameMode == 3) {
                //Online mode is now available :)
                if (!restartConfirmation) {
                    restartConfirmation = true;
                }
                drawBoard(g);
            }
        }
        else{
            //We draw the beginning screen.
            g.setColor(Color.BLACK);
            g.setFont(new Font("Malgun Gothic", Font.BOLD, 50));
            String welcomeMessage = "오목 놀자"; //"Let's play omok"
            int messageWidth = g.getFontMetrics().stringWidth(welcomeMessage);
            g.drawString(welcomeMessage, getWidth() / 2 - messageWidth / 2, getHeight() / 2);
        }
    }

    /**
     * Class that handles when there is a mouse press and we are playing.
     */

    class MyMouseListener implements MouseListener {
        private MyBoardGame outer; // Reference to the outer class
        private String [][] tablero;
        //Random r = new Random();

        MyMouseListener(MyBoardGame outer, Board board) {
            this.outer = outer;
            tablero = board.getBoard();
        }

        /**
         * We process a mouse click.
         * @param e the event to be processed
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!inGame) return;
            whoIsPlaying(getGraphics(), isWhiteTurn);
            if (Board.isThereMoves(tablero)){
                int constant = 592 / 16;
                if (outer.gameMode == 1){
                    //We get here if are playing against another human.

                    //We get their mouse click position.
                    int mcX = (int) findClosestIntersection(e.getX(), e.getY()).getY();
                    int mcY = (int) findClosestIntersection(e.getX(), e.getY()).getX();
                    int [] playerMove = {mcY - 1,mcX - 1 };

                    //We check if it was a valid move.
                    if (board.isMovePossible(playerMove)){
                        if (e.getX() > 37 && e.getX() < 592 && e.getY() > 37 && e.getY() < 592){
                            if (isWhiteTurn) {
                                board.modifyPlaceStone(playerMove, "X");
                                whitePiece(getGraphics(), constant * mcX, constant * mcY);
                                if (Board.hasSomeoneWon(board.getBoard())) win(isWhiteTurn);
                            } else {
                                board.modifyPlaceStone(playerMove, "O");
                                blackPiece(getGraphics(), constant * mcX, constant * mcY);
                                if (Board.hasSomeoneWon(board.getBoard())) win(isWhiteTurn);
                            }
                            isWhiteTurn = !isWhiteTurn;
                        }
                    }
                }
                else if (gameMode == 2){
                    //Here we are playing against machine.
                    int mcX = (int) findClosestIntersection(e.getX(), e.getY()).getY();
                    int mcY = (int) findClosestIntersection(e.getX(), e.getY()).getX();
                    int [] playerMove = {mcY - 1,mcX - 1 };

                    //Check if move was valid.
                    if (e.getX() > 37 && e.getX() < 592 && e.getY() > 37 && e.getY() < 592) {
                        if (board.isMovePossible(playerMove)){
                            board.modifyPlaceStone(playerMove, "X");
                            whitePiece(getGraphics(), constant * mcX, constant * mcY);
                            if (Board.hasSomeoneWon(board.getBoard())){
                                win(true);
                            }

                            //Right now the computer is set to the easiest difficulty.
                            //Easiest = random.
                            //Hard = logic from assigment 2
                            int [] cpuCoords = computerMove();
                            board.modifyPlaceStone(cpuCoords, "O");
                            cpuCoords[0] += 1; cpuCoords[1] += 1;
                            blackPiece(getGraphics(), constant * cpuCoords[1], constant * cpuCoords[0]);
                            if (Board.hasSomeoneWon(board.getBoard())) win(false);
                        }
                    }
                    Board.printBoard(board.getBoard());
                }

                //Now we have a third game mode, online mode.
                else if (gameMode == 3) {
                    if (e.getX() > 37 / 2 && e.getX() < 592 && e.getY() > 37 / 2 && e.getY() < 592) {
                        int mcX = (int) findClosestIntersection(e.getX(), e.getY()).getY();
                        int mcY = (int) findClosestIntersection(e.getX(), e.getY()).getX();
                        int[] playerMove = {mcY - 1, mcX - 1};
                        String serverResponse = sendMove2Server(playerMove[0], playerMove[1]);
                        System.out.println(serverResponse);
                        //Check if move is possible, if not, move on.
                        if (!serverResponse.contains("Place not empty")){
                            whitePiece(getGraphics(), constant * mcX, constant * mcY);
                            int [] cpuMoveServer = serverMove(serverResponse);

                            //Here, I was having issue placing pieces and notice that my issue was that the method from Board was static.
                            //A Better solution would have been to use the provided method from past game modes.
                            //Quick patch to use another object.
                            //It just overwrites the original board.

                            errorNotSettingPiecesRight.placeStone(playerMove, "O", cpuMoveServer, "X", tablero2CheckError);
                            board.helperMethodToFixErrorServerIssue(tablero2CheckError);
                            blackPiece(getGraphics(), (cpuMoveServer[1]+1) * constant, (cpuMoveServer[0]+1) * constant);
                        }

                        if (serverResponse.contains("\"isDraw\":true")){
                            draw();
                        }

                        if (serverResponse.contains("isWin\":true")){
                            winCheckByServer(serverResponse, serverResponse.length()<110);
                        }

                    }
                }
            }
            else{
                draw();
            }


        }

        /**
         * What happens after a long game and nothing happens.
         */
        public void draw (){
            JOptionPane.showMessageDialog(outer, "TIE!", "tie", JOptionPane.INFORMATION_MESSAGE);
            board = new Board();
            board.populateBoard();
            tablero = board.getBoard();
            repaint();
            resetGame();
            //inGame = false;
            restartConfirmation = false;
            //showStartScreen();
        }
        /**
         * Modify the difficulty of the machine.
         * It is hard coded to be random moves but this can quickly be changed to up the difficulty.
         * @return move of the computer.
         */
        public int [] computerMove (){
            cpuP CPU = new cpuP(1, "O");
            Random rand = new Random();
            int [] ranAns = new int[]{rand.nextInt(14), rand.nextInt(14)};
            while(!board.isMovePossible(ranAns)){
                ranAns = new int[]{rand.nextInt(14), rand.nextInt(14)};
            }

            //If you want to play against a higher difficulty
            int [] cpuCoords = CPU.cheat(board.getBoard());
            return ranAns;
        }

        /**
         * We send to the server the players move.
         * @param x player move.
         * @param y player move.
         * @return server response.
         *         Example: {"response":true,"ack_move":{"x":4,"y":7,"isWin":false,"isDraw":false,"row":[]},"move":{"x":4,"y":1,"isWin":true,"isDraw":false,"row":[4,1,4,2,4,3,4,4,4,5]}}
         */
        public String sendMove2Server(int x, int y){
            String url = String.format("http://omok.atwebpages.com/play/?pid=%s&x=%d&y=%d", pid, x, y);
            return new MyBoardGame().sendGet(url);
        }


        /**
         * After moving, we read what the bot had to check and get the move.
         * @param serverResponse server answer to read the move.
         * @return returns the move.
         */
        public int [] serverMove(String serverResponse){
            int xValue = -1;
            int yValue = -1;
            // Find the index of "move" in the string
            int moveIndex = serverResponse.indexOf("\"move\"");

            if (moveIndex != -1) {
                // Extract the substring starting from the "move" object
                String moveSubstring = serverResponse.substring(moveIndex);

                // Find the indices of "x" and "y" in the "move" substring
                int xIndex = moveSubstring.indexOf("\"x\"");
                int yIndex = moveSubstring.indexOf("\"y\"");

                // Extract the values of "x" and "y" based on their indices
                xValue = extractValue(moveSubstring, xIndex);
                yValue = extractValue(moveSubstring, yIndex);

                // Store the values in a different place or print them
                System.out.println("x: " + xValue);
                System.out.println("y: " + yValue);
            } else {
                System.out.println("No 'move' object found in the string.");
            }
            return new int [] {xValue, yValue};
        }

        /**
         * Helper method to check the bot's move.
         * @param substring portion of string with moves.
         * @param index position for said moves.
         * @return coordinates.
         */
        private static int extractValue(String substring, int index) {
            // Find the index of the next numeric character after the specified index
            int startIndex = substring.indexOf(':', index) + 1;

            // Find the index of the comma after the numeric value
            int endIndex = substring.indexOf(',', startIndex);

            // If no comma is found, consider the end of the substring
            if (endIndex == -1) {
                endIndex = substring.length() - 1;
            }

            // Extract the numeric value substring and convert it to an integer
            String valueSubstring = substring.substring(startIndex, endIndex).trim();
            return Integer.parseInt(valueSubstring);
        }

        /**
         * What happens if we win.
         * This was made before implementing the network part as I didn't know how it was going to work.
         *
         * @param w, flag to check who wins.
         */
        public void win(boolean w){
            Point [] highlightWin = showWinning(w);
            String winner = w ? "Blue" : "Red";
            JOptionPane.showMessageDialog(outer, winner +" player wins!\n Winning line: " + "("+getCharForNumber(highlightWin[0].y + 1)+"," +highlightWin[0].x+")" + " , " +"("+getCharForNumber(highlightWin[1].y + 1)+"," +highlightWin[1].x+")", "Game Over", JOptionPane.INFORMATION_MESSAGE);

            // Reset the board and play again.
            // This could take you back to the startScreen but that was more of a design thing.
            board = new Board();
            board.populateBoard();
            tablero = board.getBoard();
            repaint();
            resetGame();
            //inGame = false;
            restartConfirmation = false;
            //showStartScreen();
        }

        /**
         * Works very similar to the original win() method.
         * This time is account for the fact that it is the server to tell us if we won or not.
         * @param serverResponse string after someone or something won.
         * @param whoWon flag to determine player that won.
         *               It is determined by the size of the serverResponse as they differ.
         */
        public void winCheckByServer (String serverResponse, boolean whoWon){
            int [][] winRow = getWinningRow(serverResponse, whoWon);
            winingLine(getGraphics(), winRow, whoWon);
            String winner = whoWon? "Human" : "Computer";
            //Shows how win.
            JOptionPane.showMessageDialog(outer, winner +" player wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);


            //Reset game.
            repaint();
            resetGame();
            restartConfirmation = false;
        }

        /**
         * Sever tells you the winning row in a weird format. This method accounts for that.
         * @param serverResponse reads winning row.
         * @param whoWon flag to pain different color who won.
         * @return coordinates of where that win occurred.
         */
        public int [][] getWinningRow (String serverResponse, boolean whoWon){
            //This is a sample of how it looks for the computer to win
            //{"response":true,"ack_move":{"x":4,"y":7,"isWin":false,"isDraw":false,"row":[]},"move":{"x":4,"y":1,"isWin":true,"isDraw":false,"row":[4,1,4,2,4,3,4,4,4,5]}}
            //length = 157

            //This is a sample of how it looks for the player to win
            //{"response":true,"ack_move":{"x":5,"y":8,"isWin":true,"isDraw":false,"row":[5,8,5,7,5,6,5,5,5,4]}}
            //length = 98

            int [][] winRow = new int[2][5];

            int answer = serverResponse.indexOf("row");
            int finish = serverResponse.indexOf("]}}");
            String numbers = serverResponse.substring(answer+6,finish);

            numbers = !whoWon? numbers.substring(numbers.indexOf("row")+6): numbers;

            String [] ans = numbers.split(",");

            for (int i = 0; i < 5; i++){
                winRow[0][i] = Integer.parseInt(ans[i*2]);
                winRow[1][i] = Integer.parseInt(ans[((i+1)*2)-1]);
            }
            return winRow;
        }

        /**
         * The following method are never used but maybe should for a prettier implementation.
         * @param e the event to be processed
         */
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    /**
     * Shows what is the best move in a specific moment.
     * tldr; it checks if someone can win in the next move, if so then block it,
     * then if someone can make 4 in a row and so on.
     */
    @Override
    public void cheats() {
        //Board.printBoard(board.getBoard());
        cpuP CPU = new cpuP(1, "O");
        int [] cheat = CPU.cheat(board.getBoard());
        JOptionPane.showMessageDialog(this, "Best move: \n"+"("+getCharForNumber(cheat[1]+1)+", "+(cheat[0])+")", "Cheat", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * main method.
     * @param args could have args to change screen size, but I decided to keep it fixed.
     */

    public static void main(String[] args) {
        new MyBoardGame().run();
    }
}
