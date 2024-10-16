import Classes.Location;
import Classes.Pieces.*;
import Utility.Enums.*;

public class Main {
    public static void main(String[] args) {

        //test all location constructors
        Pawn p = new Pawn(Color.White,new Location(0,0));
        System.out.println(p);

        King k = new King(Color.Black,new Location(0,0));
        System.out.println(k);

    }
}
