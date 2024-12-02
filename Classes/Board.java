package Classes;

import Classes.Pieces.Piece;
import Classes.Pieces.*;
import Utility.Enums.Color;

import java.util.ArrayList;

/**
 * Board object, contains the 2D array that holds all the references to the pieces, and a list of captured pieces.
 */
public class Board{

    private Piece[][] board;

    private ArrayList<Piece> capturedPieces;

    /**
     * Board defualt constructor, initializes a chess board with all the pieces in the right spots, in (column, row) notation.
     */
    public Board() {

        board = new Piece[][]{      //board needs to be build sideways, to maintain col, row notation
            { new Rook(Color.White, new Location(0,0)), null, null, null, null, null, null, new Rook(Color.Black, new Location(0,7)) },
            { new Knight(Color.White, new Location(1,0)), null, null, null, null, null, null, new Knight(Color.Black, new Location(1,7)) },
            { new Bishop(Color.White, new Location(2,0)), null, null, null, null, null, null, new Bishop(Color.Black, new Location(2,7)) },
            { new Queen(Color.White, new Location(3,0)), null, null, null, null, null, null, new Queen(Color.Black, new Location(3,7)) },
            { new King(Color.White, new Location(4,0)), null, null, null, null, null, null, new King(Color.Black, new Location(4,7)) },
            { new Bishop(Color.White, new Location(5,0)), null, null, null, null, null, null, new Bishop(Color.Black, new Location(5,7)) },
            { new Knight(Color.White, new Location(6,0)), null, null, null, null, null, null, new Knight(Color.Black, new Location(6,7)) },
            { new Rook(Color.White, new Location(7,0)), null, null, null, null, null, null, new Rook(Color.Black, new Location(7,7)) }
        };

        for(int i = 0; i < 8; i++){
            board[i][1] = new Pawn(Color.White, new Location(i,1));
        }
        for(int i = 0 ; i < 8; i++){
            board[i][6] = new Pawn(Color.Black, new Location(i,6));
        }

        capturedPieces = new ArrayList<Piece>();
    }

    /**
     * returns the captured pieces array for editing
     */
    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    /**
     * returns the piece at a given board location. Ensures no out of bounds error.
     * @param col   the desired column
     * @param row   the desired row
     * @return      the piece at that location
     */
    public Piece pieceAt(int col, int row) {
        Piece returnPiece = null;
        if (col >= 0 && row >= 0 && col <= 7 && row <= 7) {
            returnPiece = board[col][row];
        }
        return returnPiece;
    }

    /**
     * returns the piece at a given board location. Ensures no out of bounds error.
     * @param location   the desired location
     * @return           the piece at that location
     */
    public Piece pieceAt(Location location) {
        Piece returnPiece = null;
        if (location.colIndex() >= 0 && location.rowIndex() >= 0 && location.colIndex() <= 7 && location.rowIndex() <= 7) {
            returnPiece = board[location.colIndex()][location.rowIndex()];
        }
        return returnPiece;
    }

    /**
     * View a visual representation of all the pieces on the chess bord
     * @return  returns all spots on the chess board separated by spaces, includes the key on the edges (A-H, 1-8)
     */
    @Override
    public String toString(){
        String retStr = "  A  B  C  D  E  F  G  H\n";
        for(int i = 7; i >= 0; i--){
            retStr += i+1 + " ";
            for(int j = 0; j < 8; j++){
                if(board[j][i] == null) {
                    retStr += ((i + j) % 2 == 0) ? "##" : "  ";
                }else{
                    retStr += board[j][i];
                }
                retStr += " ";
            }
            retStr += "\n";
        }
        return retStr;
    }

