package HW2;

import java.util.Arrays;
import java.util.Scanner;

//Don't forget to implement the AnimationNoApplet

// I do need a class that class another class to handle the messegaes

//showMessage(String) in this hypo class
//selectGameType()
//showBoard()
//selecetNextMove()
public class Game {
    public static void main(String[]args){
        startGame();
    }

    /**
     * Start the game and prints the welcoming messages.
     */
    public static void startGame(){
        Board board = new Board(15);

        humanP player = new humanP(1, "X");
        humanP opponent = new humanP(2, "O");
        cpuP computer = new cpuP(2, "O");

        System.out.println("----------------------------------------------------------------");
        System.out.println("|                       WELCOME TO OMOK!                       |");
        System.out.println("----------------------------------------------------------------");
        System.out.println("|                      FORMAT OF THE GAME:                     |");
        System.out.println("|   Choose first a letter, then the number divided by a coma   |");
        System.out.println("|   This is a valid coordinate position to place a stone: a,1  |");
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("----------------------------------------------------------------");
        System.out.println("|                     AVAILABLE GAME MODES                     |");
        System.out.println("----------------------------------------------------------------");
        System.out.println("|           Type \"1\" to play against another human           |");
        System.out.println("|            Type \"2\" to play against a computer!            |");
        System.out.println("|          Type \"4\" while playing to exit the game.          |");
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        int mode = gameMode();



        String [][] test = board.populateBoard();
        switch (mode) {
            case 1 -> {
                while (Board.isThereMoves()) {
                    Board.printBoard(test);
                    if (Board.placeStone(player, opponent)) {
                        System.out.println("Thanks for playing");
                        break;
                    }
                }
            }
            case 2 -> {
                while (Board.isThereMoves()) {
                    Board.printBoard(test);
                    if (Board.placeStone(player, computer)) {
                        System.out.println("Thank you for playing.");
                        break;
                    }
                }
            }
            case 3-> {
                int cheat = secreteMode();
                playWithCheats(cheat, test, player, opponent, computer);
            }

            default -> System.out.println("Invalid option here");
        }
    }

