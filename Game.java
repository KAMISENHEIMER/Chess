import Scanner.java;

public class Game{
    //class vars
    private Player currentPlayer;
    private boolean gameRunning;
    private Board board;
    private Player white;
    private Player black;
    //constructor
    public Game(){
        this.white = new Player(Color.White);
        this.black = new Player(Color.Black);
        this.currentPlayer = this.white;
        this.board = new Board();
        this.gameRunning = true;
    }
    //starting method
    public void start(){
        while(gameRunning){
            play();
        }
        end();
    }
    //game loop method
    public void play(){

        //Player input, filter for bad formatting
        System.out.println("Current player: " + currentPlayer.getColor());
        System.out.println("Please enter your move: ");
        Move move = getValidMove();

        //Game takes response, filters for illegal moves
        //first attempts non-castling moves
        if(!move.castleLeft && !move.castleRight){
            //checks if the move is a legal chess move.
            while(!checkLegalMove(move)) {
                move = getValidMove();
            }
            //perform the move
        }else{
            //castle
        }
    }
    //methods for checking move
    public Move getValidMove(){
        Scanner scnr = new Scanner(System.in);
        String moveAttempt = scnr.nextLine();
        Move retMove = makeMove(moveAttempt);
        while(retMove == null){
            System.out.println("Please enter a valid move:");
            moveAttempt = scnr.nextLine();
            retMove = makeMove(moveAttempt);
        }
        return retMove;
    }
    public boolean checkLegalMove(Move move){
        //checks if there is a piece to move. Loops otherwise
        if(!board.pieceAt(move.from)){
            return false;
        }
        // Loops otherwise
        boolean legalMove = false;
        legalMoves<Move> = getMoves();
        for(int i = 0; i < legalMoves.length; i++){
            legalMoves[i] = move ? (legalMove = true);
        }
        return legalMove;
    }

}