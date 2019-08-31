import java.util.*;
import java.io.*;


public class GameManager {

    // Instance variables
    private Board board;    // The actual Pacman board
    private String outputFileName;  // File to save the board to when exiting

    /*
     * GameManager Constructor
     *
     * Purpose:   Constructs a GameManager to Generate new game
     * Parameter: int boardSize - Integer player want to set as board size.
     *            String outputBoard - the filename the Board will be saved as.
     *            Random random - the Random generator player wanna use.
     *
     */
    GameManager(int boardSize, String outputBoard) throws Exception
    {
        // TODO
    }

    /*
     * GameManager Constructor
     *
     * Purpose:   Constructs a GameManager to Load a saved game.
     * Parameter: String inputBoard - the filename the Board will be inputted.
     *            String outputBoard - the filename the Board will be saved as.
     *            Random random - the Random generator player wanna use.
     *
     */
    GameManager(String inputBoard, String outputBoard) throws Exception
    {
        // TODO
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
    public void play() throws Exception
    {
        // TODO
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
