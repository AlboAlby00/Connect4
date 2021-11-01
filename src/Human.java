import java.util.Scanner;

public class Human extends Player{
    Human(){
        this.c=Color.YELLOW;
    }
    Human(Color c){this.c=c;}
    public boolean move(Board bd){
        bd.printBoard();
        Scanner in=new Scanner(System.in);
        int pos;
        while(true){
            System.out.println("Select a column between 1 and 7:");
            pos = in.nextInt()-1;
            if(!bd.isFree(pos)){
                System.out.println("Value inserted is not valid! try another one!");
                bd.printBoard();
            }
            else break;}
        return super.move(bd,pos);

    }

}
