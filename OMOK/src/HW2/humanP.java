package HW2;
import java.util.*;
public class humanP extends Player{


    /**
     * Uses the constructor of the parent class, Player.
     *
     * @param turn give a turn to the human.
     * @param pieceType give a String to draw on the board.
     */
    public humanP(int turn, String pieceType){
        super(turn, pieceType);
    }

    /**
     * Overrides the parent's method to include extra options, like prompt the player to make a move, check if it's correct or us cheat.
     *
     * @param tablero reads the board to check if the random coordinates are not previously occupied
     * @return set of coordinates in an array.
     */
    @Override
    public int [] coord (String [][] tablero){
        Scanner scan = new Scanner(System.in);
        String move = "";
        System.out.print("Player "+whatTypeOfPlayer()+" make a move: ");
        move = scan.nextLine();
        move = move.replaceAll("\\s","");
        move = move.toLowerCase();
        getString(move);
        int [] justInCase = new int[1];

        if (move.equals("4")){
            justInCase[0] = 1233457754;
            return justInCase;
        }
        String [] coords = move.split(",");
        while (true){
            if (!(isInAlphabet(coords[0]) && coords[0].length() == 1 && coords[0].charAt(0) >= 'a' && coords[0].charAt(0) <= 'o')){
                System.out.println("Invalid option as a letter, choose again: ");
                move = scan.nextLine();
                move = move.replaceAll("\\s","");
                move = move.toLowerCase();
                coords = move.split(",");
            }
            else if(!isNumeric(coords[1]) || Integer.parseInt(coords[1]) > 15){
                System.out.println(coords[1]);
                System.out.println("Invalid option as a number, choose again: ");
                move = scan.nextLine();
                move = move.replaceAll("\\s","");
                move = move.toLowerCase();
                coords = move.split(",");
            }
            else{
                break;
            }
        }
        int y = Integer.parseInt(coords[1]);
        coords[0] = coords[0].toLowerCase();
        int x = coords[0].charAt(0) - 'a';
        int [] temp = new int[2];
        temp [0] = y;
        temp [1] = x;
        return temp;
    }

    /**
     * Checks if given string is number to correctly take the input of the user.
     *
     *
     * @param strNum given String from the class that prompts the user for an input.
     * @return true` if the string is a number, `false` otherwise
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     *
     * Very similar to isNumeric, it checks if the given string is a letter.
     * Later we check if the string is a letter for the desire destinations.
     *
     * @param str given String from what the user inputs.
     * @return true` if the string is in the alphabet, `false` otherwise
     */
    public static boolean isInAlphabet(String str) {
        if (str == null || str == ""||str.length()>1) {
            return false;
        }
        return Character.isLetter(str.charAt(0));

    }

    public String [] getString(String move){
        move = move.replaceAll("\\s","");
        move = move.toLowerCase();
        String [] getOut = new String[]{"4"};
        if (move.equals("4")){
            return getOut;
        }

        if (move.length() <= 1 || move.length() > 3) return new String[]{getOut[-1]};
        return move.split(",");

    }

}