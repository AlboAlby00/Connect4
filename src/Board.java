
public class Board {

    private Color[][] matrix;
    private int[] full;

    public Board(){
        this.full = new int[7];
        this.matrix=new Color[6][7];
        for(int col=0;col<7;col++) {
            for (int lin = 0; lin < 6; lin++) {
                this.matrix[lin][col]=Color.EMPTY;
            }
        }

        for(int i=0;i<7;i++) this.full[i]=0;
    }



    public Color getCell(int x, int y){
        return this.matrix[x][y];
    }
    public boolean isFree(int pos){
        //System.out.println("IsFree called");
        //System.out.println(this.full[pos]<6);
        return full[pos]<6;
    }

    public void reverseCell(int pos){
        full[pos]--;
        this.matrix[full[pos]][pos] = Color.EMPTY;
    }

    public Board insertCell(int pos,Color c){
        if(c!=Color.EMPTY&&this.isFree(pos)) {
            this.matrix[full[pos]][pos] = c;
            full[pos]++;
        } else System.out.println("Error while inserting at position "+pos);
        return this;
    }

    private String symbol(int lin, int col){
        if(this.matrix[lin][col]==Color.RED) return " R ";
        if(this.matrix[lin][col]==Color.YELLOW) return " Y ";
        else return "   ";

    }
    public void printBoard(){
        for(int lin=5;lin>=0;lin--){
            System.out.print("|");
            for(int col=0;col<7;col++){
                System.out.print(symbol(lin,col));
                System.out.print("|");
            }
            System.out.println("");
        }
    }

    public void printFull(){
        for(int i : this.full){
            System.out.print(Integer.toString(i)+" ");
        }
        System.out.print("\n");
    }

    public int cellLeft(){
        int freeCell=6*7;
        for(int i : this.full) freeCell -= i;
        return freeCell;
    }

    public boolean isFull(){
        return this.cellLeft() == 0;
    }



    public boolean isWinner(Color c){
        //checking horizontally
        for(int lin=0;lin<6;lin++){
            int cons=0;
            for(int col=0;col<7;col++){
                if(this.matrix[lin][col]==c){
                    cons++;
                }
                else cons=0;
                if(cons==4) return true;
            }
        }
        //checking vertically
        for(int col=0;col<7;col++){
            int cons=0;
            for(int lin=0;lin<6;lin++){
                if(this.matrix[lin][col]==c){
                    cons++;
                }
                else cons=0;
                if(cons==4) return true;
            }
        }
        //checking left to right diagonal
        for(int col=0;col<4;col++){
            for(int line=3;line<6;line++){
                if(this.matrix[line][col]==c&&this.matrix[line-1][col+1]==c&&this.matrix[line-2][col+2]==c&&this.matrix[line-3][col+3]==c)
                    return true;
            }
        }
        //checking right to left diagonal
        for(int col=3;col<7;col++){
            for(int line=3;line<6;line++){
                if(this.matrix[line][col]==c&&this.matrix[line-1][col-1]==c&&this.matrix[line-2][col-2]==c&&this.matrix[line-3][col-3]==c)
                    return true;
            }
        }
        return false;
    }

    public void flush(){
        for(int i=0; i<6; i++){
            for(int j=0;j<7;j++){
                this.matrix[i][j]=Color.EMPTY;
            }
        }
        for(int i=0; i<7;i++) this.full[i]=0;
    }

    @Override
    protected Object clone()  {
        Board bd = new Board();
        for(int col=0;col<7;col++) {
            for (int lin = 0; lin < 6; lin++) {
                bd.matrix[lin][col]=this.matrix[lin][col];
            }
        }

        for(int i=0;i<7;i++) bd.full[i]=this.full[i];
        return bd;
    }

}
