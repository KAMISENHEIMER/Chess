package Classes;

//should only be passed values which correspond to a real move
public class Move {

    private Location from;
    private Location to;

    private char promoteTo = 0;     //only used for pawn promotion, char represents piece to upgrade to
    private boolean castleLeft;    //(long side)  only used for castling
    private boolean castleRight;   //(short side) only used for castling


    //constructor using 2 locations
    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }

    //constructor turning 2 strings into 2 constructors
    public Move(String from, String to) {
        this.from = new Location(from.charAt(0),from.charAt(1));
        this.to = new Location(to.charAt(0),to.charAt(1));
    }

    //pawn promotion
    public Move(Location from, Location to, char promoteTo) {
        this.from = from;
        this.to = to;
        this.promoteTo = promoteTo;
    }

    //castling
    public Move(String castle) {
        if (castle.equals("O-O")) {
            castleRight = true;
        } else if (castle.equals("O-O-O")) {
            castleLeft = true;
        }
    }

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
