
public class test {
    public static void main(String[] args) {
        Board test = new Board(7);
        System.out.println(test.toString());

        test.move(Direction.UP);
        System.out.println(test.toString());
        
        test.move(Direction.DOWN);
        System.out.println(test.toString());

        test.move(Direction.LEFT);
        System.out.println(test.toString());
        
        test.move(Direction.DOWN);
        System.out.println(test.toString());
    }
    

}
