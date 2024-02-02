package HW2;

import java.util.*;

abstract class Player {
    private int turn;
    private String pieceType;
    public Player(){}

    /**
     *
     * @param turn determine who is playing. human / machine / opponent.
     * @param pieceType differentiates between the two types of pieces.
     */
    public Player(int turn, String pieceType){
        // Fist player = 1
        // Opponent = 2
        this.turn = turn;
        this.pieceType = pieceType;

    }

    /**
     * Returns 2 randomly given coordinates to functions as a "basic" AI to play against.
     * The "AI" can get better if used with the "cheat" method that outputs the best move.
     *
     * @param tablero reads the board to check if the random coordinates are not previously occupied
     * @return 2 numbers in the range of the board in an array.
     */
    public int [] coord(String[][] tablero){
        Random random = new Random();

        int [] ans = new int[2];
        do {
            ans[0] = random.nextInt(15);
            ans[1] = random.nextInt(15);
        } while (!tablero[ans[0]][ans[1]].equals("."));
        return ans;
    }

    /**
     *
     * @return the int that tells the game what player are we dealing with.
     */
    public int whatTypeOfPlayer(){
        return turn;
    }

    /**
     *
     * @return the type of piece to draw in the board.
     */
    public String getPieceType(){
        return pieceType;
    };


    /**
     *
     * @param tablero reads the board to check if there are any winners.
     *                tablero is sent here even-when it is not necessary to help other methods such as cheat.
     * @return 'true' if you have win, 'false' otherwise.
     */
    public boolean haveYouWon(String[][] tablero){
        if (Board.hasSomeoneWon(tablero)){
            System.out.println("Player "+whatTypeOfPlayer()+" has won!");
            return true;
        }
        return false;
    }

