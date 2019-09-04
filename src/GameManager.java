import java.util.*;
import java.lang.*;
import java.io.*;


public class GameManager {

    // Instance variables
    private Board board;    // The actual Pacman board
    private String outputFileName;  // File to save the board to when exiting

    /*
     * GameManager Constructor
     *
     * Purpose:   Constructs a GameManager to generate new game
     * Parameter: int boardSize - Integer that player wants to set as board size.
     *            String outputBoard - the filename the Board will be saved as.
     *
     */
    GameManager(int boardSize, String outputBoard) throws Exception
    {
        board = new Board(boardSize);
        play();
    }

    /*
     * GameManager Constructor
     *
     * Purpose:   Constructs a GameManager to Load a saved game.
     * Parameter: String inputBoard - the filename the Board will be inputted.
     *            String outputBoard - the filename the Board will be saved as.
     *
     */
    GameManager(String inputBoard, String outputBoard) throws Exception
    {
        board = new Board(inputBoard);
        
    }


    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      w - Move up
    //      s - Move Down
    //      a - Move Left
    //      d - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal

    /*
     * Name:      play
     * Purpose:   Takes in input from the user to specify moves to execute.
     * Parameter: Null.
     * Return:    void.
     *
     */
    public void play() 
    {
        try {
        boolean playing = true;
        
        String playerKey;
        Scanner playerInput = new Scanner(System.in);
        
        
        
        printControls();
        System.out.println(board.toString());
        
        while(playing) {

            if(board.isGameOver()) {
                System.out.println("Game over!");
                playing = false;
                break;
            }


            playerKey = playerInput.next();
            if(playerKey.equals("w")) {
                board.move(Direction.UP);
            }
            if(playerKey.equals("s")) {
                board.move(Direction.DOWN);
            }
            if(playerKey.equals("a")) {
                board.move(Direction.LEFT);
            }
            if(playerKey.equals("d")) {
                board.move(Direction.RIGHT);
            }
            if(playerKey.equals("q")) {
                System.out.println("Quitting, saving game...");
                playing = false;
            }
            board.refreshGrid();
            System.out.println(board.toString());
        }
        playerInput.close();
        System.exit(-1);
        } catch (Exception e) {
            System.out.println("play()");
            System.out.println(e.toString());
        }
    }

    /*
     * Name:      printControls()
     * Purpose:   Print the Controls for the Game.
     * Parameter: Null.
     * Return:    void.
     */
    private void printControls()
    {
        System.out.println("  Controls:");
        System.out.println("    w - Move Up");
        System.out.println("    s - Move Down");
        System.out.println("    a - Move Left");
        System.out.println("    d - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }

}
