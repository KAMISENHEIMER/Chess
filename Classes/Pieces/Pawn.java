package Classes.Pieces;

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

    @Override
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Color.White)?"w":"b") + "P";
    }
}
