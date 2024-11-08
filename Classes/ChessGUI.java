package Classes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        boardPanel.setPreferredSize(new Dimension(400, 400));

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
                    JLabel text = new JLabel(piece.toString());
                    backgroundPanel.add(text);
                }

                int fixedI = i;
                int fixedJ = j;
                //turn each panel into a 'button'
                backgroundPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (selectedPiece == null) {
                            //select first piece
                            selectedPiece = board.pieceAt(fixedJ,fixedI);
                            selectedPanel = backgroundPanel;

                            System.out.println("PIECE SELECTED");   //TESTING
                        } else {
                            //move piece
                            //add text to the new spot
                            JLabel text = new JLabel(selectedPiece.toString()); //TODO make this an image
                            System.out.println(selectedPiece);  //TESTING
                            text.setVisible(true);
                            backgroundPanel.add(text);

                            //remove text from the previous spot
                            System.out.println(selectedPanel.getComponents()[0]);   //TESTING
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

    private int[] findPanelPosition(JPanel panel) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardGrid[i][j] == panel) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Not found
    }

}
