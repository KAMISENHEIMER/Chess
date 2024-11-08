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

public class ChessGUI {

    // unicode chess pieces
    String bK = "\u2654"; //♔
    String bQ = "\u2655"; //♕
    String bR = "\u2656"; //♖
    String bB = "\u2657"; //♗
    String bN = "\u2658"; //♘
    String bP = "\u2659"; //♙
    String wK = "\u265A"; //♚
    String wQ = "\u265B"; //♛
    String wR = "\u265C"; //♜
    String wB = "\u265D"; //♝
    String wN = "\u265E"; //♞
    String wP = "\u265F"; //♟

    JLabel whitePawn = LoadImage("Assets/whitePawn.png");
    JLabel whiteRook = LoadImage("Assets/whiteRook.png");
    JLabel whiteBishop;
    JLabel whiteKnight;
    JLabel whiteQueen;
    JLabel whiteKing;
    JLabel blackPawn = LoadImage("Assets/blackPawn.png");
    JLabel blackRook = LoadImage("Assets/blackRook.png");;
    JLabel blackBishop;
    JLabel blackKnight;
    JLabel blackQueen;
    JLabel blackKing;

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
        switch (pieceStr){
            case "wP":
                return LoadImage("Assets/whitePawn.png");
            case "wR":
                return LoadImage("Assets/whiteRook.png");
            case "wN":
                break;
            case "wB":
                break;
            case "wQ":
                break;
            case "wK":
                break;
            case "bP":
                return LoadImage("Assets/blackPawn.png");
            case "bR":
                return LoadImage("Assets/blackRook.png");
            case "bN":
                break;
            case "bB":
                break;
            case "bQ":
                break;
            case "bK":
                break;
        }
        return LoadImage("Assets/smiley.png");
    }


    //board colors
    //Color lightColor = new Color(240, 217, 181);
    //Color darkColor = new Color(181, 136, 99);
    Color lightColor = new Color(238,238,210);
    Color darkColor = new Color(118,150,86);
    //TODO include other custom color schemes selectable in the color scheme
    // potentially by using a class that contains all colors and piece icons, and is implemented by the piece.


    //variable to hold whatever piece the player has clicked
    Piece selectedPiece;
    JPanel selectedPanel;

    //2d array to hold all Panels
    JPanel[][] boardGrid;



    public ChessGUI() {
        //frame that holds window
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //panel that holds board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(600, 600));

        boardGrid = new JPanel[8][8];

        //make a chess game
        Game game = new Game();
        Board board = game.getBoard();

        //loop through the entire board to create the background and pieces
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                //panel to hold background color
                JPanel backgroundPanel = new JPanel(/*new BorderLayout()*/);
                //backgroundPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));    //border to show edges

                //alternate background colors
                backgroundPanel.setBackground((i + j) % 2 == 1?lightColor:darkColor);

                //if there is a piece, assign text to that panel TODO make this an image
                Piece piece = board.pieceAt(j,i);
                if (piece != null) {
                    JLabel picLabel = PieceToImage(piece.toString());
                    backgroundPanel.add(picLabel);
                }

                int fixedI = i;
                int fixedJ = j;
                //turn each panel into a 'button'
                backgroundPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clickHandling(board, fixedI, fixedJ, backgroundPanel);
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

    public void clickHandling(Board board, int fixedI, int fixedJ, JPanel backgroundPanel){
        if (selectedPiece == null) {
            //select first piece
            selectedPiece = board.pieceAt(fixedJ,fixedI);
            selectedPanel = backgroundPanel;

            System.out.println("PIECE SELECTED");   //TESTING
        } else {
            //move piece
            //JLabel text = new JLabel(selectedPiece.toString()); //TODO make this an image

            //remove image from the current spot if there is one
            if (backgroundPanel.getComponents().length >= 1) {
                backgroundPanel.remove(backgroundPanel.getComponents()[0]);
            }

            //add image to new spot
            JLabel picLabel = PieceToImage(selectedPiece.toString());
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
        }
    }

}
