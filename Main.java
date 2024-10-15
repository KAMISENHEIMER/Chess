import Utility.Enums.*;

public class Main {
    public static void main(String[] args) {

        //test all location constructors
        Location l1 = new Location(Coordinate.B, 5);
        Location l2 = new Location(1, 2);
        Location l3 = new Location(Coordinate.G, Coordinate.G);
        Location l4 = new Location(0, 0);


        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l3);
        System.out.println(l4);

    }
}
