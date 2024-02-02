package omok.model;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board = new Board();
    Player player = new Player("p1");
    Player opponent = new Player("p2");

    Player computer = new Player("p3");

    /**
     * Wil check that boards are created successfully.
     * Will test the size are set accordingly.
     */
    @Test
    void size() {
        var biggerBoard = new Board(19);
        var impossibleBoard = new Board(-1);
        assertEquals(15, board.size());
        assertEquals(19, biggerBoard.size());
        assertEquals(15, impossibleBoard.size());
    }


    /**
     * Will check that after placing one stone and calling the method, the board resets.
     */
    @Test
    void clear() {
        // I will place a stone then clean it.
        board.placeStone(1,1,new Player("p1"));
        assertTrue(board.isOccupied(1,1));

        board.clear();
        assertFalse(board.isOccupied(1,1));
    }

    /**
     * Checks if board is full, which should be false since it has just been initialized.
     * After filling the board, check again but this time it should be true.
     * Clears the board and fill only one column and checks again just to make sure.
     */
    @Test
    void isFull() {
        var board = new Board(6);
        assertFalse(board.isFull());
        for (int i = 0; i < board.size(); i++){
            for (int j = 0; j < board.size(); j++){
                board.placeStone(i , j, player);
            }
        }
        assertTrue(board.isFull());

        board.clear();
        for (int i = 0; i < 5; i ++) board.placeStone(i,0, player);
        assertFalse(board.isFull());
    }

    /**
     * Checks that stone has been placed.
     * Checks the exceptions just in case.

     * Adds stones with other players to test every line.
     */
    @Test
    void placeStone() {
        board.placeStone(1,1,player);
        assertTrue(board.isOccupied(1,1));
        assertFalse(board.isFull());

        assertThrows(IndexOutOfBoundsException.class, () -> board.placeStone(20,20,player));
        assertThrows(IllegalArgumentException.class, () -> board.placeStone(1,1,player));

        board.placeStone(2,2,opponent);
        board.isOccupiedBy(2,2, opponent);
        board.placeStone(3,3,computer);
        board.isOccupiedBy(3,3,computer);
    }

    /**
     * Checks every single space in board to see if it's empty.
     * Should return true since board has just been created.
     * After placing a stone, we check to see if that space is empty.
     * Should return False.
     */
    @Test
    void isEmpty() {
        for (int i = 0; i < board.size(); i++){
            for (int j = 0; j < board.size(); j++){
                assertTrue(board.isEmpty(i,j));
            }
        }
        board.placeStone(1,1, new Player("p1"));
        assertFalse(board.isEmpty(1,1));
    }

    /**
     * Check every position to see if it has been occupied.
     */

    @Test
     void isOccupied() {
        var board = new Board();
        for (int i = 0; i < board.size(); i++){
            for (int j = 0; j < board.size(); j++){
                assertFalse(board.isOccupied(i,j));
            }
        }
        board.placeStone(1,1, new Player("p1"));
        assertTrue(board.isOccupied(1,1));
    }

    /**
     * After placing a stone, we check to see if that same spot has been marked by a player.
     * Assesses true if the player has indeed placed a stone there.
     */
    @Test
    void isOccupiedBy() {
        board.placeStone(1,1, player);
        assertTrue(board.isOccupiedBy(1,1, player));
        assertFalse(board.isOccupiedBy(1,1, opponent));

        board.placeStone( 2, 2, opponent);
        assertTrue(board.isOccupiedBy(2,2,opponent));

        board.placeStone( 3, 3, computer);
        assertTrue(board.isOccupiedBy(3,3,computer));
    }

    /**
     * After placing a stone, we check to see if that same spot has been marked by our player.
     * Assesses true if the player has indeed placed a stone there.
     */
    @Test
    void playerAt() {
        board.placeStone(1,1, player);
        assertEquals("p1", board.playerAt(1, 1).name());
        assertNotEquals("p2", board.playerAt(1,1).name());

        board.placeStone(2,2,opponent);
        assertEquals("p2", board.playerAt(2,2).name());

        board.placeStone(3,3, computer);
        assertEquals("p3", board.playerAt(3,3).name());
    }

    /**
     * Check to see if the board is in the state of "won" and by whom.
     * Assesses true if someone has won.
     */
    @Test
    void isWonBy() {


        //I could have cleared the board, but I wanted to show the different type of winning possibilities.

        var horizontallWinningBoard = new Board (5);
        for (int i = 0; i < 5; i++) horizontallWinningBoard.placeStone(0,i, player);
        assertTrue(horizontallWinningBoard.isWonBy(player));
        assertFalse(horizontallWinningBoard.isWonBy(opponent));

        var verticalWinningBoard = new Board (5);
        for (int i = 0; i < 5; i++) verticalWinningBoard.placeStone(i,0, player);
        assertTrue(verticalWinningBoard.isWonBy(player));
        assertFalse(verticalWinningBoard.isWonBy(opponent));

        //Northwest - Southeast
        var diagonalWinningBoard = new Board(5);
        for (int i = 0; i < 5; i++) diagonalWinningBoard.placeStone(i,i, player);
        assertTrue(diagonalWinningBoard.isWonBy(player));
        assertFalse(diagonalWinningBoard.isWonBy(opponent));

        //Southwest - Northeast
        var otherDiagonalWinningBoard = new Board(5);
        for (int i = 0; i < 5; i++) otherDiagonalWinningBoard.placeStone(i,otherDiagonalWinningBoard.size() - 1 - i, player);
        assertTrue(otherDiagonalWinningBoard.isWonBy(player));
        assertFalse(otherDiagonalWinningBoard.isWonBy(opponent));




    }

    /**
     * Assesses true if the Iterable has the correct list of winning moves.
     */
    @Test
    void winningRow() {

        //I could have cleaned the board, but I wanted to show the different type of winning possibilities.
        var horizontalBoard = new Board();
        for (int i = 5; i < 10; i++) horizontalBoard.placeStone(i, 5, player);
        int count = 5;
        for (Board.Place coordinate : horizontalBoard.winningRow()){
            assertEquals(5, coordinate.getCoords()[1]);
            assertEquals(count, coordinate.getCoords()[0]);
            count++;
        }

        var verticalBoard = new Board();
        for (int i = 2; i < 7; i++) verticalBoard.placeStone(i, 10, player);
        count = 2;
        for (Board.Place coordinate : verticalBoard.winningRow()){
            assertEquals(10, coordinate.getCoords()[1]);
            assertEquals(count, coordinate.getCoords()[0]);
            count++;
        }

        //Different size boards and directions.
        //Northwest - Southeast
        var diagonalBoard = new Board (6);
        for (int i = 0; i < 5; i++) diagonalBoard.placeStone(i,i, player);
        for (Board.Place coordinate : diagonalBoard.winningRow()){
            assertEquals(coordinate.getCoords()[0], coordinate.getCoords()[1]);
        }

        //Southwest - Northeast
        var otherDiagonalBoard = new Board(5);
        for (int i = 0; i < 5; i++) otherDiagonalBoard.placeStone(i,otherDiagonalBoard.size() - 1- i, player);
        int t = 1;
        for (Board.Place coordinate : otherDiagonalBoard.winningRow()){
            assertEquals(coordinate.getCoords()[0],  (otherDiagonalBoard.size() - t));
            assertEquals(coordinate.getCoords()[1], t - 1);
            t++;
        }

        //Weird cases:
        var nonWinningBoard = new Board (6);
        for (int i = 0; i < 3; i++) nonWinningBoard.placeStone(i,i, player);
        assertFalse(nonWinningBoard.winningRow().iterator().hasNext());

        var wonByMoreThan5 = new Board ();
        for (int i = 0; i < 7; i++) wonByMoreThan5.placeStone(i,i, player);
        //Even thought it has 7 in a row, it only returns the first 5.
        //This could be changed.
        for (Board.Place coordinate : wonByMoreThan5.winningRow()){
            assertEquals(coordinate.getCoords()[0], coordinate.getCoords()[1]);
        }
    }
}
