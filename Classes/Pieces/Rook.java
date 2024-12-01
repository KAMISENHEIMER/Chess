package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Utility.Enums;

import java.util.ArrayList;

/**
 * Class for Rook piece
 */
public class Rook extends Piece {

    private boolean hasMoved; // For castling

    /**
     * Constructs a new Queen piece
     *
     * @param color Color of piece
     * @param location Location piece spawns in
     */
    public Rook(Enums.Color color, Location location) {
        super(color, location);
        hasMoved = false;
    }

    /**
     * Gets a list of all possible moves
     *
     * @param board Reference to game board
     * @return moves ArrayList of all valid moves for piece
     */
    @Override
    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();

        // valid moves going "ahead"
        moves.addAll(march(1, 0, board, this.location, this.getColor()));
        // valid moves going "back"
        moves.addAll(march(-1, 0, board, this.location, this.getColor()));
        // valid moves going left
        moves.addAll(march(0, -1, board, this.location, this.getColor()));
        // valid moves going right
        moves.addAll(march(0, 1, board, this.location, this.getColor()));

        return moves;
    }

    /**
     * Sets rook's location, and declares it has moved
     *
     * @param location  New desired location for rook
     */
    @Override
    public void move(Location location) {
        this.location = location;
        hasMoved = true;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * sets has moved to false, used in Undos
     */
    public void undoCastle() {
        hasMoved = false;
    }

    /**
     * Represents piece as string, for displaying on board
     *
     * @return Rook piece represented as string. Ex) wR for white pawn
     */
    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "R";
    }
}
