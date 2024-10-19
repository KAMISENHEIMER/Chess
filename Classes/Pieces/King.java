package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums;

import java.util.ArrayList;

public class King extends Piece {

    boolean hasMoved;

    public King(Enums.Color color, Location location) {
        super(color, location);
    }

    @Override
    public ArrayList<Move> getMoves(Board board) {

        ArrayList<Move> moves = new ArrayList<Move>();

        //check all adjacent locations (dont after worry about moving onto itself because of other checks in place)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; i <= 1; i++) {
                if (canMove(this.location,i,j,board)) {
                    moves.add(new Move(this.location,new Location(this.location.colIndex()+i,this.location.rowIndex()+j)));
                }
            }
        }

        //TODO add castling if both rook and king not moved and spaces between empty

        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "K";
    }
}
