package Classes.Pieces;

import Classes.Board;
import Classes.Game;
import Classes.Location;
import Classes.Move;
import Utility.Enums;

import java.util.ArrayList;

/**
 * Class for Bishop piece
 */
public class Knight extends Piece {

    /**
     * Constructs a new Knight piece
     *
     * @param color Color of piece
     * @param location Location piece spawns in
     */
    public Knight(Enums.Color color, Location location) {
        super(color, location);
    }

    /**
     * Gets a list of all possible moves
     *
     * @param board Reference to game board
     * @return moves ArrayList of all valid moves for piece
     */
    @Override
    public ArrayList<Move> getMoves(Game game) {
        Board board = game.getBoard();

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

    /**
     * Represents piece as string, for displaying on board
     *
     * @return Knight piece represented as string. Ex) wN for white knight
     */
    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "N";
    }
}
