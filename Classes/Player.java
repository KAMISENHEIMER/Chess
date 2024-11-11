package Classes;

import Classes.Pieces.Piece;
import Utility.Enums.Color;

import java.util.ArrayList;

/**
 * Player class, contains an array of all pieces of that player, and their color
 */
public class Player{
    private ArrayList<Piece> pieces;
    private Color color;
    private Piece king;

    /**
     * Player constructor
     * @param color     assigns color to this player
     */
    public Player(Color color) {
        this.color = color;
        pieces = new ArrayList<Piece>();
    }

    /**
     * gets value stored in color variable
     * @return      the color of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * gets the pieces array for updating
     * @return  the pieces array
     */
    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    /**
     * gets all possible pieces a player can make, if this is 0, then a player is in stalemate
     * @param board     the current state of the board
     * @return          the all the possible moves a player can make
     */
    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (Piece piece: pieces) {
            moves.addAll(piece.getMoves(board));
        }
        return moves;
    }

    /**
     * used in setting this players pointer to their king, useful for retrieving where the king is
     *
     * @param king      the king piece to be added to this player
     */
    public void setKing(Piece king) {
        this.king = king;
    }

    /**
     * retrieves where ever this players king is at on the board
     *
     * @return      this players king location
     */
    public Piece getKing() {
        return king;   //this shouldn't ever cause any issues because each player should always have a king
    }

    // this probably shouldn't be in player, it doesn't really have anything to do with the player

    /**
     * tries to make a move object based on a string
     * @param tryStr    the attempted move as a string, ex: "A1 B2"
     * @return          the move as a move object, or null if that is not correct notation
     */
    public Move makeMove(String tryStr){

        //castling
        if(tryStr.equals("O-O") || tryStr.equals("O-O-O")){
            return new Move(tryStr);
        }

        boolean checkFlag = tryStr.length() == 5 || tryStr.length() == 7;

        //2 locations (move)
        if(checkFlag){
            if(tryStr.charAt(0) < 'A' || tryStr.charAt(0) > 'H' || tryStr.charAt(3) < 'A' || tryStr.charAt(3) > 'H'){
                checkFlag = false;
            }
            if(tryStr.charAt(1) < '1' || tryStr.charAt(1) > '8' || tryStr.charAt(4) < '1' || tryStr.charAt(4) > '8'){
                checkFlag = false;
            }
            if(tryStr.charAt(2) != ' '){
                checkFlag = false;
            }

            //pawn promotion
            if(tryStr.length() == 7) {
                if(tryStr.charAt(5) != '='){
                    checkFlag = false;
                }
                if(tryStr.charAt(6) != 'Q' && tryStr.charAt(6) != 'N' && tryStr.charAt(6) != 'R' && tryStr.charAt(6) != 'B'){
                    checkFlag = false;
                }
            }

            if (checkFlag && tryStr.length() == 5) {
                return new Move(tryStr.substring(0,2),tryStr.substring(3,5));
            } else if (checkFlag && tryStr.length() == 7) {
                return new Move(tryStr.substring(0,2),tryStr.substring(3,5), tryStr.charAt(6));
            } else {
                return null;
            }
        } else{     //not length 5 or 7
            return null;
        }
    }
}