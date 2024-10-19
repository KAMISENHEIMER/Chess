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

        // valid moves to top left
        moves.addAll(this.march(1, -1, board, this.location, this.color));
        // valid moves to top right
        moves.addAll(this.march(1, 1, board, this.location, this.color));
        // valid moves to bottom left
        moves.addAll(this.march(-1, -1, board, this.location, this.color));
        // valid moves to bottom right
        moves.addAll(this.march(-1, 1, board, this.location, this.color));

        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "B";
    }
}
