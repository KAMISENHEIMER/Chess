package Classes;
import Classes.Pieces.King;
import Classes.Pieces.Pawn;
import Classes.Pieces.Piece;
import Utility.Enums;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

    //variables for sizing
    int windowSize = 512;       //try to keep to factors of 2, 512 is good
    int barSize = windowSize/16;
    int pieceScale = windowSize/8;
    int pieceArtScale = (int)(pieceScale*.88);
    int settingsMenuScale = (int)(windowSize*.7);
    int settingsMenuPosition = (windowSize/2)-(settingsMenuScale/2);

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
    //content layered pane for board and menus
    JLayeredPane content;
    //panel that holds board.
    JPanel boardPanel;
    //panel that holds settings menu.
    JPanel settingsMenu;

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
     * @param scale         how big to make the image after creating it
     * @return              the JLabel which is scaled and contains the image
     */
    public JLabel LoadImage(String fileName, int scale){
        BufferedImage pieceImage;
        try {
            pieceImage = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image pieceImageScaled = pieceImage.getScaledInstance(scale,scale,Image.SCALE_DEFAULT);
        return new JLabel(new ImageIcon(pieceImageScaled));
    }

    /**
     * Returns the image corresponding to a piece
     * @param pieceStr      the piece's toString passed in
     * @return              whatever image that represents that piece, as a JLabel
     */
    public JLabel PieceToImage(String pieceStr){
        return switch (pieceStr) {
            case "wP" -> LoadImage("Assets/whitePawn.png", pieceArtScale);
            case "wR" -> LoadImage("Assets/whiteRook.png", pieceArtScale);
            case "wN" -> LoadImage("Assets/whiteKnight.png", pieceArtScale);
            case "wB" -> LoadImage("Assets/whiteBishop.png", pieceArtScale);
            case "wQ" -> LoadImage("Assets/whiteQueen.png", pieceArtScale);
            case "wK" -> LoadImage("Assets/whiteKing.png", pieceArtScale);
            case "bP" -> LoadImage("Assets/blackPawn.png", pieceArtScale);
            case "bR" -> LoadImage("Assets/blackRook.png", pieceArtScale);
            case "bN" -> LoadImage("Assets/blackKnight.png", pieceArtScale);
            case "bB" -> LoadImage("Assets/blackBishop.png", pieceArtScale);
            case "bQ" -> LoadImage("Assets/blackQueen.png", pieceArtScale);
            case "bK" -> LoadImage("Assets/blackKing.png", pieceArtScale);
            default -> LoadImage("Assets/smiley.png", pieceArtScale);
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
        frame.setResizable(false);

        //layered pane to hold board and menus
        content = new JLayeredPane();
        content.setPreferredSize(new Dimension(windowSize, windowSize));

        //panel that holds board
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setPreferredSize(new Dimension(windowSize, windowSize));
        boardPanel.setBounds(0,0,windowSize,windowSize);

        //player bars (top and bottom)
        JPanel whiteBar = new JPanel();
        whiteBar.setPreferredSize(new Dimension(windowSize, barSize));
        whiteBar.setLayout(new BorderLayout());

        JPanel blackBar = new JPanel();
        blackBar.setPreferredSize(new Dimension(windowSize, barSize));
        blackBar.setLayout(new BorderLayout());

        //settings button
        JPanel settingsButton = new JPanel();
        settingsButton.setPreferredSize(new Dimension((int)(barSize*.88), (int)(barSize*.88)));
        settingsButton.setBackground(Color.lightGray);
        blackBar.add(settingsButton, BorderLayout.LINE_END);
        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displaySettingsMenu();
            }
        });
        JLabel settingsButtonIcon = LoadImage("Assets/settingsMenu.png", 32);
        settingsButtonIcon.setPreferredSize(new Dimension((int)(barSize*.75), (int)(barSize*.75)));
        settingsButton.add(settingsButtonIcon);

        //undo button
        JPanel undoButton = new JPanel();
        undoButton.setPreferredSize(new Dimension((int)(barSize*.88), (int)(barSize*.88)));
        undoButton.setBackground(Color.lightGray);
        whiteBar.add(undoButton, BorderLayout.LINE_END);
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                undo();
            }
        });
        JLabel undoButtonIcon = LoadImage("Assets/undoButton.png", 32);
        undoButtonIcon.setPreferredSize(new Dimension((int)(barSize*.75), (int)(barSize*.75)));
        undoButton.add(undoButtonIcon);

        //settings menu
        buildSettingsMenu();

        //lists that hold references to panels
        boardGrid = new JLayeredPane[8][8];
        movePanels = new ArrayList<>();

        //creates game, board, pieces, and click events
        NewGame();

        //put board and menu onto the content layered pane
        content.add(settingsMenu);      //puts the settings menu into board panel
        content.add(boardPanel);      //puts board inside content

        //loads all parts into frame
        frame.add(blackBar, BorderLayout.PAGE_START);
        frame.add(whiteBar, BorderLayout.PAGE_END);
        frame.add(content, BorderLayout.CENTER);      //puts content (board and menus) inside window
        frame.pack();       //displays panel inside frame
        frame.setLocationRelativeTo(null);  //puts window in the middle of screen

        frame.setVisible(true);     //display the whole frame
    }

    /**
     * Creates a new chess game, displays it onto the board, and initializes pieces and click events
     */
    public void NewGame() {

        //clear out old grid (if there is any)
        boardPanel.removeAll();

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
                    picLabel.setBounds(0,0,pieceScale,pieceScale);
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
            move = new Move(selectedPiece.getLocation(), new Location(fixedJ, fixedI));
            move = correctMove(move);     //fixes move if it is a pawn promotion or castle
            legalMove = game.checkLegalMove(move);      //makes sure the move is legal and not putting the player in check
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
            picLabel.setBounds(0,0,pieceScale,pieceScale);
            backgroundPanel.add(picLabel);

            //remove image from the previous spot
            selectedPanel.remove(selectedPanel.getComponents()[0]);

            //redraw the labels to accurately reflect these changes
            selectedPanel.revalidate();
            selectedPanel.repaint();
            backgroundPanel.revalidate();
            backgroundPanel.repaint();

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
        if (game.getPlayer(true).getKing() != game.getBoard().pieceAt(game.getPlayer(true).getKing().getLocation())) {
            JOptionPane.showMessageDialog(frame, "Game over, Black wins!");
            System.exit(0);
        }
        if (game.getPlayer(false).getKing() != game.getBoard().pieceAt(game.getPlayer(false).getKing().getLocation())) {
            JOptionPane.showMessageDialog(frame, "Game over, White wins!");
            System.exit(0);
        }

    }

    /**
     * Displays gray circles indicating all the legal moves a piece can make
     * @param piece     the piece to get all the moves from
     */
    public void DisplayAvailableMoves(Piece piece) {
        ArrayList<Move> moves = piece.getSafeMoves(game);
        for (Move move : moves) {
            //create a circle for all your moves, solid if onto an empty square, outline if onto a piece
            int j;
            int i;
            if (move.castleLeft) {
                j = 2;
                i = (piece.getColor() == Enums.Color.White)?0:7;
            } else if (move.castleRight) {
                j = 6;
                i = (piece.getColor() == Enums.Color.White)?0:7;
            } else {
                j = move.getTo().colIndex();
                i = move.getTo().rowIndex();
            }
            JLabel grayCircle;
            if (board.pieceAt(j,i) == null) {
                grayCircle = LoadImage("Assets/greyCircle.png", pieceArtScale);
            } else {
                grayCircle = LoadImage("Assets/greyCircleOutline.png", pieceArtScale);
            }
            grayCircle.setBounds(0,0,pieceScale,pieceScale);
            //grayCircle.setLocationRelativeTo(null);

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

    /**
     * toggles where the settings menu is displayed or not
     */
    public void displaySettingsMenu() {
        settingsMenu.setVisible(!settingsMenu.isVisible());
    }

    /**
     * builds all buttons that sit inside the settings menu
     */
    public void buildSettingsMenu() {
        settingsMenu = new JPanel();
        settingsMenu.setBackground(Color.lightGray);
        settingsMenu.setPreferredSize(new Dimension(settingsMenuScale, settingsMenuScale));
        settingsMenu.setBounds(settingsMenuPosition,settingsMenuPosition,settingsMenuScale,settingsMenuScale);
        settingsMenu.setVisible(false);
        settingsMenu.setLayout(new BoxLayout(settingsMenu,BoxLayout.PAGE_AXIS));
        settingsMenu.setBorder(BorderFactory.createEmptyBorder(20,20,50,20));

        JPanel newGameButton = new JPanel();
        JPanel saveGameButton = new JPanel();
        JPanel loadGameButton = new JPanel();

        JLabel newGameText = new JLabel("New Game");
        JLabel saveGameText = new JLabel("Save Game");
        JLabel loadGameText = new JLabel("Load Game");
        JTextField saveInput = new JTextField();

        newGameButton.add(newGameText);
        saveGameButton.add(saveGameText);
        loadGameButton.add(loadGameText);

        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NewGame();
                boardPanel.revalidate();    //redraw the new board
                boardPanel.repaint();
                displaySettingsMenu();      //get around the menu being hidden behind the new board
                displaySettingsMenu();
            }
        });
        saveGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveGame(saveInput);
            }
        });


        settingsMenu.add(newGameButton);
        settingsMenu.add(saveGameButton);
        settingsMenu.add(loadGameButton);
        settingsMenu.add(saveInput);

    }

    /**
     * calls a game undo, and updates the GUI
     */
    public void undo() {
        game.undo();
        //TODO find the specific pieces and undo them

        //delete possible move drawings to prevent issues;
        RemoveAvailableMoves();

        //redraws entire board, this should be changed later for something more specific to the undone move
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardGrid[j][i].getComponents().length >= 1) {    //remove every image, doing this because of dang piece taking in undos
                    boardGrid[j][i].remove(boardGrid[j][i].getComponents()[0]);
                    boardGrid[j][i].revalidate();
                    boardGrid[j][i].repaint();
                }
                /* if (boardGrid[j][i].getComponents().length >= 1 && board.pieceAt(j,i) == null) {    //remove image if there is no piece
                    boardGrid[j][i].remove(boardGrid[j][i].getComponents()[0]);
                    boardGrid[j][i].revalidate();
                    boardGrid[j][i].repaint();
                } else */ if (boardGrid[j][i].getComponents().length == 0 && board.pieceAt(j,i) != null) {     //add image if there is a piece
                    JLabel picLabel = PieceToImage(board.pieceAt(j,i).toString());   //have to get the piece at the board location because of pawns upgrading
                    picLabel.setBounds(0,0,pieceScale,pieceScale);
                    boardGrid[j][i].add(picLabel);
                }
            }
        }
    }

    /**
     * gets the game's data and pastes it into the text field
     * @param saveInput     the text field to paste the text into
     */
    public void saveGame(JTextField saveInput) {
        String data = game.getData();
        saveInput.setText(data);
    }

}
