import Utility.Enums.*;


public abstract class Piece {

    Color color;
    Location location;

    public abstract String[] getMoves();
}
