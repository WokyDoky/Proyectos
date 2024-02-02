package omok.model;
import java.util.ArrayList;

/**
 * Abstraction of an Omok board, which consists of n x n intersections
 * or places where players can place their stones. The board can be
 * accessed using a pair of 0-based indices (x, y), where x and y
 * denote the column and row number, respectively. The top-left
 * intersection is represented by the indices (0, 0), and the
 * bottom-right intersection is represented by the indices (n-1, n-1).
 */
public class Board {
    int [][] board;

    /** Create a new board of the default size. */
    //Default size 15
    public Board() {board = new int [15][15];}

    /** Create a new board of the specified size. */
    public Board(int size) {
        if (size < 5){
            System.out.println("Size to small. Default board created.");
            board = new int [15][15];
            return;
        }
        board = new int [size][size];
    }

    /** Return the size of this board. */
    public int size() {
        return board.length;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        board = new int [15][15];
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for (int [] l: board){
            for (int b: l){
                if (b == 0) return false;
            }
        }
        return true;
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {

        //should I check is position is available?

        if (x >= board.length || y >= board.length){
            throw new IndexOutOfBoundsException("Index out of bounds");
        } else if (isOccupied(x,y)) {
            throw new IllegalArgumentException("Position is occupied");
        } else{
            if (player.name().equals("p1")) board [x][y] = 1;
            if (player.name().equals("p2")) board [x][y] = 2;
            if (player.name().equals("p3")) board [x][y] = 3;
        }
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        return board[x][y] == 0;
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        return board[x][y] != 0;
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupiedBy(int x, int y, Player player) {
        //let's assume that player's name is p1 and p2
        //Computer name is p3
        if (player.name().equals("p1") && board[x][y] == 1) return true;
        if (player.name().equals("p2") && board[x][y] == 2) return true;
        return player.name().equals("p3") && board[x][y] == 3;
    }

    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y) {
        //let's assume that player's name is p1 and p2
        //Computer name is p3

        if (board[x][y] == 1) return new Player ("p1");
        if (board[x][y] == 2) return new Player ("p2");
        if (board[x][y] == 3) return new Player ("p3");
        return null;
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(Player player) {
        int playerPieceType = -1;
        if (player.name().equals("p1")) playerPieceType = 1;
        if (player.name().equals("p2")) playerPieceType = 2;
        if (player.name().equals("p3")) playerPieceType = 3;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0 || board[i][j] != playerPieceType){
                    continue;
                }
                // Check horizontally
                if (horizontalHelper(i,j,board)) {
                    return true;
                }

                // Check vertically
                if (verticalHelper(i,j,board)) {
                    return true;
                }

                // Check diagonally (northeast to southwest)
                if (diagonalHelperNE(i,j,board)) {
                    return true;
                }

                // Check diagonally (southwest to northeast)
                if (diagonalHelperNW(i,j,board)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean horizontalHelper(int i, int j, int [][] board){
        return (j + 4 < board[i].length && board[i][j] == (board[i][j + 1]) && board[i][j + 1] == (board[i][j + 2]) && board[i][j + 2] == (board[i][j + 3]) && board[i][j + 3] == (board[i][j + 4]));
    }
    public boolean verticalHelper (int i, int j, int [][] board){return (i + 4 < board.length && board[i][j] == (board[i + 1][j]) && board[i + 1][j] == (board[i + 2][j]) && board[i + 2][j] == (board[i + 3][j]) && board[i + 3][j] == (board[i + 4][j]));}

    public boolean diagonalHelperNE (int i , int j, int [][] board){return (i + 4 < board.length && j + 4 < board[i].length && board[i][j] == (board[i + 1][j + 1]) && board[i + 1][j + 1] == (board[i + 2][j + 2]) && board[i + 2][j + 2] == (board[i + 3][j + 3]) && board[i + 3][j + 3] == (board[i + 4][j + 4]));}

    public boolean diagonalHelperNW (int i, int j, int [][] board){
        return (i - 4 >= 0 && j + 4 < board[i].length && board[i][j] == (board[i - 1][j + 1]) && board[i - 1][j + 1] == (board[i - 2][j + 2]) && board[i - 2][j + 2] == (board[i - 3][j + 3]) && board[i - 3][j + 3] == (board[i - 4][j + 4]));
    }
    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {

        ArrayList<Place> place = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0){
                    continue;
                }
                // Check horizontally
                if (horizontalHelper(i,j,board)) {
                    place.add(new Place(i,j));
                    place.add(new Place(i,j+1));
                    place.add(new Place(i,j+2));
                    place.add(new Place(i,j+3));
                    place.add(new Place(i,j+4));
                    return place;
                }

                // Check vertically
                if (verticalHelper(i,j,board)) {
                    place.add(new Place(i,j));
                    place.add(new Place(i+1,j));
                    place.add(new Place(i+2,j));
                    place.add(new Place(i+3,j));
                    place.add(new Place(i+4,j));
                    return place;
                }

                // Check diagonally (northWest to southeast)
                if (diagonalHelperNE(i,j,board)) {
                    place.add(new Place(i,j));
                    place.add(new Place(i + 1,j + 1));
                    place.add(new Place(i + 2,j + 2));
                    place.add(new Place(i + 3,j + 3));
                    place.add(new Place(i + 4,j + 4));
                    return place;
                }

                // Check diagonally (southwest to northeast)
                if (diagonalHelperNW(i,j,board)) {
                    place.add(new Place(i,j));
                    place.add(new Place(i - 1,j + 1));
                    place.add(new Place(i - 2,j + 2));
                    place.add(new Place(i - 3,j + 3));
                    place.add(new Place(i - 4,j + 4));
                    return place;
                }
            }
        }
        return place;
        //Here I could return null. Should ask.
    }

    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position
     * of a place on the board, with (0, 0) denoting the top-left
     * corner and (n-1, n-1) denoting the bottom-right corner,
     * where n is the size of the board.
     *
     * @param x 0-based column index of this place.
     * @param y 0-based row index of this place.
     */
        public record Place(int x, int y) {
        /**
         * Create a new place of the given indices.
         *
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place {
        }
        /**
         *
         * @return create an int [] array that will return the values of the place where there are pieces.
         * This is for ease of use. It can be done with another list or have 2 methods that return x and y.
         */
        public int[] getCoords() {
                return new int[]{x, y};
            }

            // other methods if needed ...
        }
}

