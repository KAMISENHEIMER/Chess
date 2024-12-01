package Classes.Pieces;

import Classes.Board;
import Classes.Game;
import Classes.Location;
import Classes.Move;
import Utility.Enums;

import java.util.ArrayList;

/**
 * Class for Pawn piece
 */
public class Queen extends Piece {

    /**
     * Constructs a new Queen piece
     *
     * @param color Color of piece
     * @param location Location piece spawns in
     */
    public Queen(Enums.Color color, Location location) {
        super(color, location);
    }

    /**
     * Gets a list of all possible moves
     *
     * @param game Reference to game
     * @return moves ArrayList of all valid moves for piece
     */
    @Override
    public ArrayList<Move> getMoves(Game game) {
        Board board = game.getBoard();

        ArrayList<Move> moves = new ArrayList<Move>();

        // horizontal/vertical moves
        moves.addAll(march(1, 0, board, this.location, this.getColor()));
        moves.addAll(march(-1, 0, board, this.location, this.getColor()));
        moves.addAll(march(0, -1, board, this.location, this.getColor()));
        moves.addAll(march(0, 1, board, this.location, this.getColor()));
        // diagonal moves
        moves.addAll(this.march(1, -1, board, this.location, this.color));
        moves.addAll(this.march(1, 1, board, this.location, this.color));
        moves.addAll(this.march(-1, -1, board, this.location, this.color));
        moves.addAll(this.march(-1, 1, board, this.location, this.color));




        return moves;
    }

    /**
     * Represents piece as string, for displaying on board
     *
     * @return Queen piece represented as string. Ex) wQ for white pawn
     */
    @Override
    public String toString() {
        return (color.equals(Enums.Color.White)?"w":"b") + "Q";
    }
}
