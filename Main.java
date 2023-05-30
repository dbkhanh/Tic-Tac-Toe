package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static int sec = 0;
    private static Timer timer = new Timer();
    private static JLabel lblTime;
    private static Board board;
    private static JButton btnButton;
    public static void main(String[] args){
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);

        board = new Board();
        board.setEndGameListener(new EndGameListener(){
            @Override
            public void end(String player, int st){
                if(st == Board.WIN){
                    JOptionPane.showMessageDialog(null, player + " WINS");
                    stopGame();

                }
                else if(st == Board.DRAW){
                    JOptionPane.showMessageDialog(null, "DRAW");
                    stopGame();
                }
            }
        });
        board.setPreferredSize(new Dimension(300, 300));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(flowLayout);

        btnButton = new JButton("Start");
        //Timer and TimerTask to find count-down
        lblTime = new JLabel("0:0");
        bottomPanel.add(lblTime);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(btnButton);
        btnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(btnButton.getText().equals("Start")){
                    startGame();
                }else{
                    stopGame();
                }
            }
        });

        jPanel.add(board);
        jPanel.add(bottomPanel);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame jFrame = new JFrame("Tic-Tac-Toe");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(true);
        jFrame.add(jPanel);

        jFrame.pack();

        int x = (int) (dimension.getWidth()/2 - jFrame.getWidth()/2);
        int y = (int) (dimension.getHeight()/2 - jFrame.getHeight()/2);

        jFrame.setLocation(x, y);
        jFrame.setVisible(true);

        ////////////////////////////////
        // Ask if the user wants to play with the robot
//        int robotChoice = JOptionPane.showConfirmDialog(null, "Do you want to play with the robot?", "Play with Robot", JOptionPane.YES_NO_OPTION);
//
//        if (robotChoice == JOptionPane.YES_OPTION) {
//            // Ask if the robot should go first
//            int robotFirstChoice = JOptionPane.showConfirmDialog(null, "Do you want the robot to go first?", "Robot First", JOptionPane.YES_NO_OPTION);
//
//            if (robotFirstChoice == JOptionPane.YES_OPTION) {
//                // Robot goes first
//                board.setCurrentPlayer(Cell.O_VALUE);
//                if (board.getCurrentPlayer().equals(Cell.O_VALUE)) {
//                    board.makeRobotMove();
//                }
//            } else {
//                // Human goes first
//                board.setCurrentPlayer(Cell.X_VALUE);
//            }
//        } else {
//            // Human vs. Human
//            board.setCurrentPlayer(Cell.X_VALUE);
//        }

        board.setCurrentPlayer(Cell.X_VALUE);

    }

    private static void startGame() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want X to go first?", "Choose Player", JOptionPane.YES_NO_OPTION);
        String currentPlayer = (choice == JOptionPane.YES_OPTION) ? Cell.X_VALUE : Cell.O_VALUE;
        board.setCurrentPlayer(currentPlayer);

        sec = 0;
        lblTime.setText("0:0");
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sec++;
                String value = sec / 60 + ":" + sec % 60;
                lblTime.setText(value);
            }
        }, 1000, 1000);
        btnButton.setText("Stop");
    }

    private static void stopGame(){
        btnButton.setText("Start");
        sec = 0;

        lblTime.setText("0:0");
        timer.cancel();
        timer = new Timer();

        board.reset();
    }
}