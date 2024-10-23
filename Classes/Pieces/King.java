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
        hasMoved = false;
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
                    moves.add(new Move(this.location,new Location(this.location.colIndex()+j,this.location.rowIndex()+i)));
                }
            }
        }

        //check if can castle
        int row = (getColor() == Enums.Color.White ? 0 : 7);
        boolean clearPathToLeftRook = board.pieceAt(1,row)==null && board.pieceAt(2,row)==null && board.pieceAt(3,row)==null;
        if (!hasMoved && clearPathToLeftRook && board.pieceAt(0,row)!=null && board.pieceAt(0,row).getClass()==Rook.class) {
            Rook leftRook = (Rook) board.pieceAt(0, row);
            if (!leftRook.getHasMoved()) {
                moves.add(new Move("O-O-O"));
            }
        }
        boolean clearPathToRightRook = board.pieceAt(5,row)==null && board.pieceAt(6,row)==null;
        if (!hasMoved && clearPathToRightRook && board.pieceAt(7,row)!=null && board.pieceAt(7,row).getClass()==Rook.class) {
            Rook rightRook = (Rook) board.pieceAt(7, row);
            if (!rightRook.getHasMoved()) {
                moves.add(new Move("O-O"));
            }
        }

        return moves;
    }

    /**
     * Sets king's location, and declares it has moved
     *
     * @param location  New desired location for pawn
     */
    @Override
    public void move(Location location) {
        this.location = location;
        hasMoved = true;
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
