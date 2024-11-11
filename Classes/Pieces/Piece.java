package Classes.Pieces;

import Classes.Board;
import Classes.Location;
import Classes.Move;
import Utility.Enums.*;

import java.util.ArrayList;

/**
 * Class representing Piece, extended by types of Pieces (Ex. Rook, Bishop)
 */
public abstract class Piece {

    protected Color color;
    protected Location location;

    /**
     * Constructor for new piece
     *
     * @param color Color of piece
     * @param location Location piece spawns in
     */
    public Piece(Color color, Location location) {
        this.color = color;
        this.location = location;
    }

    /**
     * Gets a list of all valid moves for piece
     *
     * @param board Current game board
     * @return ArrayList of valid moves
     */
    public abstract ArrayList<Move> getMoves(Board board);

    /**
     * Getter for piece color
     * @return color Piece's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for piece location
     * @return color Piece's location
     */
    public Location getLocation() { return location; }

    /**
     * Sets piece's location
     *
     * @param location New desired location for piece
     */
    public void move(Location location) {
        this.location = location;
    }

    /**
     * For use for pieces such as rook or bishop, which have valid moves in a straight direction
     * as long as there's not another piece, or the edge of the board.
     * If there is a piece, the movement is valid if it's the opposite color (taking piece), but no further.
     *
     * @param rowInc Number of rows moved, iterates until we can go no further
     * @param colInc Number of columns moved, iterates until we can go no further
     * @param board Reference to current game board
     * @param currLocation Piece's current location
     * @param color Piece's color
     * @return moves List of valid moves for piece
     */
    public ArrayList<Move> march(int rowInc, int colInc, Board board, Location currLocation, Color color){
        ArrayList<Move> moves = new ArrayList<Move>();

        int i = currLocation.rowIndex() + rowInc;
        int j = currLocation.colIndex() + colInc;
        while(i <= 7 && j <= 7 && i >= 0 && j >= 0){
            Move newMove = new Move(currLocation, new Location(j,i));
            if(!(board.pieceAt(j, i) == null)){ // if board has a piece at possible move
                if(board.pieceAt(j,i).getColor() == this.getColor()){
                    // same color, move isn't legal
                    break;
                } else {
                    moves.add(newMove);
                    break;
                    // enemy piece, can take, move is legal
                }
            } else{ // no piece at possible move
                moves.add(newMove);
                i += rowInc;
                j += colInc;
            }
        }

        return moves;
    }

    /**
     * To find valid moves for knight and king. Given a distance from piece, checks if the spot is valid to
     * move to.
     *
     * @param currLocation Piece's current location
     * @param colDir Number and direction of column movement
     * @param rowDir Number and direction of row movement
     * @param board Reference to game board
     * @return moves True if spot is a valid move, false if else
     */
    public boolean canMove(Location currLocation, int colDir, int rowDir, Board board){

        if(currLocation.colIndex() + colDir > 7 || currLocation.colIndex() + colDir < 0 ||
                currLocation.rowIndex() + rowDir > 7 || currLocation.rowIndex() + rowDir < 0){
            return false;
        }

        Location checkLocation = new Location(currLocation.colIndex() + colDir, currLocation.rowIndex() + rowDir);
        if(board.pieceAt(checkLocation) != null){
            if(board.pieceAt(checkLocation).getColor().equals(this.getColor())) { // if same color
                return false; // invalid move
            }
            else{ // if other color
                return true; // valid move (take piece)
            }
        }
        else {
            return true;
        }
    }
}
