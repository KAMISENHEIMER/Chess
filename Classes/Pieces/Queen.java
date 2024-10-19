package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(Enums.Color color, Location location) {
        super(color, location);
    }

    @Override
    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();

        // horizontal/vertical moves
        moves.addAll(march(1, 0, board, this.location, this.getColor()));
        moves.addAll(march(-1, 0, board, this.location, this.getColor()));
        moves.addAll(march(0, -1, board, this.location, this.getColor()));
        moves.addAll(march(0, 1, board, this.location, this.getColor()));
        // diagonal moves
        moves.addAll(this.march(1, -1, board, this.location, this.color));
        moves.addAll(this.march(1, 1, board, this.location, this.color));
        moves.addAll(this.march(-1, -1, board, this.location, this.color));
        moves.addAll(this.march(-1, 1, board, this.location, this.color));




        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "Q";
    }
}
