package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Classes.Piece;
import Utility.Enums;

import java.util.ArrayList;

/**
 * Class for King piece
*/
public class King extends Piece {

    boolean hasMoved; // For castling

    /**
     * Constructs a new king piece
     * @param color of the piece
     * @param location the piece spawns in
     */
    public King(Enums.Color color, Location location) {
        super(color, location);
    }

    /**
     * Gets all possible moves for king piece
     * @param board reference to the game board
     * @return moves ArrayList of all valid moves for piece
     */
    @Override
    public ArrayList<Move> getMoves(Board board) {

        ArrayList<Move> moves = new ArrayList<Move>();

        // check all adjacent locations (don't worry about moving onto itself because of other checks in place)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; i <= 1; i++) {
                if (canMove(this.location,i,j,board)) {
                    moves.add(new Move(this.location,new Location(this.location.colIndex()+i,this.location.rowIndex()+j)));
                }
            }
        }

        //TODO add castling if both rook and king not moved and spaces between empty

        return moves;
    }

    /**
     * Represents piece as string, for displaying on board
     *
     * @return King piece represented as string. Ex) wK for white king
     */
    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "K";
    }
}
