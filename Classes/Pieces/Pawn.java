package Classes.Pieces;

import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums.*;

public class Pawn extends Piece {

    boolean hasMoved;

    public Pawn(Color color, Location location) {
        super(color, location);
    }

    @Override
    public Move[] getMoves() {
        return new Move[0];
    }

    @Override
    public String toString() {
        return (color.equals(Color.White)?"w":"b") + "P";
    }
}
