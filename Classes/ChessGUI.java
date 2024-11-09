package Classes;
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

public class ChessGUI {

    //board colors
    //Color lightColor = new Color(240, 217, 181);
    //Color darkColor = new Color(181, 136, 99);
    Color lightColor = new Color(238,238,210);
    Color darkColor = new Color(118,150,86);
    Color lightColorHighlight = new Color(250,250,110);
    Color darkColorHighlight = new Color(186,202,68);
    //TODO include other custom color schemes selectable in the color scheme

    //variable to hold whatever piece the player has clicked
    Piece selectedPiece;
    JLayeredPane selectedPanel;

    //2d array to hold references to all panels
    JLayeredPane[][] boardGrid;

    //arrayList to hold references to all potential move panels
    ArrayList<JLayeredPane> movePanels;


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

    public ChessGUI() {
        //frame that holds window
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //panel that holds board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(600, 600));

        boardGrid = new JLayeredPane[8][8];

        movePanels = new ArrayList<>();

        //make a chess game
        Game game = new Game();
        Board board = game.getBoard();

        //loop through the entire board to create the background and pieces
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                //panel to hold background color
                JLayeredPane backgroundPanel = new JLayeredPane();
                backgroundPanel.setOpaque(true);
                //backgroundPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));    //border to show edges

                //alternate background colors
                backgroundPanel.setBackground((i + j) % 2 == 1?lightColor:darkColor);

                //if there is a piece, assign text to that panel TODO make this an image
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
                        clickHandling(board, fixedI, fixedJ, backgroundPanel, game, frame, movePanels);
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

    public void clickHandling(Board board, int fixedI, int fixedJ, JLayeredPane backgroundPanel, Game game, JFrame frame, ArrayList<JLayeredPane> movePanels){

        boolean pieceExists = (board.pieceAt(fixedJ,fixedI) != null);
        boolean pieceCorrectColor = pieceExists && (board.pieceAt(fixedJ,fixedI).getColor() == game.getCurrentPlayer().getColor());

        Move move;
        boolean legalMove = false;
        if (selectedPiece != null) {
            move = new Move(selectedPiece.location, new Location(fixedJ, fixedI));
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
            DisplayAvailableMoves(selectedPiece, board, movePanels);

            System.out.println("PIECE SELECTED");   //TESTING
        } else if (selectedPiece != null && notSamePiece && legalMove) {   //a piece is already selected, and the piece isnt itself
            //move piece
            //JLabel text = new JLabel(selectedPiece.toString()); //TODO make this an image

            //unhighlight selected panel
            selectedPanel.setBackground(selectedPanel.getBackground()==lightColorHighlight?lightColor:darkColor);

            //remove all possible move circles
            RemoveAvailableMoves(movePanels);

            //remove image from the new spot if there is one
            if (backgroundPanel.getComponents().length >= 1) {
                backgroundPanel.remove(backgroundPanel.getComponents()[0]);
            }

            //add image to new spot
            JLabel picLabel = PieceToImage(selectedPiece.toString());
            picLabel.setBounds(0,0,75,75);
            backgroundPanel.add(picLabel);

            //remove image from the previous spot
            selectedPanel.remove(selectedPanel.getComponents()[0]);

            //redraw the labels to accurately reflect these changes
            selectedPanel.revalidate();
            selectedPanel.repaint();
            backgroundPanel.revalidate();
            backgroundPanel.repaint();

            //move the chess piece  TODO replace this with actual game logic
            board.getBoard()[selectedPiece.location.colIndex()][selectedPiece.location.rowIndex()] = null;
            board.getBoard()[fixedJ][fixedI] = selectedPiece;
            selectedPiece.move(new Location(fixedJ,fixedI));

            //empty both variables for future use;
            selectedPiece = null;
            selectedPanel = null;

            System.out.println("PIECE MOVED");   //TESTING
            CheckForKingCapture(game, frame);
        } else {    //every other click event, remove possible moves and unhighlight panel
            if (selectedPanel != null) {
                selectedPanel.setBackground(selectedPanel.getBackground() == lightColorHighlight ? lightColor : darkColor);
            }
            RemoveAvailableMoves(movePanels);

            selectedPiece = null;
            selectedPanel = null;
        }
    }

    public void CheckForKingCapture(Game game, JFrame frame) {
        if (game.getPlayer(true).getKing() != game.getBoard().pieceAt(game.getPlayer(true).getKing().location)) {
            System.out.println("WHITE KING TAKEN, DISPLAY POPUP");
            JOptionPane.showMessageDialog(frame, "Game over, Black wins!");
        }
        if (game.getPlayer(false).getKing() != game.getBoard().pieceAt(game.getPlayer(false).getKing().location)) {
            System.out.println("BLACK KING TAKEN, DISPLAY POPUP");
            JOptionPane.showMessageDialog(frame, "Game over, White wins!");
            System.exit(0);
        }

    }

    public void DisplayAvailableMoves(Piece piece, Board board, ArrayList<JLayeredPane> movePanels) {
        ArrayList<Move> moves = piece.getMoves(board);
        for (Move move : moves) {
            //create a circle for all your moves, solid if onto an empty square, outline if onto a piece

            int j = move.getTo().colIndex();
            int i = move.getTo().rowIndex();

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

            //TODO big circle if piece there
        }
    }

    public void RemoveAvailableMoves(ArrayList<JLayeredPane> movePanels) {
        for (JLayeredPane panel : movePanels) {
            //delete the last component (an outline if there was a piece there, or just the circle)
            panel.remove(panel.getComponents()[panel.getComponents().length-1]);

            //redraw that square to reflect removal
            panel.revalidate();
            panel.repaint();

            //TODO delete the second object if there is a piece there
        }
        movePanels.clear();
    }


}
