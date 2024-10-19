package Classes;

import Classes.Board;
import Classes.Move;
import Classes.Player;
import Utility.Enums.Color;
import java.util.ArrayList;


import java.util.Scanner;

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
        this.gameRunning = false;
    }
    //starting method
    public void start(){
        gameRunning = true;
        while(gameRunning){
            play();
        }
        //end();
    }
    //game loop method
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
    }
    //methods for checking move
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
    public boolean checkLegalMove(Move move){
        //checks if there is a piece to move. Loops otherwise
        if(board.pieceAt(move.getFrom())==null){
            return false;
        }

        return board.pieceAt(move.getFrom()).getMoves(board).contains(move);
    }

}
