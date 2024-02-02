package HW2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//use abstract or interface for this, you can also ask TA if there is a way to have the abstract class do the return methods instead of every class
//Mainly I just want to have something here for the points
public class cpuP extends Player {

    public cpuP(int turn, String pieceType){
        super(turn, pieceType);
    }

    /**
     * Checks if you have won, but this time in prints a different message since it's the computer who won.
     *
     * @param tablero reads the board to check if there are any winners.
     *                tablero is sent here even-when it is not necessary to help other methods such as cheat.
     * @return 'true' if computer has won, 'false' otherwise.
     */
    @Override
    public  boolean haveYouWon(String[][] tablero){
        if (Board.hasSomeoneWon(tablero)){
            System.out.println("Computer has won!");
            return true;
        }
        return false;
    }
}
