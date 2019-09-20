import java.lang.StringBuilder;
import java.util.*;
import java.io.*;

/**
 * @author Alex Lin
 */
public class Board {

    // FIELD
    public final int GRID_SIZE;

    private char[][] grid;          // String Representation of Pac-man Game Board
    private boolean[][] visited;    // Record of where Pac-man has visited
    private PacCharacter pacman;    // Pac-man that user controls
    private PacCharacter[] ghosts;  // 4 Ghosts that controlled by the program
    private int score;              // Score Recorded for the gamer


    /*
     * Constructor
     *
     * <p> Description: TODO
     *
     * @param:  TODO
     * @return: TODO
     */
    public Board(int size) {
        GRID_SIZE = size;
        int gridCenter = GRID_SIZE / 2;
        int ghostNum = 4;
        int ghostCount = 0;
        score = 0;

        //Initialize pacman object
        pacman = new PacCharacter(gridCenter, gridCenter, 'P');

        //Creates four ghost objects and sets their locations at the corners of the grid
        ghosts = new PacCharacter[4];
        while (ghostCount < ghostNum) {
            for (int x = 0; x < GRID_SIZE; x = x + (GRID_SIZE - 1)) {
                for (int y = 0; y < GRID_SIZE; y = y + (GRID_SIZE - 1)) {
                    ghosts[ghostCount] = new PacCharacter(x, y, 'G');
                    ghostCount++;
                }
            }
        }
        
        //Initialize grid
        grid = new char[GRID_SIZE][GRID_SIZE];
        
        //Initialize visited array
        visited = new boolean[GRID_SIZE][GRID_SIZE];
        //Sets all locations on the board as unvisited by Pac-Man at the start
        for(int x = 0; x < GRID_SIZE; x++) {
            for(int y = 0; y < GRID_SIZE; y++) {
                visited[x][y] = false;
            }
        }
        //Sets Pac-Man's starting location as visited
        setVisited(gridCenter, gridCenter);

        // Using the refreshGrid method to set the grid character
        // representations based on visted, ghosts, pacman states
        refreshGrid();
        
    }
    
    public Board(String saveName) throws FileNotFoundException {

        String boardFileLine;
        FileInputStream fileStream = new FileInputStream(saveName);
        Scanner file = new Scanner(fileStream);

        // Takes the first int as the grid size and the second int as the score
        GRID_SIZE = file.nextInt();
        score = file.nextInt();

        // Advances the scanner to the next line, to the start of the game board
        file.nextLine();

        grid = new char[GRID_SIZE][GRID_SIZE];
        visited = new boolean[GRID_SIZE][GRID_SIZE];
        ghosts = new PacCharacter[4];
        int y = 0;
        int ghostNum = 0;

        while (file.hasNext()) {
            boardFileLine = file.nextLine();
            for (int x = 0; x < GRID_SIZE; x++) {
                
                if(boardFileLine.charAt(x) == '*') {
                    visited[x][y] = false;
                }
                
                if(boardFileLine.charAt(x) == 'G') {
                    visited[x][y] = false;
                    ghosts[ghostNum] = new PacCharacter(x, y, 'G');
                    ghostNum++;
                }
                
                if(boardFileLine.charAt(x) == 'P') {
                    visited[x][y] = true;
                    pacman = new PacCharacter(x, y, 'P');
                }
                
                if(boardFileLine.charAt(x) == ' ') {
                    visited[x][y] = true;
                }
                
            }
            y++;
        }
        refreshGrid();
        file.close();
    }


    public void setVisited(int x, int y) {
        visited[x][y] = true;
    }

    public void refreshGrid() {
        //Assigns an empty slot to the grid if PacMan has visited it.  Else the slot has a Pac-Dot in it
        for(int x = 0; x < GRID_SIZE; x++) {
            for(int y = 0; y < GRID_SIZE; y++) {
                if(visited[x][y] == true) {
                    grid[x][y] = ' ';
                } else {
                    grid[x][y] = '*';
                }
            }
        }
        //Sets slots on the grid to 'G' based on current ghost locations
        for(int ghostNum = 0; ghostNum < ghosts.length; ghostNum++) {            
            grid[ghosts[ghostNum].getX()][ghosts[ghostNum].getY()] = ghosts[ghostNum].getAppearance();
        }
        //Sets pacman's location on the grid
        grid[pacman.getX()][pacman.getY()] = pacman.getAppearance();
        
        //Sets a slot to 'X' if there is a game over where pacman and a ghost are in the same slot
        if(isGameOver()) {
            grid[pacman.getX()][pacman.getY()] = 'X';
        }
    }

