package Classes;

import Utility.Enums.*;


public abstract class Piece {

    protected Color color;
    protected Location location;

    public Piece(Color color, Location location) {
        this.color = color;
        this.location = location;
    }

    public abstract Move[] getMoves();

    public void move(Location location) {
        this.location = location;
    }

}
