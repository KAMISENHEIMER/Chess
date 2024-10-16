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
    public Board(){
        board = new Piece[8][8];
    }
}