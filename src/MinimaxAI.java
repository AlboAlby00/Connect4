

public class MinimaxAI extends Player{

    private int depthValue;

    public MinimaxAI(){
        this.c=Color.RED;
        this.depthValue=5;
    }
    public MinimaxAI(Color c,int depth){
        this.c=c;
        this.depthValue=depth;
    }
    private int minimax(Board bd, boolean isAi, int depth, int alpha, int beta) {


        if(bd.isWinner(this.c))  return Integer.MAX_VALUE;
        if(bd.isWinner(this.c.opposite())) return Integer.MIN_VALUE;
        if(bd.isFull()) return 0;
        if(depth==depthValue) return this.h(bd);

        if(isAi){
            int max=Integer.MIN_VALUE;
            for(int i=0; i<7; i++){
                if(bd.isFree(i)){
                    int val;
                    bd.insertCell(i,this.c);
                    val=minimax(bd,false,depth+1,alpha,beta);
                    bd.reverseCell(i);
                    max=Math.max(max,val);
                    alpha=Math.max(val,alpha);
                    if(beta<=alpha) break;
                }
            }
            return max;
        } else{
            int min= Integer.MAX_VALUE;
            for(int i=0; i<7; i++){
                if(bd.isFree(i)){
                    int val;
                    bd.insertCell(i,this.c.opposite());
                    val=minimax(bd,true,depth+1,alpha,beta);
                    bd.reverseCell(i);
                    min=Math.min(min,val);
                    beta=Math.min(beta,min);
                    if(alpha>=beta) break;
                }
            }
            return min;

        }
    }
    public void setDepthValue(int depthValue){
        this.depthValue=depthValue;
    }
    public int h(Board bd){

        if(bd.isWinner(this.c)) return Integer.MAX_VALUE;
        if(bd.isWinner(this.c.opposite())) return Integer.MIN_VALUE;
        if(bd.isFull()) return 0;
        int balance=0;
        for(int lin=0;lin<6;lin++){
            for(int col=0;col<4;col++){
                int col2=col,count=0;
                while (col2<7&&count<4){
                    if(bd.getCell(lin,col2)!=this.c.opposite()) count++;
                    else break;
                    if(count==4) balance++;
                    col2++;
                }
                col2=col;count=0;
                while (col2<7&&count<4){
                    if(bd.getCell(lin,col2)!=this.c) count++;
                    else break;
                    if(count==4) balance--;
                    col2++;
                }

            }
        }
        //checking vertically
        for(int col=0;col<7;col++){
            for(int lin=0;lin<3;lin++){
                int line2=lin,count=0;
                while (line2<6&&count<4){
                    if(bd.getCell(lin,col)!=this.c.opposite()) count++;
                    else break;
                    if(count==4) balance++;
                    line2++;
                }
                line2=lin;count=0;
                while (line2<6&&count<4){
                    if(bd.getCell(line2,col)!=this.c) count++;
                    else break;
                    if(count==4) balance--;
                    line2++;
                }

            }}
        //checking left to right diagonal
        for(int col=0;col<4;col++){
            for(int line=3;line<6;line++){
                if(bd.getCell(line,col)!=c.opposite()&&bd.getCell(line-1,col+1)!=c.opposite()&&bd.getCell(line-2,col+2)!=c.opposite()&&bd.getCell(line-3,col+3)!=c.opposite())
                    balance++;
                if(bd.getCell(line,col)!=c&&bd.getCell(line-1,col+1)!=c&&bd.getCell(line-2,col+2)!=c&&bd.getCell(line-3,col+3)!=c)
                    balance--;
            }
        }
        //checking right to left diagonal
        for(int col=3;col<7;col++){
            for(int line=3;line<6;line++){
                if(bd.getCell(line,col)!=c.opposite()&&bd.getCell(line-1,col-1)!=c.opposite()&&bd.getCell(line-2,col-2)!=c.opposite()&&bd.getCell(line-3,col-3)!=c.opposite())
                    balance++;
                if(bd.getCell(line,col)!=c&&bd.getCell(line-1,col-1)!=c&&bd.getCell(line-2,col-2)!=c&&bd.getCell(line-3,col-3)!=c)
                    balance--;
            }
        }
        return balance;
    }
    private int bestMove(Board bd){
        int maxVal=Integer.MIN_VALUE;
        int pos=0;
        for(int i=0; i<7; i++){

            int val;
            if(bd.isFree(i)){
                val=minimax(bd.insertCell(i,this.c),false,0,Integer.MIN_VALUE,Integer.MAX_VALUE);
                //System.out.println("mossa " +i+ " : "+val);
                bd.reverseCell(i);
                if(val>=maxVal) pos=i;
                maxVal=Math.max(val,maxVal);
            }
        }
        System.out.println("best move: "+pos+ " maxVal: " + maxVal);
        return pos;
    }
    public boolean move(Board bd){
        int pos=this.bestMove(bd);
        //System.out.println(pos);

        return super.move(bd, pos);




    }

}