    public boolean canMove(Direction direction) {
        //If pacman is trying to move into the border of the board, it cannot move that direction
        if (direction == Direction.UP) {
            if (pacman.getY() == 0) {
                return false;
            }
        }
        
        else if (direction == Direction.DOWN) {
            if (pacman.getY() == GRID_SIZE - 1) {
                return false;
            }
        }

        else if (direction == Direction.LEFT) {
            if (pacman.getX() == 0) {
                return false;
            }
        }

        else if (direction == Direction.RIGHT) {
            if (pacman.getX() == GRID_SIZE - 1) {
                return false;
            }
        }
        return true;
    }

    public void move(Direction direction) {
        //Only moves the characters if a valid direction is entered
        if(canMove(direction) == true) {
            //Store the position of pacman before he moves
            int pacX = pacman.getX();
            int pacY = pacman.getY();
            
            //Declare variables for the positions of ghosts before movement
            Direction ghostDirection;
            int ghostX;
            int ghostY;
            
            //Move pacman up or down depending on the user input
            if(direction == Direction.UP || direction == Direction.DOWN) {
                pacman.setPosition(pacX, pacY + direction.getY());
            }
            //Move pacman left or right depending on the user input
            else if(direction == Direction.LEFT || direction == Direction.RIGHT) {
                pacman.setPosition(pacX + direction.getX(), pacY);
            }          
            
            System.out.println(pacman.getX());
            System.out.println(pacman.getY());
            
            System.out.println();

            
                        
            //Move ghosts in the direction that will best decrease their Manhattan distance to pacman
            for(int ghostNum = 0; ghostNum < ghosts.length; ghostNum++) {
                ghostX = ghosts[ghostNum].getX();
                ghostY = ghosts[ghostNum].getY();
                
                ghostDirection = ghostMove(ghosts[ghostNum]);
                
                if(ghostDirection == Direction.UP || ghostDirection == Direction.DOWN) {
                    ghosts[ghostNum].setPosition(ghostX, ghostY + ghostDirection.getY());
                }
                else if(ghostDirection == Direction.LEFT || ghostDirection == Direction.RIGHT) {
                    ghosts[ghostNum].setPosition(ghostX + ghostDirection.getX(), ghostY);
                }
            }
            
            //Adds to the score if pacman consumes a pac-dot
            if(isGameOver() != true) {
                if(visited[pacman.getX()][pacman.getY()] == false) {
                    score += 10;
                }
                setVisited(pacman.getX(), pacman.getY());
            }
            
            //Refreshes the grid after pacman and ghosts have moved
            refreshGrid();
        }
    }

    public boolean isGameOver() {
        //If pacman has the same row or column as any ghost, it is game over
        for(int ghostNum = 0; ghostNum < ghosts.length; ghostNum++) {
            if (pacman.getX() == ghosts[ghostNum].getX()
                    && pacman.getY() == ghosts[ghostNum].getY()) {
                return true;
            }
        }
        return false;
    }

    public Direction ghostMove(PacCharacter ghost) {
        int xDistance = ghost.getX() - pacman.getX();
        int yDistance = ghost.getY() - pacman.getY();

        if(xDistance == 0 && yDistance != 0) {
            if (yDistance < 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
        
        else if(yDistance == 0 && xDistance != 0) {
            if (xDistance < 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        }

        
        else if (Math.abs(xDistance) < Math.abs(yDistance)) {
            if (xDistance < 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } 
        
        else if (Math.abs(xDistance) > Math.abs(yDistance)) {
            if (yDistance < 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
            
        }
        
        else if (Math.abs(xDistance) == Math.abs(yDistance)) {
            if (xDistance < 0) {
                return Direction.RIGHT;
            } 
            else {
                return Direction.LEFT;
            }
        }
        // If the ghost is not above, below, left, or right of pacman, the
        // ghost must be in the same slot as pacman and will stay.
        else {
            return Direction.STAY;
        }
    }

    public String toString(){

        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", this.score));

        for(int y = 0; y < GRID_SIZE; y++) {
            for(int x = 0; x < GRID_SIZE; x++) {
                outputString.append("  " + grid[x][y] + "  ");
            }
            outputString.append("\n");
        }
        String gridString = outputString.toString();
        return gridString;

    }
    
    public void saveBoard(String saveBoardFile) {
        
    }




}
