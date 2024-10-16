public class Board{
    private Piece[][] board;
    @Override
    public String toString(){
        String retStr = "  A B C D E F G H\n";
        for(int i = 0; i < 8; i++){
            retStr += (8-i) + " ";
            for(int j = 0; j < 8; j++){
                if(board[i][j] == null) {
                    retStr += ((i + j) % 2 == 1) ? "##" : "  ";
                }else{
                    retStr += board[i][j];
                }
            }
            retStr += "\n";
        }
        return retStr;
    }
    public Board() {
        board = new Piece[8][8] = {
            new Rook(new Location(7,0), "Black"), new Knight(new Location(7,1), "Black"), new Bishop(new Location(7,2), "Black"), new Queen(new Location(7,3), "Black"), new King(new Location(7,4), "Black"), new Bishop(new Location(7,5), "Black"), new Knight(new Location(7,6), "Black"), new Rook(new Location(7,7), "Black"),
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            new Rook(new Location(0,0), "White"), new Knight(new Location(0,1), "White"), new Bishop(new Location(0,2), "White"), new Queen(new Location(0,3), "White"), new King(new Location(0,4), "White"), new Bishop(new Location(0,5), "White"), new Knight(new Location(0,6), "White"), new Rook(new Location(0,7), "White")
        }
        for(int i = 0; i < 8; i++){
            board[1][i] = new Pawn(new Location(6,i), "Black");
        }
        for(int i = 0 ; i < 8; i++){
            board[6][i] = new Pawn(new Location(1,i), "White");
        }
    }
}