    /**
     * Handles what game mode to play depending on the users input.
     * @return number of game mode.
     */
    public static int gameMode (){
        Scanner scan = new Scanner(System.in);

        int mode = 0;
        boolean flag = true;
        while(flag){
            System.out.print("Game mode: ");
            String gameMode = scan.nextLine();
            try{
                mode = Integer. parseInt(gameMode);
                if (mode == 1 || mode == 2 || mode == 3){
                    flag = false;
                    break;
                }
                else{
                    System.out.println("Invalid option");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid option");
            }
        }
        return  mode;

    }

    /**
     * This is the secrete mode for the extra credit.
     * @return int to make the selection of the game mode.
     */
    public static int secreteMode(){
        System.out.println("----------------------------------------------------------------");
        System.out.println("|                         secrete mode                          |");
        System.out.println("----------------------------------------------------------------");
        System.out.println("|            Type \"41\" to  show your best move PvP            |");
        System.out.println("|           Type \"42\" to  show your best move PvCPU           |");
        System.out.println("| Type \"51\" to show both your opponent and your best move PVP |");
        System.out.println("| Type \"52\" to show both your opponent and your best move PVC |");
        System.out.println("----------------------------------------------------------------");
        System.out.println();

        int cheat = gameMode("Choose a cheat: ", 41, 42, 51, 52);
        return cheat;
    }

    /**
     * Makes the user select an input for a cheat "level".
     * I have mulitple parameter, so I could overload this method with the one I already had but that proved to be confusing so now it's like this.
     *1
     * @param message decides what message should be printed once the secrete menu has been activated to read the users input.
     * @param i makes the number of the first option.
     * @param j makes the selection number of the 2nd option.
     * @param x makes the selection number of the 3rd option.
     * @param y makes the selection number of the 4th option.
     * @return int of the selection for the game mode.
     */
    public static int gameMode (String message, int i, int j, int x, int y){
        Scanner scan = new Scanner(System.in);

        int mode = 0;
        boolean flag = true;
        while(flag){
            System.out.print(message);
            String gameMode = scan.nextLine();
            try{
                mode = Integer. parseInt(gameMode);
                if (mode == i || mode == j || mode == x || mode == y || mode == 3){
                    break;
                }
                else{
                    System.out.println("Invalid option");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid option");
            }
        }
        return  mode;

    }

    /**
     * Works very similar to startGame() but this time with cheats so there are more options.
     *
     * @param mode int to decide what type of game you would like.
     * @param test the board.
     * @param player human player
     * @param opponent human opponent.
     * @param computer computer player.
     */
    public static void playWithCheats (int mode, String [][] test, humanP player, humanP opponent, cpuP computer){
        switch (mode) {
            case 41 -> {
                while (Board.isThereMoves()) {
                    Board.printBoard(test);
                    int [] options =  player.cheat(test);
                    String letter = Player.numberToLetter(options[1]);
                    System.out.println("Best move: ["+letter+", "+options[0]+"]");
                    if (Board.placeStone(player, opponent)) {
                        System.out.println("Thanks for playing");
                        break;
                    }
                }
            }
            case 42 -> {
                while (Board.isThereMoves()) {
                    Board.printBoard(test);
                    int [] options =  player.cheat(test);
                    String letter = Player.numberToLetter(options[1]);
                    System.out.println("Best move: ["+letter+", "+options[0]+"]");
                    if (Board.placeStone(player, computer)) {
                        System.out.println("Thank you for playing the game");
                        break;
                    }
                }
            }
            case 52 -> {
                while (Board.isThereMoves()) {
                    Board.printBoard(test);
                    int [] options =  player.cheat(test);
                    String letter = Player.numberToLetter(options[1]);
                    System.out.println("Best move: ["+letter+", "+options[0]+"]");
                    int [] opsOptions =  computer.cheat(test);
                    String letra = Player.numberToLetter(opsOptions[1]);
                    System.out.println("Best move for opponent: ["+letra+", "+options[0]+"]");
                    if (Board.placeStone(player, computer)) {
                        System.out.println("Thanks for playing");
                        break;
                    }
                }
            }
            case 51 -> {
                while (Board.isThereMoves()) {
                    Board.printBoard(test);
                    int [] options =  player.cheat(test);
                    String letter = Player.numberToLetter(options[1]);
                    System.out.println("Best move: ["+letter+", "+options[0]+"]");
                    int [] opsOptions =  computer.cheat(test);
                    String letra = Player.numberToLetter(opsOptions[1]);
                    System.out.println("Best move for opponent: ["+letra+", "+options[0]+"]");
                    if (Board.placeStone(player, computer)) {
                        System.out.println("Thank you for playing the game");
                        break;
                    }
                }
            }
            case 3-> {
                cantBeliveYouveDoneThis();
                System.out.println("Don't choose 3 again.");
            }
        }
    }

    /**
     * Easter egg if you try to get more cheats. Cheating is bad.
     */
    private static void cantBeliveYouveDoneThis(){

        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠤⣤⣀⣀⡤⠵⠛⠋⠉⠉⠉⠉⠉⠉⠉⠛⠳⠶⣤⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⢀⡴⠚⢉⣠⣴⡖⡟⠻⣶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠫⢟⠶⣦⣄⡀⠀");
        System.out.println("⠀⠀⠀⠀⠀⢠⡶⠋⠐⠀⠀⠀⠀⠀⠀⠀⠸⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⢦⡀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⢈⡤⠂⠀⠀⠀⠀⠀⠀⣼⠀⠀⣿⠇⠀⠀⠀⠀⠀⠀⠀⣀⡤⠴⠚⠋⠉⠉⠉⠹⣦⡀⠀⠀⠙⢦⣤⣄⡀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⢸⡁⠀⠀⠀⠀⠀⠀⢰⡿⠀⢠⡏⠀⠀⠀⠀⠀⣀⠴⠚⠁⠀⠀⠀⠀⠀⠀⠀⠀⠹⣿⠄⠀⠤⠬⠿⣆⠉⠳⣄⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⢸⠁⠀⠀⠀⠀⠀⣰⠟⢁⣴⠏⠀⠀⠀⢀⡴⠚⠁⠀⠀⠀⠀⠀⠀⢀⣠⣞⣿⠿⠗⠊⠣⠀⠀⠀⠀⠹⡄⠀⠘⢦⡀⠀⠀");
        System.out.println("⣼⠀⠀⠀⠀⡞⠀⠀⠀⠀⠀⣰⣏⣴⣿⠃⠀⠀⢀⡴⠋⠀⠀⠀⠀⠀⠀⠀⢀⣴⠟⠛⠁⠀⠀⠀⠀⠀⠀⠀⠻⣿⡄⢻⠀⠀⠀⢳⡀⠀");
        System.out.println("⠘⢦⣠⡤⠞⠀⢠⠃⠀⠀⣰⡟⢏⣾⠃⠀⢀⡴⠋⠀⠀⠀⠀⠀⠀⢀⡤⠖⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠈⢧⠘⡇⠀⠀⡀⢳⡀");
        System.out.println("⠀⠀⠙⡷⠀⠀⣸⠀⠀⣰⣿⢉⣾⠃⠀⣠⠋⠀⠀⠀⠀⣀⡤⠴⠚⣹⣿⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⡇⠀⡀⠀⢸⠀⣇⠀⠀⠰⡀⣗");
        System.out.println("⠀⠀⠀⡇⠀⠀⡯⡄⡴⠛⣿⡛⠳⠦⠾⠥⠤⠶⠖⠚⠙⢿⡟⠷⣦⣿⡀⢻⣧⠀⠀⠀⠀⠀⢀⣴⡾⢿⡇⢰⡇⠀⢸⡃⣿⠀⠀⠀⢇⣿");
        System.out.println("⠀⠀⠀⡇⠀⠀⣷⡟⣡⡾⠏⠙⢦⣄⡀⠀⠀⠀⠀⠀⠀⠈⢿⣆⣾⠋⠛⢷⣿⣇⣀⣤⠴⠞⠛⠉⢀⣾⣱⢿⡇⠀⠘⡇⡿⠀⠀⠀⠨⡿");
        System.out.println("⠀⠀⠀⣽⣄⠀⢻⡄⠉⠀⠀⠀⡾⠈⠙⠛⠶⣶⡶⠦⠤⠤⠤⠿⠷⠶⠒⠚⠉⠉⠀⠀⠀⠀⠀⠰⠛⠋⠁⠸⣿⠀⠀⣧⡇⠀⠀⠀⠐⡇");
        System.out.println("⠀⠀⣼⢡⠍⠳⢬⣳⣄⡀⠀⣼⠁⠀⠀⠀⢀⡞⠙⢦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡆⠀⣿⣷⡤⡀⠀⠀⡇");
        System.out.println("⠀⢸⡇⣾⠀⠀⠀⢠⠅⠀⣼⡇⠀⠀⠀⠀⣼⠁⠀⠀⠈⠛⢷⣶⣤⠄⠀⠀⠀⠀⠀⣴⠾⠳⣴⠳⡄⠀⠀⠀⢸⣧⢚⣿⣷⡹⠇⠀⢠⠇");
        System.out.println("⠀⢸⢣⣿⡀⠀⠀⣾⠀⢰⣿⠀⡀⠀⠀⢀⡏⢲⣄⡀⠠⢤⣀⣀⠀⠀⠀⠀⠀⠀⡟⢿⠀⣆⢹⡇⢳⣖⠊⢉⡾⠗⠋⣿⠇⣻⡄⢀⡼⠀");
        System.out.println("⠀⢸⡘⣿⢱⢆⢰⣏⠆⣽⡇⢠⠇⠀⠀⢸⠁⠀⠻⡿⣷⣶⣦⣼⠀⠀⠀⠀⠀⠀⢷⠀⠁⠀⠀⠀⠸⢯⡇⣼⠇⠀⠀⡏⢀⣟⣠⠞⠀⠀");
        System.out.println("⠀⠈⠳⡟⢧⡘⢼⣯⠾⣸⡇⢸⡀⠀⠀⣸⠀⠀⠀⠀⠀⠉⠙⠁⠀⠀⠀⠀⠀⠀⠸⡆⠀⠀⣀⣀⢀⡞⢸⢧⡃⠀⢸⡇⠘⠉⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠙⢦⡿⣷⣹⢣⣸⡇⠀⠀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣪⣭⠤⠬⣿⡀⣾⢒⣳⠀⢸⡇⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠈⠹⡄⢿⣿⡀⠀⢺⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⡄⠢⢅⠐⣿⠷⣩⠾⡆⢸⠃⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢾⢳⡱⢄⢸⡍⠓⢤⣀⠀⠀⠐⠯⠭⠭⣔⣒⠢⡄⠀⢸⡔⢨⠑⡄⠢⢿⣙⢦⡹⡇⢸⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢦⣙⣧⡀⢀⣠⠟⠓⠲⠦⣤⣤⣀⣀⣬⣥⣤⣼⡇⠢⠡⢌⡑⢺⣏⠶⣱⡇⡾⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣷⢷⡟⠷⣄⠀⢀⣴⢾⣷⣼⣴⣦⡀⣿⣿⡇⢡⠃⡔⢨⠀⣿⡾⢿⡾⠁⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⣷⢷⡟⠷⣄⠀⢀⣴⢾⣷⣼⣴⣦⡀⣿⣿⡇⢡⠃⡔⢨⠀⣿⡾⢿⡾⠁⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠛⡙⢳⡿⣄⣈⣳⣬⣿⣾⣿⣻⣿⣿⢧⢿⣿⣇⠂⡃⠔⢂⠅⣸⡇⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡼⠃⢆⠡⠂⣿⠈⠻⣧⣠⢈⣿⣿⣥⣿⣿⣤⡾⢹⣿⡄⠱⡈⠆⡌⢸⡷⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⠃⠌⡠⢁⠂⣿⡈⠐⠠⢉⣛⣏⣘⣻⣣⡞⠃⠄⠂⣿⣿⣦⣁⣌⣐⡼⠃⠀⠀⠀");

    }

}
