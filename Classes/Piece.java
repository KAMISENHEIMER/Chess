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
            if(board.pieceAt(i, j)){ // if board has a piece at possible move
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

}
