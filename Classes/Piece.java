package Classes;

import Utility.Enums.*;

import java.util.ArrayList;


public abstract class Piece {

    protected Color color;
    protected Location location;

    public Piece(Color color, Location location) {
        this.color = color;
        this.location = location;
    }

    public abstract ArrayList<Move> getMoves(Board board);

    public Color getColor() {
        return color;
    }

    public void move(Location location) {
        this.location = location;
    }

    public ArrayList<Move> march(int rowInc, int colInc, Board board, Location currLocation, Color color){
        ArrayList<Move> moves = new ArrayList<Move>();

        int i = currLocation.rowIndex() + rowInc;
        int j = currLocation.colIndex() + colInc;
        while(i <= 7 && j <= 7 && i >= 0 && j >= 0){
            Move newMove = new Move(currLocation, new Location(i,j));
            if(!(board.pieceAt(i, j) == null)){ // if board has a piece at possible move
                if(board.pieceAt(i,j).getColor() == this.getColor()){
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
