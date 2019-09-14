public class PacCharacter {

    private char appearance;
    private int x;
    private int y;

    public PacCharacter(int x, int y, char appearance) {
        this.x = x;
        this.y = y;
        this.appearance = appearance;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getAppearance() {
        return appearance;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setAppearance(char appearance) {
        this.appearance = appearance;
    }
}
