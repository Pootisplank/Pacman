public class GamePacman {

    //Default Size
    private static final int DEFAULT_SIZE = 10;
    
    //boardSize of the game
    private static int boardSize;
    
    //The board object the user will play on 
    private static Board board;


    public static void main(String[] args) {

        processArgs(args);
        
        System.out.println("Welcome to Pac-Man!");
        
        board = new Board( boardSize );
        System.out.println(board);
        

    }

    private static void processArgs(String[] args) {
        
        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }
        
        if(!"-s".equals(args[0])) {
            printUsage();
            System.exit(-1);
        }
        
        // Set the board size to the input value, but if input value is less
        // than 3, set board size to default value of 10
        boardSize = Integer.parseInt(args[args.length - 1]);
        if(boardSize < 3) {
            boardSize = DEFAULT_SIZE;
        }
        
    }

    // Print the Usage Message
    private static void printUsage()
    {
        System.out.println("Pac-Man");
        System.out.println("Usage:  GamePacman [-s size]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
        System.out.println();
        System.out.println("  -s [size]  -> Specifies the size of the Pac-Man board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i are used, then the size of the board");
        System.out.println("                will be determined by the input file. If board size declared is less");
        System.out.println("                than 3, the board size will be modified to the default size. The default");
        System.out.println("                size is 10.");

    }
}
