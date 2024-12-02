package Classes;

import Classes.Board;
import Classes.Move;
import Classes.Pieces.Piece;
import Classes.Player;
import Classes.Pieces.King;
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

        System.out.println(currentPlayer.getColor()+ " king has moved: "+((King)currentPlayer.getKing()).hasMoved);

        //switch color
        currentPlayer = currentPlayer.getColor()==Color.White?black:white;

        // TEMP: check for check
        //System.out.println(currentPlayer.getColor() + " in Check?: " + ((King) currentPlayer.getKing()).isInCheck(this));
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
            return currentPlayer.getKing().getSafeMoves(this).contains(move);
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
        return board.pieceAt(move.getFrom()).getSafeMoves(this).contains(move);
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
        moveHistory.remove(moveHistory.size()-1);
    }

    /**
     * gets all data related to the chess game, pieces, and moves
     * @return      the string made by all the data
     */
    public String getData() {

        /* --KEY--
        '!' (exclamation) - separates board, move, and taken pieces
        '@' (at)          - separates each piece on board, each move, and each piece in taken pieces array
        '#' (hashtag)     - separates each row on the board
        ' ' (space)       - separates piece info (type/color,position,hasMoved (if applicable)), and taken piece info
        '/' (slash)       - separates move info (toString,tookPiece,firstMove)
        */

        StringBuilder returnString = new StringBuilder();

        //board
        returnString.append(board.getData());
        returnString.append("!");

        //move history
        for (Move move: moveHistory) {
            returnString.append(move.getData());
            returnString.append("@");
        }
        returnString.append("!");

        //captured pieces
        for (Piece piece: board.getCapturedPieces()) {
            returnString.append(piece.getData());
            returnString.append("@");
        }

        return returnString.toString();
    }

    /**
     * load data constructor
     */
    public Game(String data) {
        String[] dataPieces = new String[]{"","",""};
        String[] dataPiecesChopped = data.split("!");
        if (dataPiecesChopped.length==2) {
            dataPieces[0] = dataPiecesChopped[0];
            dataPieces[1] = dataPiecesChopped[1];
        }
        if (dataPiecesChopped.length==1) {
            dataPieces[0] = dataPiecesChopped[0];
        }

        //board
        board = new Board(dataPieces[0],dataPieces[2]);

        //moves
        moveHistory = new ArrayList<>();
        String[] moveStrings = dataPieces[1].split("@");
        if (!moveStrings[0].isEmpty()) {
            for (int i = 0; i < moveStrings.length; i++) {
                String[] moveStringData = moveStrings[i].split("/");
                moveHistory.add(new Move(moveStringData[0], moveStringData[1], moveStringData[2]));
            }
        }

        this.white = new Player(Color.White);
        this.black = new Player(Color.Black);
        this.currentPlayer = moveHistory.size()%2==0?white:black;

        //search for king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.pieceAt(j,i) !=null && board.pieceAt(j,i).getClass().equals(King.class)) {
                    (board.pieceAt(j,i).getColor()==Color.White?white:black).setKing(board.pieceAt(j,i));
                }
            }
        }


    }

}
