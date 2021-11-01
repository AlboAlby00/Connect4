abstract public class Player {

    protected Color c;

    public Player(){
        this.c=Color.EMPTY;
    }
    public Player(Color c){
        this.c=c;
    }
    public void setDepthValue(int depthValue){
    }

    public boolean move(Board bd, int pos){
        if(bd.isFree(pos)){
            bd.insertCell(pos,this.c);
            return true;}
        return false;
    }
    abstract public boolean move(Board bd);
    public boolean isWinner(Board bd){
        return bd.isWinner(this.c);
    }
    public Color getColor(){
        return this.c;
    }
}


