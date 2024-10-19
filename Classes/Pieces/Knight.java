package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Enums.Color color, Location location) {
        super(color, location);
    }

    @Override
    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Location checkLocation = null;

        // check +1 col, +2 row
        if(canMove(this.location, 1, 2, board)){
            checkLocation = new Location(this.location.colIndex() + 1, this.location.rowIndex() + 2);
            moves.add(new Move(this.location, checkLocation));
        }
        // check +2 col, +1 row
        if(canMove(this.location, 2, 1, board)){
            checkLocation = new Location(this.location.colIndex() + 2, this.location.rowIndex() + 1);
            moves.add(new Move(this.location, checkLocation));
        }
        // check +2 col, -1 row
        if(canMove(this.location, 2, -1, board)){
            checkLocation = new Location(this.location.colIndex() + 2, this.location.rowIndex() - 1);
            moves.add(new Move(this.location, checkLocation));
        }
        // check +1 col, -2 row
        if(canMove(this.location, 1, -2, board)){
            checkLocation = new Location(this.location.colIndex() + 1, this.location.rowIndex() - 2);
            moves.add(new Move(this.location, checkLocation));
        }
        // check -1 col, -2 row
        if(canMove(this.location, -1, -2, board)){
            checkLocation = new Location(this.location.colIndex() - 1, this.location.rowIndex() - 2);
            moves.add(new Move(this.location, checkLocation));
        }
        // check -2 col, -1 row
        if(canMove(this.location, -2, -1, board)){
            checkLocation = new Location(this.location.colIndex() - 2, this.location.rowIndex() - 1);
            moves.add(new Move(this.location, checkLocation));
        }
        // check -2 col, +1 row
        if(canMove(this.location, -2, 1, board)){
            checkLocation = new Location(this.location.colIndex() - 2, this.location.rowIndex() + 1);
            moves.add(new Move(this.location, checkLocation));
        }
        // check -1 col, +2 row
        if(canMove(this.location, -1, 2, board)){
            checkLocation = new Location(this.location.colIndex() - 1, this.location.rowIndex() + 2);
            moves.add(new Move(this.location, checkLocation));
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "N";
    }
}
