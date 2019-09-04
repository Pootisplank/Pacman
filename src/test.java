import java.io.*;
import java.util.*;
public class test {
    public static void main(String[] args) throws IOException {
        
        Board gameBoard = new Board(9);
        System.out.println(gameBoard.toString());
        gameBoard.move(Direction.UP);
        System.out.println(gameBoard.toString());
        gameBoard.move(Direction.LEFT);
        System.out.println(gameBoard.toString());
        gameBoard.move(Direction.RIGHT);
        System.out.println(gameBoard.toString());
        gameBoard.move(Direction.RIGHT);
        System.out.println(gameBoard.toString());
        gameBoard.move(Direction.RIGHT);
        System.out.println(gameBoard.toString());
    }
    

}
