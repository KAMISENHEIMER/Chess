package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Utility.Enums;

import java.util.ArrayList;

/**
 * Class for Bishop piece
 */
public class Bishop extends Piece {

    /**
     * Constructs a new Bishop piece
     *
     * @param color Color of piece
     * @param location Location piece spawns in
     */
    public Bishop(Enums.Color color, Location location) {
        super(color, location);
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

    /**
     * Represents piece as string, for displaying on board
     *
     * @return Bishop piece represented as string. Ex) wB for white bishop
     */
    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "B";
    }
}
