package Classes;
import javax.swing.*;
import java.awt.*;

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

    public ChessGUI() {
        //frame that holds window
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //panel that holds board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(400, 400));

        //board colors
        //Color lightColor = new Color(240, 217, 181);
        //Color darkColor = new Color(181, 136, 99);
        Color lightColor = new Color(238,238,210);
        Color darkColor = new Color(118,150,86);
        //TODO include other custom color schemes selectable in the color scheme
        // potentially by using a class that contains all colors and piece icons, and is implemented by the piece.

        //loop through the entire board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //panel to hold background color
                JPanel backgroundPanel = new JPanel(/*new BorderLayout()*/);
                //backgroundPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));    //border to show edges

                //alternate background colors
                backgroundPanel.setBackground((i + j) % 2 == 0?lightColor:darkColor);

                boardPanel.add(backgroundPanel);

            }
        }


        frame.add(boardPanel);      //puts board inside window
        frame.pack();       //displays panel inside frame
        frame.setLocationRelativeTo(null);  //puts window in the middle of screen

        frame.setVisible(true);     //display the whole frame
    }



}
