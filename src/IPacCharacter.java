/*
 * Interface for PacCharacter
 * 
 * Purpose: Intended for testing, and making sure you implement all method required.
 */

/* DO NOT CHANGE THIS FILE */
public interface IPacCharacter {
	
	/* Description for Each Method can be found in writeup */

    public int getRow();

    public int getCol();

    public char getAppearance();

    public void setPosition(int row, int col);

    public void setAppearance(char appearance);


}