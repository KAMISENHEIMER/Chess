package Classes.Pieces;

import Classes.*;
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
     * @param game Current game
     * @return ArrayList of valid moves
     */
    public abstract ArrayList<Move> getMoves(Game game);

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
     * used in undoes, is never overwritten to update hasMoved booleans
     * @param location the location to move to
     */
    public void unmove(Location location) {
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
            return !board.pieceAt(checkLocation).getColor().equals(this.getColor()); // if not the same color
        }
        else {
            return true;
        }
    }

    public void checkMovesForChecks(ArrayList<Move> moves, Game game) {
        for (int i = 0; i < moves.size(); i++) {
            game.playGUI(moves.get(i));
            Player oppositePlayer = game.getCurrentPlayer().getColor()==Color.White?game.getPlayer(false):game.getPlayer(true);
            if (((King)oppositePlayer.getKing()).isInCheck(game)) {     //switch player
                moves.remove(moves.get(i));
                i--;
            }
            game.undo();
        }
    }

    /**
     * returns all available moves the piece can make without putting the king into check
     * @param game  the game the players are playing
     * @return      the updated move array with all the moves a piece can make without putting their king into check
     */
    public ArrayList<Move> getSafeMoves(Game game) {
        ArrayList<Move> moves = getMoves(game);
        checkMovesForChecks(moves, game);
        return moves;
    }

    /**
     * turns the piece into a data string
     * @return  the data string generated by this piece
     */
    public String getData() {
        StringBuilder returnString = new StringBuilder();
        returnString.append(toString().substring(1));
        returnString.append(" ");
        returnString.append(color.name().charAt(0));
        returnString.append(" ");
        returnString.append(location.colIndex());
        returnString.append(" ");
        returnString.append(location.rowIndex());

        return returnString.toString();
    }

    /**
     * load data constructor
     */
    public Piece(String color, String col, String row) {
        this.color = color.equals("W")?Color.White:Color.Black;
        this.location = new Location(Integer.parseInt(col),Integer.parseInt(row));
    }

}
