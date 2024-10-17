import Classes.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();

        Piece piece = board.pieceAt(3,7);
        System.out.println(board);
        System.out.println(piece);


    }
}
