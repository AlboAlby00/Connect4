
public class Main {

    private static boolean check_winner(Board bd){
        if(bd.isWinner(Color.RED)){
            bd.printBoard();
            System.out.println("Red is winner!");
            return true;
        }
        if(bd.isWinner(Color.YELLOW)){
            bd.printBoard();
            System.out.println("Yellow is winner!");
            return true;
        }
        if(bd.isFull()){
            bd.printBoard();
            System.out.println("It's a tie!");
            return true;
        }
        else return false;
    }

    public static Color opponent(Color c){
        if(c==Color.RED) return Color.YELLOW;
        else return Color.RED;
    }


    public static void main(String[] args) {
        new ConnectFourGUI();
    }
}