    /**
     * moves a desired piece based on what move is provided. Special cases for castling TODO and pawn promotion
     * @param move      the desired move the player would like to make
     * @param player    the player making the move
     */
    public void movePiece(Move move, Player player){
        if(move.castleLeft){
            int row = (player.getColor() == Color.White ? 0 : 7);
            //rook swaps to spot
            board[3][row] = board[0][row];
            //king swaps to spot
            board[2][row] = board[4][row];
            //delete rook and king from old squares
            board[0][row] = null;
            board[4][row] = null;
            //update pieces' stored location
            board[3][row].move(new Location(3,row));
            board[2][row].move(new Location(2,row));
        } else if(move.castleRight){
            int row = (player.getColor() == Color.White ? 0 : 7);
            //rook swaps to spot
            board[5][row] = board[7][row];
            board[5][row].move(new Location(5,row));
            //king swaps to spot
            board[6][row] = board[4][row];
            board[6][row].move(new Location(6,row));
            //delete rook and king from old squares
            board[7][row] = null;
            board[4][row] = null;
            //update pieces' stored location
            board[5][row].move(new Location(5,row));
            board[6][row].move(new Location(6,row));
        } else if (move.promoteTo != 0) {       //pawn promotion case
            Location from = move.getFrom();
            Location to = move.getTo();
            capturePiece(move, player);
            Piece pawn = board[from.colIndex()][from.rowIndex()];
            Piece promotedPiece;
            switch (move.promoteTo) {
                case 'Q':
                    promotedPiece = new Queen(pawn.getColor(), pawn.getLocation());
                    break;
                case 'N':
                    promotedPiece = new Knight(pawn.getColor(), pawn.getLocation());
                    break;
                case 'R':
                    promotedPiece = new Rook(pawn.getColor(), pawn.getLocation());
                    break;
                case 'B':
                    promotedPiece = new Bishop(pawn.getColor(), pawn.getLocation());
                    break;
                default:
                    //shouldn't ever happen, something went wrong, default to queen
                    promotedPiece = new Queen(pawn.getColor(), pawn.getLocation());
            }
            board[to.colIndex()][to.rowIndex()] = promotedPiece;
            board[to.colIndex()][to.rowIndex()].move(to);
            board[from.colIndex()][from.rowIndex()] = null;
        } else {    //otherwise, simply move piece
            Location from = move.getFrom();
            Location to = move.getTo();

            //if the piece moved a piece which hasn't been moved yet, alter the move
            Piece currentPiece = board[from.colIndex()][from.rowIndex()];
            if ((currentPiece.getClass()==Pawn.class && !((Pawn)currentPiece).hasMoved) || (currentPiece.getClass()==King.class && !((King)currentPiece).hasMoved) || (currentPiece.getClass()==Rook.class && !((Rook)currentPiece).getHasMoved())) {
                move.firstMove=true;
            }

            capturePiece(move, player);
            board[to.colIndex()][to.rowIndex()] = board[from.colIndex()][from.rowIndex()];
            board[to.colIndex()][to.rowIndex()].move(to);
            board[from.colIndex()][from.rowIndex()] = null;
        }
    }

    /**
     * helper function to check if there is a piece at a location and update the related piece lists
     * @param move      move of the piece capturing
     * @param player    the player capturing the piece
     */
    public void capturePiece(Move move, Player player) {
        Location to = move.getTo();
        if (board[to.colIndex()][to.rowIndex()] != null) {
            capturedPieces.add(board[to.colIndex()][to.rowIndex()]);
            //player.getPieces().remove(board[to.colIndex()][to.rowIndex()]);     //TODO should remove from the opposite players list

            //board[to.colIndex()][to.rowIndex()].getColor()==Color.White?
            //player.getPieces().remove(board[to.colIndex()][to.rowIndex()]);
            move.tookPiece = true;
        }
    }

