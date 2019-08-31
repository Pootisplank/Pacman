/*
 * Interface for Board
 * 
 * Purpose: Intended for testing, and making sure you implement all method required.
 */

/* DO NOT CHANGE THIS FILE */
public interface IBoard {
	
	/* Description for Each Method can be found in writeup */

	public void setVisited(int x, int y);

    public void refreshGrid();

    public boolean canMove(Direction direction);

    public void move(Direction direction);

    public boolean isGameOver();

    public Direction ghostMove(PacCharacter ghost);

    public String toString();


}