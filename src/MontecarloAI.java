public class MontecarloAI extends AI{

    private int numSimulations;
    MontecarloAI(Color c, int numSimulations){
        this.c = c;
        this.numSimulations=numSimulations;
    }

    @Override
    public boolean move(Board bd) {
        return false;
    }


}
