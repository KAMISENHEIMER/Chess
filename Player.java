public class Player{
    private Piece[] pieces;
    private String color;
    public boolean makeMove(String tryStr){
        if(tryStr.equals("O-O") || tryStr.equals("O-O-O")){
            return true;
        }
        if(tryStr.length() == 5){
            boolean checkFlag = true;
            if(tryStr.charAt(0) < 65 || tryStr.charAt(3) > 72){
                checkFlag = false;
            }
            if(tryStr.charAt(1) < 49 || tryStr.charAt(4) > 56){
                checkFlag = false;
            }
            if(tryStr.charAt(2) != 32){
                checkFlag = false;
            }
            return checkFlag;
        }else if(tryStr.length() == 7){
            boolean checkFlag = true;
            if(tryStr.charAt(0) < 65 || tryStr.charAt(3) > 72){
                checkFlag = false;
            }
            if(tryStr.charAt(1) < 49 || tryStr.charAt(4) > 56){
                checkFlag = false;
            }
            if(tryStr.charAt(2) != 32){
                checkFlag = false;
            }
            if(tryStr.charAt(5) != 61){
                checkFlag = false;
            }
            if(tryStr.charAt(6) != 66 && tryStr.charAt(6) != 78 && tryStr.charAt(6) != 81 && tryStr.charAt(6) != 82){
                checkFlag = false;
            }
            return checkFlag;
        }else{
            return false;
        }
    }
}