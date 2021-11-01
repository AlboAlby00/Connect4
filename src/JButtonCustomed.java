import javax.swing.*;

public class JButtonCustomed extends JButton {
    private int index;

    JButtonCustomed(int i){
        super();
        index=i;
    }

    public int getIndex() {
        return index;
    }
}
