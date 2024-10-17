package Classes;

import Classes.Move;
import Classes.Piece;
import Utility.Enums.Color;

import java.util.ArrayList;

public class Player{
    private ArrayList<Piece> pieces;
    private Color color;

    public Player(Color color) {
        this.color = color;
        pieces = new ArrayList<Piece>();
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (Piece piece: pieces) {
            moves.addAll(piece.getMoves(board));
        }
        return moves;
    }


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