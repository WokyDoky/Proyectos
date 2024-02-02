import java.util.*;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("|                             OMOK                             |");
        System.out.println("----------------------------------------------------------------");
        System.out.println("|                      FORMAT OF THE GAME:                     |");
        System.out.println("|   Choose first a letter, then the number divided by a coma   |");
        System.out.println("|   This is a valid coordinate position to place a stone: a,1  |");
        System.out.println("----------------------------------------------------------------");
        Game(Board());
    }
    public static void Game(String [][] tablero){
        printBoard(tablero);
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < 5; i++){
            tablero[i][0] = "X";
        }
        for (int i = 0; i < 5; i++){
            tablero[5][i+1] = "O";
        }
        for (int i = 0; i < 5; i++){
            tablero[i][i+6] = "X";
        }
        System.out.println("here");
        System.out.println(areFiveConnected(tablero,5,1));
        System.out.println(areFiveConnected(tablero,4,0));
        System.out.println(areFiveConnected(tablero,0,0));
        System.out.println(tablero[5][0]);


        while(isThereMoves(tablero)){
            break;
            //Player 1
//            System.out.println("Player");
//            int [] coo = isMovePossible(tablero); //if move is possible, save the x, y coordinates.
//            if(areFiveConnected(tablero,coo[0],coo[1])){
//                System.out.println("you have won!!");
//                break;
//            }
//            System.out.println(coo[0]+" - "+coo[1]);
//            tablero[coo[0]][coo[1]] = "X";
//
//            System.out.println("Opponent");
//            //Opponent
//            int [] coordinates = isMovePossible(tablero);
//            if(areFiveConnected(tablero,coordinates[0],coordinates[1])){
//                System.out.println("you have won!!");
//                break;
//            }
//            tablero[coordinates[0]][coordinates[1]] = "O";
//            printBoard(tablero);
//            //System.out.println();
//            //break;
        }
        printBoard(tablero);
    }
    public static String [][] Board(){
        String [][] tablero = new String[15][15];
        populateBoard(tablero);
        return tablero;
    }

    //Next 2 methods are usefull for when I do my Player class
    public static int [] coord (String [][] tablero){
        Scanner scan = new Scanner(System.in);
        System.out.print("Make a move: ");
        String move = scan.nextLine();
        String [] coords = move.split(",");
        int y = Integer.parseInt(coords[1]);
        coords[1] = coords[1].toLowerCase();
        int x = coords[0].charAt(0) - 'a';
        int [] ans = new int[2];
        ans [0] = y;
        ans [1] = x;
        return ans;
    }
    public static int [] isMovePossible(String [][] tablero){
            int [] ans = coord(tablero);
            if (ans[0] > 15 || ans[1] > 15){
                System.out.println("Index out of order");
            }
            else if (tablero[ans[0]][ans[1]].equals("X")|| tablero[ans[0]][ans[1]].equals("O")){
                System.out.println("Position is occupied");
            }
            else{
                return ans;
            }
            return ans;

    }
//    public static void opponent(String [][] tablero){
//        while (true){
//            int [] ans = coord(tablero);
//            if (ans[0] > 15 || ans[1] > 15){
//                System.out.println("Index out of order");
//            }
//            else if (tablero[ans[0]][ans[1]].equals("X")|| tablero[ans[0]][ans[1]].equals("O")){
//                System.out.println("Position is occupied");
//            }
//            else{
//                tablero[ans[0]][ans[1]] = "O";
//                break;
//            }
//        }
//    }
    public static boolean isThereMoves (String [][] tablero){
        boolean flag = false;
        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero.length; j++){
                if(tablero[i][j].equals(".")){
                    flag = true;
                }
            }
        }
        return flag;
    }
    public static boolean areFiveConnected(String [][] tablero, int x, int y){
        System.out.println("Vertical           :  "+hasFiveInARowVertical(tablero,x,y));
//        System.out.println("Diagonally           :  "+northeastDiagonal(tablero,x,y));
//        System.out.println("Diagonally southWest :  "+southwestDiagonal(tablero, x, y));
//        System.out.println("Horizontal             :  "+hasFiveInARowHorizontal(tablero, x, y));

        return hasFiveInARowVertical(tablero,x,y) || northeastDiagonal(tablero,x,y) || southwestDiagonal(tablero, x, y) || hasFiveInARowHorizontal(tablero, x, y);

    }
    private static boolean hasFiveInARowVertical(String[][] array, int row, int col) {
        // Check if the current element and the next 4 elements in the row are all the same symbol.
        for (String [] line: array){
            System.out.println(Arrays.toString(line));
            for (int i = 0; i < line.length - 4; i++) {
                System.out.println(!line[i].equals(".")+line[i]+"here");
                if (!line[i].equals(".") || line[i].equals(line[i + 1]) && line[i + 1].equals(line[i + 2]) && line[i + 2].equals(line[i + 3]) &&line[i + 3].equals(line[i + 4])) {
                    return true;
                }
            }
        }

        // If we reach here, there are 5 of the same symbols in a row horizontally.
        return false;
    }
    private static boolean hasFiveInARowHorizontal(String[][] array, int row, int col) {
        // Check if the current element and the next 4 elements in the column are all the same symbol.
        for (int i = 0; i < 5; i++) {
            if (row + i >= array.length || !array[row + i][col].equals(array[row][col]) || array[row][col].equals(".")) {
                return false;
            }
        }

        // If we reach here, there are 5 of the same symbols in a row vertically.
        return true;
    }
    private static boolean northeastDiagonal(String[][] tablero, int row, int col) {
        // ascendingDiagonalCheck
        for (int i = 0; i < 5; i++) {
            // Check northeast diagonal.
            if (row + i >= tablero.length || col + i >= tablero[row].length || !tablero[row + i][col + i].equals(tablero[row][col]) || tablero[row][col].equals(".")) {
                return false;
            }
        }
        return true;
    }
    private static boolean southwestDiagonal(String[][] tablero, int row, int col) {
        // ascendingDiagonalCheck
        for (int i = 0; i < 5; i++) {
            // Check northeast diagonal.
            if (row - i < 0 || col + i >= tablero[row].length || !tablero[row - i][col + i].equals(tablero[row][col]) || tablero[row][col].equals(".")) {
                return false;
            }
        }
        return true;
    }
    public static void populateBoard(String [][]  tablero){
        for (int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero.length; j++){
                tablero[i][j] = ".";
            }
        }
    }
    public static void printBoard(String[][] tablero){
        System.out.println("    a   b   c   d   e   f   g   h   i   j   k   l   m   n   o");
        for (int i = 0; i < tablero.length; i++){
            if (i < 10){System.out.print(i+"  ");}
            else {System.out.print(i+" "); }
            for (int j = 0; j < tablero.length; j++){
                System.out.print("["+tablero[i][j]+"] ");
            }
            System.out.println();
        }
        System.out.println("    a   b   c   d   e   f   g   h   i   j   k   l   m   n   o");
    }
}