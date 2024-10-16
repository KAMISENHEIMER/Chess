import Classes.Location;
import Classes.Piece;
import Classes.Pieces.*;
import Utility.Enums.Color;

public class Board{

    private Piece[][] board;

    public Board() {
        board = new Piece[][]{
                { new Rook(Color.White, new Location(0,0)), new Knight(Color.White, new Location(0,1)), new Bishop(Color.White, new Location(0,2)), new Queen(Color.White, new Location(0,3)), new King(Color.White, new Location(0,4)), new Bishop(Color.White, new Location(0,5)), new Knight(Color.White, new Location(0,6)), new Rook(Color.White, new Location(0,7)) },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null },
                { new Rook(Color.Black, new Location(7,0)), new Knight(Color.Black, new Location(7,1)), new Bishop(Color.Black, new Location(7,2)), new Queen(Color.Black, new Location(7,3)), new King(Color.Black, new Location(7,4)), new Bishop(Color.Black, new Location(7,5)), new Knight(Color.Black, new Location(7,6)), new Rook(Color.Black, new Location(7,7)) }
        };
        for(int i = 0; i < 8; i++){
            board[1][i] = new Pawn(Color.White, new Location(6,i));
        }
        for(int i = 0 ; i < 8; i++){
            board[6][i] = new Pawn(Color.Black, new Location(1,i));
        }
    }

    @Override
    public String toString(){
        String retStr = "  A  B  C  D  E  F  G  H\n";
        for(int i = 7; i >= 0; i--){
            retStr += i+1 + " ";
            for(int j = 0; j < 8; j++){
                if(board[i][j] == null) {
                    retStr += ((i + j) % 2 == 0) ? "##" : "  ";
                }else{
                    retStr += board[i][j];
                }
                retStr += " ";
            }
            retStr += "\n";
        }
        return retStr;
    }

}