import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourGUI {


    private JMenuBar menuBar;
    private JMenu options;
    private JMenuItem newGame;
    private JMenuItem aiStart;
    private JMenuItem humanStart;
    private JSlider depthSlider;
    private JLabel depthLabel;
    private JFrame frame;
    private JLabel[][] cells;
    private JButtonCustomed[] buttons;
    private JPanel buttonPanel;
    private JPanel gridPanel;
    private Timer timer;

    private Color currentPlayer;
    private boolean hasWinner;
    private boolean isFull;

    static ImageIcon redPng = getAndResizeImageIcon("red.png",100,100);
    static ImageIcon yellowPng = getAndResizeImageIcon("yellow.png",100,100);
    static ImageIcon greyPng = getAndResizeImageIcon("grey.png",100,100);

    private final Board bd = new Board();
    private final Player player1;
    private final Player player2;

    ConnectFourGUI(){

        int depth = 7;
        this.player1 = new MinimaxAI(Color.RED,depth);
        this.player2 = new Human();
        this.frame = new JFrame();
        this.frame.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.currentPlayer = player1.getColor();
        this.hasWinner=false;
        this.isFull=false;


        InitializeBoard();
        InitializeMenu();
        InitializeDepthSlider(depth);
        setTimer();
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setSize(new Dimension(720,750));



    };

    private void showWinnerMessage(){
        hasWinner=true;
        timer.stop();
        JOptionPane.showMessageDialog(null,"Player "+currentPlayer+" has won!");
    };

    private void InitializeDepthSlider(int def){
        this.depthSlider = new JSlider(SwingConstants.HORIZONTAL,1,10,def);
        this.depthLabel = new JLabel("Current depth value: "+def);
        this.depthSlider.setMajorTickSpacing(1);
        this.depthSlider.setPaintTicks(true);
        this.depthSlider.setSnapToTicks(true);
        this.depthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = depthSlider.getValue();
                player1.setDepthValue(value);
                depthLabel.setText("Current depth value: "+value);
            }
        });
        this.frame.add(this.depthSlider);
        this.frame.add(this.depthLabel);
    }

    private void CheckWinner(){
        if(this.bd.isWinner(player1.getColor())||this.bd.isWinner(player2.getColor())){
            showWinnerMessage();
        }
        if(bd.isFull()){
            this.isFull=true;
            JOptionPane.showMessageDialog(null,"It's a tie!");
        }

    }

    private void InitializeBoard(){

        buttonPanel=new JPanel(new FlowLayout());
        buttons = new JButtonCustomed[7];
        for(int i=0; i<7;i++){
            this.buttons[i]=new JButtonCustomed(i);
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if(currentPlayer.equals(player2.getColor())&&!hasWinner&&!isFull){
                        JButtonCustomed btn = (JButtonCustomed)  actionEvent.getSource();
                        if(player2.move(bd,btn.getIndex())){
                            TogglePlayer();
                            RefreshJBoard();}
                    }
                }
            });
            buttons[i].setPreferredSize(new Dimension(95,20));
            buttonPanel.add(buttons[i]);
        }
        this.frame.add(buttonPanel);

        gridPanel = new JPanel(new GridLayout(6,7));
        cells=new JLabel[6][7];
        for(int i=0; i<6;i++){
            for(int j=0;j<7;j++){
                cells[i][j]=new JLabel();
                cells[i][j].setIcon(greyPng);
                gridPanel.add(cells[i][j]);
            }
        }
        this.frame.add(gridPanel);



    }

    private void ResetGame(){
        bd.flush();
        RefreshJBoard();
        this.hasWinner=false;
        this.isFull=false;
        timer.start();

    }

    private void InitializeMenu(){
        this.menuBar = new JMenuBar();
        this.options = new JMenu("Options");
        this.newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               ResetGame();
            }
        });
        this.aiStart = new JMenuItem("AI starts");
        aiStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentPlayer=player1.getColor();
                ResetGame();
            }
        });
        this.humanStart =new JMenuItem("Human starts");
        humanStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentPlayer = player1.getColor();
                ResetGame();
            }
        });
        this.options.add(newGame);
        this.options.add(humanStart);
        this.options.add(aiStart);
        this.menuBar.add(options);
        this.frame.setJMenuBar(menuBar);
    }

    private void RefreshJBoard(){
        for(int i=0; i<6;i++){
            for(int j=0; j<7;j++){
                switch (this.bd.getCell(5-i,j)){
                    case RED:
                        cells[i][j].setIcon(redPng);
                        break;
                    case YELLOW:
                        cells[i][j].setIcon(yellowPng);
                        break;
                    case EMPTY:
                        cells[i][j].setIcon(greyPng);
                        break;
                }
            }
        }
    }

    private void TogglePlayer(){
        if(currentPlayer.equals(Color.RED)) currentPlayer=Color.YELLOW;
        else currentPlayer=Color.RED;
    }

    private void setTimer(){
        this.timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(currentPlayer.equals(player1.getColor())&&!hasWinner&&!isFull&&player1 instanceof AI){

                    player1.move(bd);
                    RefreshJBoard();
                    CheckWinner();
                    TogglePlayer();
                }
                if(currentPlayer.equals(player2.getColor())&&!hasWinner&&!isFull&&player2 instanceof AI){

                    player2.move(bd);
                    RefreshJBoard();
                    CheckWinner();
                    TogglePlayer();
                }
            }

        });
        this.timer.start();
    }

    private static ImageIcon getAndResizeImageIcon(String str, int width, int height){
        ImageIcon imageIcon = new ImageIcon(str); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        return imageIcon;
    }
}