    /**
     * returns the board, for altering it directly
     * @return      the board object which contains all pieces
     */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * does the opposite of a move, including pawn promotions and castles, with some careful logic for detecting first moves.
     * @param move              the move to be undone
     * @param currentPlayer     the player who made the move
     */
    public void undo(Move move, Player currentPlayer, Player otherPlayer) {
        if (move.castleLeft) {
            int row = (currentPlayer.getColor() == Color.White ? 0 : 7);
            //move king and rook to new squares
            board[0][row] = board[3][row];
            board[4][row] = board[2][row];
            //delete rook and king from old squares
            board[3][row] = null;
            board[2][row] = null;
            //update pieces' stored location
            board[0][row].unmove(new Location(0, row));
            board[4][row].unmove(new Location(4, row));
            ((Rook)(board[0][row])).undoCastle();
            ((King)(board[4][row])).undoCastle();
        } else if (move.castleRight) {
            int row = (currentPlayer.getColor() == Color.White ? 0 : 7);
            //move king and rook to new squares
            board[7][row] = board[5][row];
            board[4][row] = board[6][row];
            //delete rook and king from old squares
            board[5][row] = null;
            board[6][row] = null;
            //update pieces' stored location
            board[7][row].unmove(new Location(7, row));
            board[4][row].unmove(new Location(4, row));
            ((Rook)(board[7][row])).undoCastle();
            ((King)(board[4][row])).undoCastle();
        } else {
            Location from = move.getFrom();
            Location to = move.getTo();

            if (move.promoteTo!=0) {    //pawn promotion, switch the piece back into a pawn and continue
                board[to.colIndex()][to.rowIndex()] = new Pawn(currentPlayer.getColor(), to, true);
            }

            //normal move
            board[from.colIndex()][from.rowIndex()] = board[to.colIndex()][to.rowIndex()];
            board[from.colIndex()][from.rowIndex()].unmove(from);
            board[to.colIndex()][to.rowIndex()] = null;

            //check if the move captured another piece, and if so, put it back
            if (move.tookPiece && !capturedPieces.isEmpty() && capturedPieces.get(capturedPieces.size()-1).getLocation().toString().equals(to.toString())) {    //if it was, add it back to the game and remove it from the list
                board[to.colIndex()][to.rowIndex()] = capturedPieces.get(capturedPieces.size()-1);
                capturedPieces.remove(capturedPieces.size()-1);
            }

            //check if the move was the pieces' first move, and if so, fix their hasMoved boolean
            if (move.firstMove) {
                if (board[from.colIndex()][from.rowIndex()].getClass() == King.class) {
                    ((King)board[from.colIndex()][from.rowIndex()]).undoCastle();
                } else if (board[from.colIndex()][from.rowIndex()].getClass() == Rook.class) {
                    ((Rook)board[from.colIndex()][from.rowIndex()]).undoCastle();
                } else {
                    ((Pawn)board[from.colIndex()][from.rowIndex()]).hasMoved = false;
                }
            }
            
        }
    }

    /**
     * returns the chess board as a string (for saving and loading)
     * @return  the string made by all the pieces on the board
     */
    public String getData() {
        StringBuilder returnString = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieceAt(j,i) != null) {
                    returnString.append(pieceAt(j, i).getData());
                }
                returnString.append("@");
            }
            returnString.append("#");
        }

        return returnString.toString();
    }

    /**
     * load data constructor
     */
    public Board(String board, String capturedPieces) {
        this.board = new Piece[8][8];
        String[] line = board.split("#");
        for (int i = 0; i < 8; i++) {
            System.out.println(line[i]); //TESTING
            System.out.println("LINE"); //TESTING
            String[] pieces = line[i].split("@", -1);
            for (int j = 0; j < 8; j++) {
                String[] pieceData = pieces[j].split(" ");
                switch (pieceData[0]) {
                    case "P":
                        this.board[j][i] = new Pawn(pieceData[1],pieceData[2],pieceData[3],pieceData[4]);
                        break;
                    case "K":
                        this.board[j][i] = new King(pieceData[1],pieceData[2],pieceData[3],pieceData[4]);
                        break;
                    case "Q":
                        this.board[j][i] = new Queen(pieceData[1],pieceData[2],pieceData[3]);
                        break;
                    case "R":
                        this.board[j][i] = new Rook(pieceData[1],pieceData[2],pieceData[3],pieceData[4]);
                        break;
                    case "B":
                        this.board[j][i] = new Bishop(pieceData[1],pieceData[2],pieceData[3]);
                        break;
                    case "N":
                        this.board[j][i] = new Knight(pieceData[1],pieceData[2],pieceData[3]);
                        break;
                }
            }
        }

        //captured pieces
        this.capturedPieces = new ArrayList<Piece>();
        String[] capturedPiecesData = capturedPieces.split("@");
        for (int i = 0; i < capturedPiecesData.length; i++) {
            String[] pieceData = capturedPiecesData[i].split(" ");
            switch (pieceData[0]) {
                case "P":
                    this.capturedPieces.add(new Pawn(pieceData[1],pieceData[2],pieceData[3],pieceData[4]));
                    break;
                case "K":
                    this.capturedPieces.add(new King(pieceData[1],pieceData[2],pieceData[3],pieceData[4]));
                    break;
                case "Q":
                    this.capturedPieces.add(new Queen(pieceData[1],pieceData[2],pieceData[3]));
                    break;
                case "R":
                    this.capturedPieces.add(new Rook(pieceData[1],pieceData[2],pieceData[3],pieceData[4]));
                    break;
                case "B":
                    this.capturedPieces.add(new Bishop(pieceData[1],pieceData[2],pieceData[3]));
                    break;
                case "N":
                    this.capturedPieces.add(new Knight(pieceData[1],pieceData[2],pieceData[3]));
                    break;
            }
        }
    }

}