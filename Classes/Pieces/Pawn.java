package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums.*;

import java.util.ArrayList;

/**
 * Class for Pawn piece
 */
public class Pawn extends Piece {

    boolean hasMoved;

    /**
     * Constructs a new Pawn piece
     *
     * @param color Color of piece
     * @param location Location piece spawns in
     */
    public Pawn(Color color, Location location) {
        super(color, location);
    }

    /**
     * Gets a list of all possible moves
     *
     * @param board Reference to game board
     * @return moves ArrayList of all valid moves for piece
     */
    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();

        // 1 for white, -1 for black
        int colorMultiplier = this.getColor().equals(Color.White) ? 1 : -1;

        // Double jumping
        if (!this.hasMoved && board.pieceAt(new Location(this.location.colIndex(), this.location.rowIndex() + colorMultiplier)) == null){
            Location jumpLocation = new Location(this.location.colIndex(), this.location.rowIndex() + (2 * colorMultiplier));
            if(board.pieceAt(jumpLocation) == null){
                moves.add(new Move(this.location, jumpLocation));
            }
        }


        // is ahead out of bounds?
        // NOTE: This shouldn't run, as a pawn would have been promoted if it's in this position.
        boolean canMoveAhead = true;
        if(this.color == Color.White) { // for white, moving up rows
            if (this.location.rowIndex() + 1 >= 8) {
                canMoveAhead = false;
                return moves;
            }
        }
        else{ // for black, moving down rows
            if(this.location.rowIndex() - 1 <= -1) {
                canMoveAhead = false;
                return moves;
            }
        }


        // Check spot in front of it. If there's a piece then we can't move.
        Location ahead = new Location(this.location.colIndex(), this.location.rowIndex() + colorMultiplier);
        if(board.pieceAt(ahead) == null) {
            moves.add(new Move(this.location, ahead)); // path clear
        }

        // Check if enemy at diagonals:
        // Ahead and to the left
        Location checkLocation = new Location(this.location.colIndex() - colorMultiplier, this.location.rowIndex() + colorMultiplier);
        if((board.pieceAt(checkLocation) != null) && (board.pieceAt(checkLocation).getColor() != this.getColor())) {
            moves.add(new Move(this.location, checkLocation));
        }

        // Ahead and to the right
        checkLocation = new Location(this.location.colIndex() + colorMultiplier, this.location.rowIndex() + colorMultiplier);
        if(board.pieceAt(checkLocation) != null && board.pieceAt(checkLocation).getColor() != this.getColor()) {
            moves.add(new Move(this.location, checkLocation));
        }

        return moves;
    }

    /**
     * Represents piece as string, for displaying on board
     *
     * @return Pawn piece represented as string. Ex) wP for white pawn
     */
    @Override
    public String toString() {
        return (color.equals(Color.White)?"w":"b") + "P";
    }
}
