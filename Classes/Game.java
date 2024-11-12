package Classes;

import Classes.Board;
import Classes.Move;
import Classes.Player;
import Utility.Enums.Color;
import java.util.ArrayList;


import java.util.Scanner;

/**
 * Game class, contains all variables related to the game (such as the players and the board), and all methods involving the chess game loop
 */
public class Game{
    //class vars
    private Player currentPlayer;
    private boolean gameRunning;
    private Board board;
    private Player white;
    private Player black;
    private ArrayList<Move> moveHistory;

    /**
     * Game constructor, initializes all variables, and adds all pieces to their respective player lists
     */
    public Game(){
        this.white = new Player(Color.White);
        this.black = new Player(Color.Black);
        this.currentPlayer = this.white;
        this.board = new Board();
        this.gameRunning = false;
        this.moveHistory = new ArrayList<>();

        //add all pieces to their respective players
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                white.getPieces().add(board.pieceAt(j,i));
            }
        }
        white.setKing(board.pieceAt(4,0));
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                black.getPieces().add(board.pieceAt(j,i));
            }
        }
        black.setKing(board.pieceAt(4,7));

    }
    //starting method

    /**
     * begins the game loop (based on console inputs)
     */
    public void start(){
        gameRunning = true;
        while(gameRunning){
            play();
        }
        //end();
    }
    //game loop method

    /**
     * represents one turn of the game, prints the board, instructions, and accepts a valid and legal input to move a piece to.
     */
    public void play(){

        System.out.println(board);

        //Player input, filter for bad formatting
        System.out.println("Current player: " + currentPlayer.getColor());
        System.out.println("Please enter your move: ");
        Move move = getValidMove();

        //Classes.Game takes response, filters for illegal moves
        while(!checkLegalMove(move)) {
            System.out.println("Please enter a legal move:");
            move = getValidMove();
        }
        //perform the move
        board.movePiece(move, currentPlayer);

        //switch color
        currentPlayer = currentPlayer.getColor()==Color.White?black:white;
    }

    /**
     * represents one turn of the game, move a piece to the given GUI location.
     * @param move      the move given by the GUI clicks, assume it is legal.
     */
    public void playGUI(Move move){

        //perform the move
        board.movePiece(move, currentPlayer);

        //add the move into the move history
        moveHistory.add(move);

        //switch color
        currentPlayer = currentPlayer.getColor()==Color.White?black:white;
    }

    //methods for checking move

    /**
     * Repeatedly asks for a valid move until it gets one
     * @return      the valid move
     */
    public Move getValidMove(){
        Scanner scnr = new Scanner(System.in);
        String moveAttempt = scnr.nextLine();
        Move retMove = currentPlayer.makeMove(moveAttempt);
        while(retMove == null){
            System.out.println("Please enter a valid move:");
            moveAttempt = scnr.nextLine();
            retMove = currentPlayer.makeMove(moveAttempt);
        }
        return retMove;
    }

    /**
     * Repeatedly asks for a move that actually exists as a possible move
     * @param move      the move to check
     * @return
     */
    public boolean checkLegalMove(Move move){
        //checks for castling
        if ((move.castleLeft || move.castleRight)) {
            return currentPlayer.getKing().getMoves(board).contains(move);
        }
        //checks if there is a piece to move
        if(board.pieceAt(move.getFrom())==null){
            return false;
        }
        //ensures the piece is the same color as the current player
        if(board.pieceAt(move.getFrom()).getColor()!=currentPlayer.getColor()){
            return false;
        }
        //returns whether that move is in the moves list of that piece
        return board.pieceAt(move.getFrom()).getMoves(board).contains(move);
    }

    /**
     * retrieves the board object
     * @return  the board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * retrieves a references to whoever the current player is
     * @return  the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * retrieves one of the players in the game
     * @param getWhite      true if you want to get white, false if you want to get black
     * @return              a reference to the player that was chosen
     */
    public Player getPlayer(boolean getWhite) {
        return getWhite?white:black;
    }

    /**
     * undoes a move, switches the color and adjusts the history
     */
    public void undo() {
        if (moveHistory.isEmpty()) {    //tried undoing the first move
            return;
        }

        //switch color
        currentPlayer = currentPlayer.getColor()==Color.White?black:white;

        //undo the last move on the board
        Player otherPlayer = currentPlayer.getColor()==Color.White?black:white;
        board.undo(moveHistory.get(moveHistory.size()-1), currentPlayer, otherPlayer);

        //remove the move from the history
        moveHistory.remove(moveHistory.get(moveHistory.size()-1));
    }
}
