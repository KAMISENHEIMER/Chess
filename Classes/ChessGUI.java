package Classes;
import Classes.Pieces.King;
import Classes.Pieces.Pawn;
import Utility.Enums;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Chess GUI class, contains everything related to displaying moves on the intractable GUI board.
 */
public class ChessGUI {

    //board colors
    //Color lightColor = new Color(240, 217, 181);
    //Color darkColor = new Color(181, 136, 99);
    Color lightColor = new Color(238,238,210);
    Color darkColor = new Color(118,150,86);
    Color lightColorHighlight = new Color(250,250,110);
    Color darkColorHighlight = new Color(186,202,68);
    //TODO include other custom color schemes selectable in the color scheme

    //frame that holds window
    JFrame frame;

    //variable to hold whatever piece the player has clicked
    Piece selectedPiece;
    JLayeredPane selectedPanel;

    //2d array to hold references to all panels
    JLayeredPane[][] boardGrid;

    //arrayList to hold references to all potential move panels
    ArrayList<JLayeredPane> movePanels;

    //Chess game
    Game game;
    Board board;

    /**
     * Tries to load a specific image from a file, and puts it into a JLabel.
     *
     * @param fileName      the file path as a string to the desired image
     * @return              the JLabel which is scaled and contains the image
     */
    public JLabel LoadImage(String fileName){
        BufferedImage pieceImage;
        try {
            pieceImage = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image pieceImageScaled = pieceImage.getScaledInstance(64,64,Image.SCALE_DEFAULT);
        return new JLabel(new ImageIcon(pieceImageScaled));
    }

    /**
     * Returns the image corresponding to a piece
     * @param pieceStr      the piece's toString passed in
     * @return              whatever image that represents that piece, as a JLabel
     */
    public JLabel PieceToImage(String pieceStr){
        return switch (pieceStr) {
            case "wP" -> LoadImage("Assets/whitePawn.png");
            case "wR" -> LoadImage("Assets/whiteRook.png");
            case "wN" -> LoadImage("Assets/whiteKnight.png");
            case "wB" -> LoadImage("Assets/whiteBishop.png");
            case "wQ" -> LoadImage("Assets/whiteQueen.png");
            case "wK" -> LoadImage("Assets/whiteKing.png");
            case "bP" -> LoadImage("Assets/blackPawn.png");
            case "bR" -> LoadImage("Assets/blackRook.png");
            case "bN" -> LoadImage("Assets/blackKnight.png");
            case "bB" -> LoadImage("Assets/blackBishop.png");
            case "bQ" -> LoadImage("Assets/blackQueen.png");
            case "bK" -> LoadImage("Assets/blackKing.png");
            default -> LoadImage("Assets/smiley.png");
        };
    }

    /**
     * ChessGUI constructor, makes the window, the board, the mouse events on every square, and initializes all pieces on the board. Also starts a chess game.
     */
    public ChessGUI() {
        //window frame setup
        frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //panel that holds board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(600, 600));

        boardGrid = new JLayeredPane[8][8];

        movePanels = new ArrayList<>();

        //make a chess game
        game = new Game();
        board = game.getBoard();

        //loop through the entire board to create the background and pieces
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                //panel to hold background color
                JLayeredPane backgroundPanel = new JLayeredPane();
                backgroundPanel.setOpaque(true);
                //backgroundPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));    //border to show edges

                //alternate background colors
                backgroundPanel.setBackground((i + j) % 2 == 1?lightColor:darkColor);

                //if there is a piece, assign an image to that panel
                Piece piece = board.pieceAt(j,i);
                if (piece != null) {
                    JLabel picLabel = PieceToImage(piece.toString());
                    picLabel.setBounds(0,0,75,75);
                    backgroundPanel.add(picLabel);

                }

                int fixedI = i;
                int fixedJ = j;
                //turn each panel into a 'button'
                backgroundPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clickHandling(fixedI, fixedJ, backgroundPanel);
                    }
                });

                boardGrid[j][i] = backgroundPanel;
                boardPanel.add(backgroundPanel);
            }
        }

        frame.add(boardPanel);      //puts board inside window
        frame.pack();       //displays panel inside frame
        frame.setLocationRelativeTo(null);  //puts window in the middle of screen

        frame.setVisible(true);     //display the whole frame
    }

    /**
     * Handles selecting pieces, and calling to display possible moves, and then calling to move the piece whenever a piece is clicked.
     * @param fixedI            row index of the click event
     * @param fixedJ            column index of the click event
     * @param backgroundPanel   the panel that contains the piece that was clicked
     */
    public void clickHandling(int fixedI, int fixedJ, JLayeredPane backgroundPanel){

        boolean pieceExists = (board.pieceAt(fixedJ,fixedI) != null);
        boolean pieceCorrectColor = pieceExists && (board.pieceAt(fixedJ,fixedI).getColor() == game.getCurrentPlayer().getColor());

        Move move =new Move(new Location(0,0),new Location(0,0));
        boolean legalMove = false;
        if (selectedPiece != null) {
            move = new Move(selectedPiece.location, new Location(fixedJ, fixedI));
            move = correctMove(move);     //fixes move if it is a pawn promotion or castle
            legalMove = game.checkLegalMove(move);
        }
        boolean notSamePiece = (selectedPiece != board.pieceAt(fixedJ, fixedI));

        if (selectedPiece == null && pieceCorrectColor) {   //select the current piece, as long as one isn't yet selected, and the player is selecting the right colored piece.
            //select first piece
            selectedPiece = board.pieceAt(fixedJ,fixedI);
            selectedPanel = backgroundPanel;

            //highlight the piece
            backgroundPanel.setBackground(backgroundPanel.getBackground() == lightColor ? lightColorHighlight : darkColorHighlight);
            //display all moves the piece can make
            DisplayAvailableMoves(selectedPiece);

            System.out.println("PIECE SELECTED");   //TESTING
        } else if (selectedPiece != null && notSamePiece && legalMove) {   //a piece is already selected, and the piece isnt itself
            //all of this moves a piece

            //move the chess piece (and switch the current player)
            game.playGUI(move);

            //unhighlight selected panel
            selectedPanel.setBackground(selectedPanel.getBackground()==lightColorHighlight?lightColor:darkColor);

            //remove all possible move circles
            RemoveAvailableMoves();

            //remove image from the new spot if there is one
            if (backgroundPanel.getComponents().length >= 1) {
                backgroundPanel.remove(backgroundPanel.getComponents()[0]);
            }

            //add image to new spot
            JLabel picLabel = PieceToImage(board.pieceAt(fixedJ,fixedI).toString());   //have to get the piece at the board location because of pawns upgrading
            picLabel.setBounds(0,0,75,75);
            backgroundPanel.add(picLabel);

            //remove image from the previous spot
            selectedPanel.remove(selectedPanel.getComponents()[0]);

            //redraw the labels to accurately reflect these changes
            selectedPanel.revalidate();
            selectedPanel.repaint();
            backgroundPanel.revalidate();
            backgroundPanel.repaint();

            System.out.println(move.castleLeft);    //TESTING

            //repaint the rook's squares if the move was a castle
            int row = (game.getCurrentPlayer().getColor() == Enums.Color.White ? 7 : 0);
            if (move.castleLeft) {
                JLabel rookLabel = PieceToImage(board.pieceAt(3,row).toString());
                rookLabel.setBounds(0,0,75,75);
                boardGrid[3][row].add(rookLabel);
                boardGrid[3][row].revalidate();
                boardGrid[3][row].repaint();    //repaint rook's current square

                boardGrid[0][row].remove(boardGrid[0][row].getComponents()[0]);
                boardGrid[0][row].revalidate();
                boardGrid[0][row].repaint();    //repaint rook's first square
                System.out.println("repainted: 0 "+row);
            } else if (move.castleRight) {
                JLabel rookLabel = PieceToImage(board.pieceAt(5,row).toString());
                rookLabel.setBounds(0,0,75,75);
                boardGrid[5][row].add(rookLabel);
                boardGrid[5][row].revalidate();
                boardGrid[5][row].repaint();    //repaint rook's current square

                boardGrid[7][row].remove(boardGrid[7][row].getComponents()[0]);
                boardGrid[7][row].revalidate();
                boardGrid[7][row].repaint();    //repaint rook's first square
            }

            //empty both variables for future use;
            selectedPiece = null;
            selectedPanel = null;

            System.out.println("PIECE MOVED");   //TESTING
            CheckForKingCapture();
        } else {    //every other click event, remove possible moves and unhighlight panel
            if (selectedPanel != null) {
                selectedPanel.setBackground(selectedPanel.getBackground() == lightColorHighlight ? lightColor : darkColor);
            }
            RemoveAvailableMoves();

            selectedPiece = null;
            selectedPanel = null;
        }
    }

    /**
     * Checks both kings' locations to see if either is captured, and if so displays a message and ends the game.
     */
    public void CheckForKingCapture() {
        if (game.getPlayer(true).getKing() != game.getBoard().pieceAt(game.getPlayer(true).getKing().location)) {
            System.out.println("WHITE KING TAKEN, DISPLAY POPUP");
            JOptionPane.showMessageDialog(frame, "Game over, Black wins!");
            System.exit(0);
        }
        if (game.getPlayer(false).getKing() != game.getBoard().pieceAt(game.getPlayer(false).getKing().location)) {
            System.out.println("BLACK KING TAKEN, DISPLAY POPUP");
            JOptionPane.showMessageDialog(frame, "Game over, White wins!");
            System.exit(0);
        }

    }

    /**
     * Displays gray cirlces indicating all the legal moves a piece can make
     * @param piece     the piece to get all the moves from
     */
    public void DisplayAvailableMoves(Piece piece) {
        ArrayList<Move> moves = piece.getMoves(board);
        for (Move move : moves) {
            //create a circle for all your moves, solid if onto an empty square, outline if onto a piece
            int j;
            int i;
            if (move.castleLeft) {
                j = 2;
                i = (piece.color == Enums.Color.White)?0:7;
            } else if (move.castleRight) {
                j = 6;
                i = (piece.color == Enums.Color.White)?0:7;;
            } else {
                j = move.getTo().colIndex();
                i = move.getTo().rowIndex();
            }
            JLabel grayCircle;
            if (board.pieceAt(j,i) == null) {
                grayCircle = LoadImage("Assets/greyCircle.png");
            } else {
                grayCircle = LoadImage("Assets/greyCircleOutline.png");
            }
            grayCircle.setBounds(0,0,75,75);
            boardGrid[j][i].add(grayCircle);

            //redraw that square to reflect addition
            boardGrid[j][i].revalidate();
            boardGrid[j][i].repaint();

            //add to an array for easy deleting afterward
            movePanels.add(boardGrid[j][i]);
        }
    }

    /**
     * Loops through all previously displayed possible moves and removes them from the screen
     */
    public void RemoveAvailableMoves() {
        for (JLayeredPane panel : movePanels) {
            //delete the last component (an outline if there was a piece there, or just the circle)
            panel.remove(panel.getComponents()[panel.getComponents().length-1]);

            //redraw that square to reflect removal
            panel.revalidate();
            panel.repaint();

        }
        movePanels.clear();
    }

    /**
     * Takes a move input and changes it if it is a castle or pawn promotion, to properly reflect the Move system.
     * @param move  the inputted move (always from 1 spot to another)
     * @return      the move with proper castles/pawn promotions
     */
    public Move correctMove(Move move) {
        //alter move if necessary (castle or pawn promotion)
        if (game.getBoard().pieceAt(move.getFrom()) == game.getCurrentPlayer().getKing() && !((King)game.getCurrentPlayer().getKing()).hasMoved) {
            if (move.getTo().colIndex() == 2) {     //leftside castle
                move = new Move("O-O-O");
            } else if (move.getTo().colIndex() == 6) {  //rightside castle
                move = new Move("O-O");
            }
        } else if (game.getBoard().pieceAt(move.getFrom()).getClass() == Pawn.class) {
            if (move.getTo().rowIndex() == 0 && game.getCurrentPlayer().getColor() == Enums.Color.Black) {     //black pawn promotion
                move = new Move(move.getFrom(),move.getTo(),'Q');
            } else if (move.getTo().rowIndex() == 7 && game.getCurrentPlayer().getColor() == Enums.Color.White) {      //white pawn promotion
                move = new Move(move.getFrom(),move.getTo(),'Q');
            }   //TODO bring up a menu for pawn promotions
        }
        return move;
    }

}
