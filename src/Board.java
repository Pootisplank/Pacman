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
            for (int row = 0; row < GRID_SIZE; row = row + (GRID_SIZE - 1)) {
                for (int col = 0; col < GRID_SIZE; col = col + (GRID_SIZE - 1)) {
                    ghosts[ghostCount] = new PacCharacter(row, col, 'G');
                    ghostCount++;
                }
            }
        }
        
        //Initialize grid
        grid = new char[GRID_SIZE][GRID_SIZE];
        
        //Initialize visited array
        visited = new boolean[GRID_SIZE][GRID_SIZE];
        //Sets all locations on the board as unvisited by Pac-Man at the start
        for(int row = 0; row < GRID_SIZE; row++) {
            for(int col = 0; col < GRID_SIZE; col++) {
                visited[row][col] = false;
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
        int rowNum = 0;
        int ghostNum = 0;

        while (file.hasNext()) {
            boardFileLine = file.nextLine();
            for (int i = 0; i < GRID_SIZE; i++) {
                
                if(boardFileLine.charAt(i) == '*') {
                    visited[i][rowNum] = false;
                }
                
                if(boardFileLine.charAt(i) == 'G') {
                    visited[i][rowNum] = false;
                    ghosts[ghostNum] = new PacCharacter(i, rowNum, 'G');
                    ghostNum++;
                }
                
                if(boardFileLine.charAt(i) == 'P') {
                    visited[i][rowNum] = true;
                    pacman = new PacCharacter(i, rowNum, 'P');
                }
                
                if(boardFileLine.charAt(i) == ' ') {
                    visited[i][rowNum] = true;
                }
                
            }
            rowNum++;
        }
        refreshGrid();
        file.close();
    }


    public void setVisited(int x, int y) {
        visited[x][y] = true;
    }

    public void refreshGrid() {
        //Assigns an empty slot to the grid if PacMan has visited it.  Else the slot has a Pac-Dot in it
        for(int row = 0; row < GRID_SIZE; row++) {
            for(int col = 0; col < GRID_SIZE; col++) {
                if(visited[row][col] == true) {
                    grid[row][col] = ' ';
                } else {
                    grid[row][col] = '*';
                }
            }
        }
        //Sets slots on the grid to 'G' based on current ghost locations
        for(int ghostNum = 0; ghostNum < ghosts.length; ghostNum++) {            
            grid[ghosts[ghostNum].getRow()][ghosts[ghostNum].getCol()] = ghosts[ghostNum].getAppearance();
        }
        //Sets pacman's location on the grid
        grid[pacman.getRow()][pacman.getCol()] = pacman.getAppearance();
        
        //Sets a slot to 'X' if there is a game over where pacman and a ghost are in the same slot
        if(isGameOver()) {
            grid[pacman.getRow()][pacman.getCol()] = 'X';
        }
    }

    public boolean canMove(Direction direction) {
        //If pacman is trying to move into the border of the board, it cannot move that direction
        if (direction == Direction.UP) {
            if (pacman.getRow() == 0) {
                return false;
            }
        }
        
        else if (direction == Direction.DOWN) {
            if (pacman.getRow() == GRID_SIZE - 1) {
                return false;
            }
        }

        else if (direction == Direction.LEFT) {
            if (pacman.getCol() == 0) {
                return false;
            }
        }

        else if (direction == direction.RIGHT) {
            if (pacman.getCol() == GRID_SIZE - 1) {
                return false;
            }
        }
        return true;
    }

    public void move(Direction direction) {
        //Only moves the characters if a valid direction is entered
        if(canMove(direction) == true) {
            //Store the position of pacman before he moves
            int pacRow = pacman.getRow();
            int pacCol = pacman.getCol();
            
            //Declare variables for the positions of ghosts before movement
            Direction ghostDirection;
            int ghostRow;
            int ghostCol;
            
            //Move pacman up or down depending on the user input
            if(direction == Direction.UP || direction == Direction.DOWN) {
                pacman.setPosition(pacRow + direction.getY(), pacCol);
            }
            //Move pacman left or right depending on the user input
            else if(direction == Direction.LEFT || direction == Direction.RIGHT) {
                pacman.setPosition(pacRow, pacCol + direction.getX());
            }          
                        
            //Move ghosts in the direction that will best decrease their Manhattan distance to pacman
            for(int ghostNum = 0; ghostNum < ghosts.length; ghostNum++) {
                ghostRow = ghosts[ghostNum].getRow();
                ghostCol = ghosts[ghostNum].getCol();
                
                ghostDirection = ghostMove(ghosts[ghostNum]);
                
                if(ghostDirection == Direction.UP || ghostDirection == Direction.DOWN) {
                    ghosts[ghostNum].setPosition(ghostRow + ghostDirection.getY(), ghostCol);
                }
                else if(ghostDirection == Direction.LEFT || ghostDirection == Direction.RIGHT) {
                    ghosts[ghostNum].setPosition(ghostRow, ghostCol + ghostDirection.getX());
                }
            }
            
            //Adds to the score if pacman consumes a pac-dot
            if(isGameOver() != true) {
                if(visited[pacman.getRow()][pacman.getCol()] == false) {
                    score += 10;
                }
                setVisited(pacman.getRow(), pacman.getCol());
            }
            
            //Refreshes the grid after pacman and ghosts have moved
            refreshGrid();
        }
    }

    public boolean isGameOver() {
        //If pacman has the same row or column as any ghost, it is game over
        for(int ghostNum = 0; ghostNum < ghosts.length; ghostNum++) {
            if (pacman.getRow() == ghosts[ghostNum].getRow()
                    && pacman.getCol() == ghosts[ghostNum].getCol()) {
                return true;
            }
        }
        return false;
    }

    public Direction ghostMove(PacCharacter ghost) {
        int colDistance = ghost.getCol() - pacman.getCol();
        int rowDistance = ghost.getRow() - pacman.getRow();

        if(colDistance == 0 && rowDistance != 0) {
            if (rowDistance < 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
        }
        
        else if(rowDistance == 0 && colDistance != 0) {
            if (colDistance < 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        }

        
        else if (Math.abs(colDistance) < Math.abs(rowDistance)) {
            if (colDistance < 0) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        } 
        
        else if (Math.abs(colDistance) > Math.abs(rowDistance)) {
            if (rowDistance < 0) {
                return Direction.DOWN;
            } else {
                return Direction.UP;
            }
            
        }
        
        else if (Math.abs(colDistance) == Math.abs(rowDistance)) {
            if (colDistance < 0) {
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

        for(int row = 0; row < GRID_SIZE; row++) {
            for(int col = 0; col < GRID_SIZE; col++) {
                outputString.append("  " + grid[row][col] + "  ");
            }
            outputString.append("\n");
        }
        String gridString = outputString.toString();
        return gridString;

    }




}
