package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums;

import java.util.ArrayList;

public class Rook extends Piece {

    boolean hasMoved;

    public Rook(Enums.Color color, Location location) {
        super(color, location);
    }

    @Override
    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "R";
    }
}
