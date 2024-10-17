import Classes.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();

        Piece piece = board.pieceAt(4,3);
        ArrayList<Move> moves = piece.getMoves(board);

        System.out.println(moves);

        System.out.println(board);

        Game game = new Game();
        game.start();



    }
}
