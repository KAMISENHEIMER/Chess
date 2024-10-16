package Classes;

public class Move {
    private Location from;
    private Location to;

    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
    }
    public Move(String from, String to) {
        this.from = new Location(from.charAt(0),from.charAt(1));
        this.to = new Location(to.charAt(0),to.charAt(1));
    }

    @Override
    public String toString() {
        return from+" "+to;
    }
}
