package Classes;

/**
 * Class representing a potential move
 */
public class Move {

    private Location from;
    private Location to;

    //TODO these should really be private and have get methods
    public char promoteTo = 0;     //only used for pawn promotion, char represents piece to upgrade to
    public boolean castleLeft;    //(long side)  only used for castling
    public boolean castleRight;   //(short side) only used for castling

    public boolean tookPiece = false;   //if this move captured another piece, used in undoes
    public boolean firstMove = false;   //if this move was the pieces first move, used in undoes


    /**
     * Constructs a new move given a location from and to
     * @param from Location piece is coming from
     * @param to Location piece is moving to
     */
    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs a new move given a string representing the location from and to
     * @param from Location piece is coming from
     * @param to Location piece is moving to
     */
    public Move(String from, String to) {
        this.from = new Location(from);
        this.to = new Location(to);
    }

    /**
     * Constructs a new move given a location from and to, for use in promoting a pawn
     * @param from Location piece is coming from
     * @param to Location piece is moving to
     * @param promoteTo Char representing the piece we wish to promote pawn to Ex) Q for Queen
     */
    public Move(Location from, Location to, char promoteTo) {
        this.from = from;
        this.to = to;
        this.promoteTo = promoteTo;
    }

    /**
     * Constructs a new move given a string representing the location from and to, for use in promoting a pawn
     * @param from Location piece is coming from
     * @param to Location piece is moving to
     * @param promoteTo Char representing the piece we wish to promote pawn to Ex) Q for Queen
     */
    public Move(String from, String to, char promoteTo) {
        this.from = new Location(from);
        this.to = new Location(to);
        this.promoteTo = promoteTo;
    }

    /**
     * Given a string of text representing a castle input, determines which castle to performs
     * @param castle String representing a castle input
     */
    public Move(String castle) {
        if (castle.equals("O-O")) {
            castleRight = true;
        } else if (castle.equals("O-O-O")) {
            castleLeft = true;
        }
    }

    /**
     * Gets the location a piece is moving from
     * @return from The location a piece is moving from
     */
    public Location getFrom() {
        return from;
    }
    /**
     * Gets the location a piece is moving to
     * @return to The location a piece is moving to
     */
    public Location getTo() {
        return to;
    }

    /**
     * For comparing two moves by turning them into strings and comparing, for finding equivalency
     * @param o Move we want to compare to given move
     * @return True if moves' strings match, false if else
     */
    @Override
    public boolean equals(Object o)
    {
        return o.toString().equals(this.toString());
    }

    /**
     * Converts a move into a string
     * @return String representing a given move Ex) "E4 E5"
     */
    @Override
    public String toString() {
        if (castleRight) {
            return "O-O";
        } else if (castleLeft) {
            return "O-O-O";
        } else if (promoteTo!=0) {
            return from+" "+to+"="+promoteTo;
        } else {
            return from+" "+to;
        }

    }
}
