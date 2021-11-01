public enum Color {
    RED,
    YELLOW,
    EMPTY;
    public Color opposite(){
        if(this.equals(Color.RED)) return  Color.YELLOW;
        if(this.equals(Color.YELLOW)) return  Color.RED;
        return Color.EMPTY;
    }
}
