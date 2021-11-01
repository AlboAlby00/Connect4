public class MontecarloAI extends Player{

    private int numSimulations;
    MontecarloAI(Color c, int numSimulations){
        super(c);
        this.numSimulations=numSimulations;
    }

    @Override
    public boolean move(Board bd) {
        return false;
    }


}
