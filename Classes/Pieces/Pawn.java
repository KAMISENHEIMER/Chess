package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums.*;

import java.util.ArrayList;

public class Pawn extends Piece {

    boolean hasMoved;

    public Pawn(Color color, Location location) {
        super(color, location);
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Color.White)?"w":"b") + "P";
    }
}
