package Classes;

import Classes.Location;
import Classes.Piece;
import Classes.Pieces.*;
import Utility.Enums.Color;

public class Board{

    private Piece[][] board;

    public Board() {

        board = new Piece[][]{
            { new Rook(Color.White, new Location(0,0)), null, null, null, null, null, null, new Rook(Color.Black, new Location(0,7)) },
            { new Knight(Color.White, new Location(1,0)), null, null, null, null, null, null, new Knight(Color.Black, new Location(1,7)) },
            { new Bishop(Color.White, new Location(2,0)), null, null, null, null, null, null, new Bishop(Color.Black, new Location(2,7)) },
            { new Queen(Color.White, new Location(3,0)), null, null, null, null, null, null, new Queen(Color.Black, new Location(3,7)) },
            { new King(Color.White, new Location(4,0)), null, null, null, null, null, null, new King(Color.Black, new Location(4,7)) },
            { new Bishop(Color.White, new Location(5,0)), null, null, null, null, null, null, new Bishop(Color.Black, new Location(5,7)) },
            { new Knight(Color.White, new Location(6,0)), null, null, null, null, null, null, new Knight(Color.Black, new Location(6,7)) },
            { new Rook(Color.White, new Location(7,0)), null, null, null, null, null, null, new Rook(Color.Black, new Location(7,7)) }
        };
        /*      old board in case i break everything
        board = new Piece[][]{          //board needs to be build sideways, to maintain col, row notation
                { new Rook(Color.White, new Location(0,0)), new Knight(Color.White, new Location(0,1)), new Bishop(Color.White, new Location(0,2)), new Queen(Color.White, new Location(0,3)), new King(Color.White, new Location(0,4)), new Bishop(Color.White, new Location(0,5)), new Knight(Color.White, new Location(0,6)), new Rook(Color.White, new Location(0,7)) },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { new Rook(Color.Black, new Location(7,0)), new Knight(Color.Black, new Location(7,1)), new Bishop(Color.Black, new Location(7,2)), new Queen(Color.Black, new Location(7,3)), new King(Color.Black, new Location(7,4)), new Bishop(Color.Black, new Location(7,5)), new Knight(Color.Black, new Location(7,6)), new Rook(Color.Black, new Location(7,7)) }
        };    */
        for(int i = 0; i < 8; i++){
            board[i][1] = new Pawn(Color.White, new Location(i,1));
        }
        for(int i = 0 ; i < 8; i++){
            board[i][6] = new Pawn(Color.Black, new Location(i,6));
        }
    }

    public Piece pieceAt(int col, int row) {
        Piece returnPiece = null;
        if (col >= 0 && row >= 0 && col <= 7 && row <= 7) {
            returnPiece = board[col][row];
        }
        return returnPiece;
    }

    public Piece pieceAt(Location location) {
        Piece returnPiece = null;
        if (location.colIndex() >= 0 && location.rowIndex() >= 0 && location.colIndex() <= 7 && location.rowIndex() <= 7) {
            returnPiece = board[location.colIndex()][location.rowIndex()];
        }
        return returnPiece;
    }

    @Override
    public String toString(){
        String retStr = "  A  B  C  D  E  F  G  H\n";
        for(int i = 7; i >= 0; i--){
            retStr += i+1 + " ";
            for(int j = 0; j < 8; j++){
                if(board[j][i] == null) {
                    retStr += ((i + j) % 2 == 0) ? "##" : "  ";
                }else{
                    retStr += board[j][i];
                }
                retStr += " ";
            }
            retStr += "\n";
        }
        return retStr;
    }
    //
    public void movePiece(Move move, Player player){
        if(move.castleLeft){
            int row = (player.getColor() == Color.White ? 0 : 7);
            //rook swaps to spot
            board[3][row] = board[0][row];
            //king swaps to spot
            board[2][row] = board[4][row];
            //delete rook and king from old squares
            board[0][row] = null;
            board[4][row] = null;
            //prevent error from continuing method
            return;
        }
        if(move.castleRight){
            int row = (player.getColor() == Color.White ? 0 : 7);
            //rook swaps to spot
            board[5][row] = board[7][row];
            //king swaps to spot
            board[6][row] = board[4][row];
            //delete rook and king from old squares
            board[7][row] = null;
            board[4][row] = null;
            //prevent error from continuing method
            return;
        }
        Location from = move.getFrom();
        Location to = move.getTo();
        //otherwise, simply move piece
        board[to.colIndex()][to.rowIndex()] = board[from.colIndex()][from.rowIndex()];
        board[from.colIndex()][from.rowIndex()] = null;
    }
}