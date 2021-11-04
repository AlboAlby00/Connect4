import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MontecarloAI extends AI{

    private int numSimulations;

    MontecarloAI(Color c, int numSimulations){
        this.c = c;
        this.numSimulations=numSimulations;
    }

    @Override
    public boolean move(Board bd) {
        int pos = bestMove(bd);
        return super.move(bd,pos);
    }

    private double simulateMove(int pos, Board bd){
        int val=0;
        for(int countSim=0; countSim<this.numSimulations;countSim++){
            Color col=this.getColor();
           Board newBd = (Board) bd.clone();
           newBd.insertCell(pos,this.getColor());


            while(true){
                col=col.opposite();
                if(newBd.isFull()) {
                    val+=0;
                    //System.out.println("full");
                    break;
                }
                if(newBd.isWinner(this.getColor())) {
                    val+=1;
                    //System.out.println("win");
                    break;
                }
                if(newBd.isWinner(this.getColor().opposite())) {
                    val-=1;
                    //System.out.println("lose");
                    break;
                }
                int p = selectRandomMove(newBd);
                newBd.insertCell(p, col);
                //System.out.println(p);

            }
        }
        return (double) val/numSimulations;
    }

    private int bestMove(Board bd){
        double maxVal=Integer.MIN_VALUE;
        int pos=0;
        for(int i=0; i<7; i++){

            double val;
            if(bd.isFree(i)){
                val=simulateMove(i,bd);
                //System.out.println("mossa " +i+ " : "+val);
                if(val>=maxVal) pos=i;
                maxVal=Math.max(val,maxVal);
            }
        }
        System.out.println("best move found with Montecarlo simulation: "+pos+ " maxVal: " + maxVal);
        return pos;
    }

    int selectRandomMove(Board newBd){
        Random gen = new Random();
        //System.out.println("start");
        int v=0;
        while(true){
            v = gen.nextInt(7);
            //System.out.println("ran: "+v);
            //newBd.printFull();
            if(newBd.isFree(v)) break;

        }
        //System.out.println("end");
        return v;

    }



}
