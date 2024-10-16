import Classes.Location;
import Classes.Pieces.*;
import Utility.Enums.*;

public class Main {
    public static void main(String[] args) {

        Player p = new Player(Color.White);
        System.out.println("Should be true:");
        System.out.println(p.makeMove("A1 H8"));
        System.out.println(p.makeMove("B2 G7"));
        System.out.println(p.makeMove("O-O"));
        System.out.println(p.makeMove("O-O-O"));
        System.out.println(p.makeMove("A1 H8=Q"));
        System.out.println(p.makeMove("B2 G7=N"));


        System.out.println("Should be false:");
        System.out.println(p.makeMove("Z1 @1"));
        System.out.println(p.makeMove(".........."));
        System.out.println(p.makeMove("A1.B2"));
        System.out.println(p.makeMove("H8 H9"));
        System.out.println(p.makeMove("O-O-O-O"));
        System.out.println(p.makeMove("A1 H8=A"));
        System.out.println(p.makeMove("B2 G7 Q"));




    }
}
