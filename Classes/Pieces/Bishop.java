package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(Enums.Color color, Location location) {
        super(color, location);
    }

    @Override
    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Location currLocation = this.location;

        // valid moves to top left
        moves.addAll(this.march(1, -1, board, currLocation, this.color));


        /*
        int i = currLocation.rowIndex() + 1;
        int j = currLocation.colIndex() - 1;
        while(i <= 7 && j >= 0){
            Move newMove = new Move(currLocation, new Location(i,j));
            moves.add(newMove);
            i++;
            j--;
        }
         */


        // valid moves to top right
        moves.addAll(this.march(1, 1, board, currLocation, this.color));
        /*
        i = currLocation.rowIndex() + 1;
        j = currLocation.colIndex() + 1;
        while(i <= 7 && j <= 7){
            Move newMove = new Move(currLocation, new Location(i,j));
            if(board.pieceAt(i, j)){ // if board has a piece at possible move
                if(board.pieceAt(i,j).getColor() == this.getColor()){
                    // same color, move isn't legal
                    break;
                } else {
                    moves.add(newMove);
                    break;
                    // enemy piece, can take, move is legal
                }
            } else{ // no piece at possible move
                moves.add(newMove);
                i++;
                j++;
            }
        }
        */

        // valid moves to bottom left
        moves.addAll(this.march(-1, -1, board, currLocation, this.color));
        /*
        i = currLocation.rowIndex() - 1;
        j = currLocation.colIndex() - 1;
        while(i >= 0 && j >= 0){
            Move newMove = new Move(currLocation, new Location(i,j));
            moves.add(newMove);
            i--;
            j--;
        }
         */

        // valid moves to bottom right
        moves.addAll(this.march(-1, 1, board, currLocation, this.color));
        /*
        i = currLocation.rowIndex() - 1;
        j = currLocation.colIndex() + 1;
        while(i >= 0 && j <= 7){
            Move newMove = new Move(currLocation, new Location(i,j));
            moves.add(newMove);
            i--;
            j++;
        }
         */

        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "B";
    }
}