    /**
     * Returns the best next move.
     *
     * @param tablero reads a board to give the best next move
     * @return 2 coordinates where the game thinks it's the best position in a 1D array
     * @see "Go-Moku and Threat-Space Search"
     * @see <a href>https://en.wikipedia.org/wiki/Gomoku#Computers_and_gomoku</a>
     */
    public int [] cheat (String [][] tablero){


        String [][] temp = new String[tablero.length][tablero.length];
        ArrayList<int []> ans = new ArrayList<>();

        //This is to copy the board without reference.
        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero.length; j++){
                temp[i][j] = tablero[i][j];
            }
        }
        int [] resp;


        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero.length;j++){
                if (tablero[i][j].equals(".")) {
                    resp = isThereAWin(temp, i, j);
                    if (resp[2] != 0){
                        ans.add(resp);
                    }
                }
            }
        }

        if (ans.isEmpty()) return coordForOtherPiece(tablero);


        int bestOption = Integer.MIN_VALUE;
        int [] possible = new int[2];
        for (int [] possibleAnswer: ans){
            if (possibleAnswer[2] > bestOption) {
                possible = possibleAnswer;
                //possible = Arrays.copyOfRange(possibleAnswer,0,2);
                bestOption = possibleAnswer[2];
            }
        }



        return possible;

    }

    /**
     *
     * @param number number to transform
     * @return letter that corresponds to the number.
     */

    protected static String numberToLetter(int number) {
        if (number < 0 || number > 25) {
            return "Invalid number";
        }
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        return String.valueOf(alphabet[number]);
    }

    /**
     * This method helps the "cheat" method to check if by putting a piece you can win.
     * If you can't then it goes to the next best thing, which is creating a 4 in line.
     * If you also can't then it goes to the next best thing to search for a 3 in line.
     *
     * This method can be used reactively or proactively to check whether the opponent has a winning line.
     *
     * @param temp reads a copy of the orginal board to check if a hypothetical move would work
     * @param i iterate over the intersections.
     * @param j iterate over the intersections.
     * @return a set of coordinates in an array.
     */
    int []isThereAWin(String[][] temp, int i, int j){
        int [] ans = new int[3];

        temp[i][j] = getPieceType();

        if(Board.hasSomeoneWon(temp)){
            ans[0] = i;
            ans[1] = j;
            ans[2] = 5;
            temp[i][j] = ".";
            return ans;
        }
        if(find4Now(temp)){
            ans[0] = i;
            ans[1] = j;
            ans[2] = 4;
            temp[i][j] = ".";
            return ans;
        }
        if (find3Now(temp)) {
            ans[0] = i;
            ans[1] = j;
            ans[2] = 3;
            temp[i][j] = ".";
            return ans;
        }
        else{
            temp[i][j] = ".";
        }
        temp[i][j] = ".";
        return ans;
    }

    /**
     * Works very similar to the method that check if you have won but instead of 5 in a row, it's for 4 in row.
     * @param tablero reads the hypothetical board to check for best moves.
     * @return true if there is a spot, false elsewhere.
     */

    private boolean find4 (String [][]tablero){
        for (int i = 0; i < tablero.length; i++) { // iterate rows, bottom to top
            for (int j = 0; j < tablero[i].length; j++){
                if (tablero[i][j].equals(".")) return false;
                if (j + 3 < tablero[i].length && tablero[i][j].equals(tablero[i][j + 1]) && tablero[i][j + 1].equals(tablero[i][j + 2]) && tablero[i][j + 2].equals(tablero[i][j + 3])) {
                    return true;
                }
                if (i + 3 < tablero.length && tablero[i][j].equals(tablero[i + 1][j]) && tablero[i + 1][j].equals(tablero[i + 2][j]) && tablero[i + 2][j].equals(tablero[i + 3][j])) {
                    return true;
                }
                if (i + 3 < tablero.length && j + 3 < tablero[i].length && tablero[i][j].equals(tablero[i + 1][j + 1]) && tablero[i + 1][j + 1].equals(tablero[i + 2][j + 2]) && tablero[i + 2][j + 2].equals(tablero[i + 3][j + 3])) {
                    return true;
                }

                if (i - 3 >= 0 && j + 3 < tablero[i].length && tablero[i][j].equals(tablero[i - 1][j + 1]) && tablero[i - 1][j + 1].equals(tablero[i - 2][j + 2]) && tablero[i - 2][j + 2].equals(tablero[i - 3][j + 3])) {

                    return true;
                }

            }

        }

// If we reach here, there are no 4 in a row of the same type of string.

        return false;


    }
    private boolean find4Now(String[][] tablero) {
        // Check horizontally
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length - 3; j++) {
                if (!tablero[i][j].equals(".") &&
                        tablero[i][j].equals(tablero[i][j + 1]) &&
                        tablero[i][j + 1].equals(tablero[i][j + 2]) &&
                        tablero[i][j + 2].equals(tablero[i][j + 3])) {
                    return true;
                }
            }
        }

        // Check vertically
        for (int i = 0; i < tablero.length - 3; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (!tablero[i][j].equals(".") &&
                        tablero[i][j].equals(tablero[i + 1][j]) &&
                        tablero[i + 1][j].equals(tablero[i + 2][j]) &&
                        tablero[i + 2][j].equals(tablero[i + 3][j])) {
                    return true;
                }
            }
        }

        // Check diagonally (bottom-left to top-right)
        for (int i = 3; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length - 3; j++) {
                if (!tablero[i][j].equals(".") &&
                        tablero[i][j].equals(tablero[i - 1][j + 1]) &&
                        tablero[i - 1][j + 1].equals(tablero[i - 2][j + 2]) &&
                        tablero[i - 2][j + 2].equals(tablero[i - 3][j + 3])) {
                    return true;
                }
            }
        }

        // Check diagonally (top-left to bottom-right)
        for (int i = 0; i < tablero.length - 3; i++) {
            for (int j = 0; j < tablero[i].length - 3; j++) {
                if (!tablero[i][j].equals(".") &&
                        tablero[i][j].equals(tablero[i + 1][j + 1]) &&
                        tablero[i + 1][j + 1].equals(tablero[i + 2][j + 2]) &&
                        tablero[i + 2][j + 2].equals(tablero[i + 3][j + 3])) {
                    return true;
                }
            }
        }

        // If no 4 in a row is found, return false
        return false;
    }


    /**
     * Works very similar to the method that check if you have won but instead of 5 in a row,
     * it's for 3 in row. * I believe searching for 2 in a row is not as strong of a strategy.
     * You can spend your time else where, like checking for the opponents best moves.
     * @param tablero  reads the hypothetical board to check for best moves
     * @return true if there is a spot, false elsewhere.
     */
    private boolean find3(String [][] tablero){
        // Check horizontally.
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j].equals(".")) return false;
                if (j + 2 < tablero[i].length && tablero[i][j].equals(tablero[i][j + 1]) && tablero[i][j + 1].equals(tablero[i][j + 2])) {
                    return true;
                }


                if (i + 2 < tablero.length && tablero[i][j].equals(tablero[i + 1][j]) && tablero[i + 1][j].equals(tablero[i + 2][j])) {
                    return true;
                }


                if (i + 2 < tablero.length && j + 2 < tablero[i].length && tablero[i][j].equals(tablero[i + 1][j + 1]) && tablero[i + 1][j + 1].equals(tablero[i + 2][j + 2])) {
                    return true;
                }

                if (i - 2 >= 0 && j + 2 < tablero[i].length && tablero[i][j].equals(tablero[i - 1][j + 1]) && tablero[i - 1][j + 1].equals(tablero[i - 2][j + 2])) {
                    return true;
                }

            }

        }


        return false;


    }
    private boolean find3Now(String[][] tablero) {
        // Check horizontally.
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length - 2; j++) { // Adjusted loop limit
                if (!tablero[i][j].equals(".") && tablero[i][j].equals(tablero[i][j + 1]) && tablero[i][j + 1].equals(tablero[i][j + 2])) {
                    return true;
                }
            }
        }

        // Check vertically.
        for (int i = 0; i < tablero.length - 2; i++) { // Adjusted loop limit
            for (int j = 0; j < tablero[i].length; j++) {
                if (!tablero[i][j].equals(".") && tablero[i][j].equals(tablero[i + 1][j]) && tablero[i + 1][j].equals(tablero[i + 2][j])) {
                    return true;
                }
            }
        }

        // Check diagonally (from top-left to bottom-right).
        for (int i = 0; i < tablero.length - 2; i++) { // Adjusted loop limit
            for (int j = 0; j < tablero[i].length - 2; j++) { // Adjusted loop limit
                if (!tablero[i][j].equals(".") && tablero[i][j].equals(tablero[i + 1][j + 1]) && tablero[i + 1][j + 1].equals(tablero[i + 2][j + 2])) {
                    return true;
                }
            }
        }

        // Check diagonally (from top-right to bottom-left).
        for (int i = 0; i < tablero.length - 2; i++) { // Adjusted loop limit
            for (int j = 2; j < tablero[i].length; j++) { // Adjusted loop limit and starting point
                if (!tablero[i][j].equals(".") && tablero[i][j].equals(tablero[i + 1][j - 1]) && tablero[i + 1][j - 1].equals(tablero[i + 2][j - 2])) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * If you cannot make 3, 4, or 5 in a row then recommend a neighbouring piece.
     * If there are no possible neighbouring pieces, which is highly unlikely then choose at random.
     *
     * @param tablero board to interact with.
     * @return a coordinate that is neighboring another piece.
     */

    private int [] coordForOtherPiece (String [][] tablero){

        int [] ans = new int[3];
        for (int i = 0; i < tablero.length; i ++){
            for (int j = 0; j <tablero.length; j++){
                if (tablero[i][j].equals(getPieceType())){
                    ans[2] = 2;
                    try{
                        if (j+1 < tablero.length){
                            ans[0] = i;
                            ans[1] = j+1;
                            return ans;
                        }
                        if (i+1 < tablero.length){
                            ans[0] = i+1;
                            ans[1] = j;
                            return ans;
                        }
                        if (j-1 >  0){
                            ans[0] = i;
                            ans[1] = j-1;
                            return ans;
                        }
                        if (i-1 >  0){
                            ans[0] = i-1;
                            ans[1] = j;
                            return ans;
                        }
                    }catch (Exception e){
                        return coord(tablero);
                    }

                }
            }
        }
        return ans;
    }


//    private boolean find2 (String [][] tablero){
//        for (int i = 0; i < tablero.length; i++) {
//            for (int j = 0; j < tablero[i].length; j++) {
//                if (tablero[i][j].equals(".")){
//                    // Check horizontally
//                    if (j + 2 < tablero[i].length && tablero[i][j].equals(tablero[i][j + 1])) {
//                        return true;
//                    }
//
//                    // Check vertically
//                    if (i + 2 < tablero.length && tablero[i][j].equals(tablero[i + 1][j])) {
//                        return true;
//                    }
//
//                    // Check diagonally (northeast to southwest)
//                    if (i + 2 < tablero.length && j + 2 < tablero[i].length && tablero[i][j].equals(tablero[i + 1][j + 1])) {
//                        return true;
//                    }
//
//                    // Check diagonally (southwest to northeast)
//                    if (i - 2 >= 0 && j + 2 < tablero[i].length && tablero[i][j].equals(tablero[i - 1][j + 1])) {
//                        return true;
//                    }
//                }
//                return false;
//            }
//        }
//
//        // If we reach here, there are no 5 in a row of the same type of string.
//        return false;
//    }

}
//private boolean find4(String [][] tablero){
//        for (int r = 0; r < tablero.length; r++) { // iterate rows, bottom to top
//            for (int c = 0; c < tablero.length; c++) { // iterate columns, left to right
//                String piece = tablero[r][c];
//                if (piece.equals("."))
//                    continue;
//                if (c + 3 < tablero.length &&
//                        piece.equals(tablero[r][c+1])  &&
//                        piece.equals(tablero[r][c+2]) &&
//                        piece.equals(tablero[r][c+3]))
//                    return true;
//                if (r + 3 < tablero.length) {
//                    if (piece.equals(tablero[r+1][c]) &&
//                            piece .equals(tablero[r+2][c]) &&
//                            piece .equals(tablero[r+3][c]))
//                        return true;
//                    if (c + 3 < tablero.length &&
//                            piece .equals(tablero[r+1][c+1]) &&
//                            piece .equals(tablero[r+2][c+2]) &&
//                            piece .equals(tablero[r+3][c+3]))
//                        return true;
//                    if (c - 3 >= 0 &&
//                            piece .equals(tablero[r+1][c-1]) &&
//                            piece .equals(tablero[r+2][c-2]) &&
//                            piece .equals(tablero[r+3][c-3]))
//                        return true;
//                }
//            }
//        }
//        return false;
//    }
//    /**
//     * Works very similar to the method that check if you have won but instead of 5 in a row, it's for 3 in row.
//     * I believe searching for 2 in a row is not as strong of a strategy.
//     * You can spend your time else where, like checking for the opponents best moves.
//     * @param tablero reads the hypothetical board to check for best moves.
//     * @return set of coordinates in an array.
//     */
//    private boolean find3(String [][] tablero){
//        for (int i = 0; i < tablero.length; i++) {
//            for (int j = 0; j < tablero.length; j++) {
//                if (tablero[i][j].equals("."))
//                    continue;
//                if (j + 2 < tablero.length &&
//                        tablero[i][j].equals(tablero[i][j+1])  &&
//                        tablero[i][j].equals(tablero[i][j+2]))
//                    return true;
//                if (i + 2 < tablero.length) {
//                    if (tablero[i][j].equals(tablero[i+1][j]) &&
//                            tablero[i][j] .equals(tablero[i+2][j]))
//                        return true;
//                    if (j + 2 < tablero.length &&
//                            tablero[i][j] .equals(tablero[i+1][j+1]) &&
//                            tablero[i][j] .equals(tablero[i+2][j+2]) )
//                        return true;
//                    if (j - 2 >= 0 &&
//                            tablero[i][j] .equals(tablero[i+1][j-1]) &&
//                            tablero[i][j] .equals(tablero[i+2][j-2]))
//                        return true;
//                }
//            }
//        }
//        return false;
//    }
