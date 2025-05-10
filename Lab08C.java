import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import gbs.*;
import gbs.Matrix;
import java.awt.Color;
import java.awt.Font;

public class Lab08C extends GBSNetworkApp {
    // Declare your private instance variables here...
    private final Color NAVY = new Color(0, 24, 73);
    private final Color GOLD = new Color(166, 124, 0);
    private final int BLUE_TOKEN = 1;
    private final int GOLD_TOKEN = 5;

    private Image BACKGROUND;
    private Image BLUE_IMAGE;
    private Image GOLD_IMAGE;
    private Font turn;

    private ArrayList<JButton> buttons;
    private JButton playAgain;
    private Matrix<Integer> gameBoard;
    private int currentPlayer;
    private int winningPlayer;

    public Lab08C(JFrame f) {
        super.setBackground(new Color(201, 227, 202));
        super.setSize(770, 550);
        super.setLayout(null);

        BACKGROUND = super.getImage("media/board.png");
        BLUE_IMAGE = super.getImage("media/blue.png");
        GOLD_IMAGE = super.getImage("media/gold.png");
        turn = new Font(Font.DIALOG, Font.ITALIC, 42);

        buttons = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            JButton b = new JButton("Drop");
            b.addActionListener(this);
            b.setBounds(96 + (i * 84), 5, 75, 30);
            super.add(b);
            buttons.add(b);
        }

        playAgain = new JButton("Play again!");
        playAgain.addActionListener(this);
        playAgain.setBounds(310,50,150,30);
        super.add(playAgain);
        playAgain.setVisible(false);

        int number = (int) (Math.random()*100+1);
        connect("localhost", "bveitch" + number);


        this.newGame();
    }

    public void newGame() {
        // Enable buttons
        for (int i = 0; i < 7; i++) {
            JButton b = buttons.get(i);
            b.setEnabled(true);
        }

        // Choose random player
        if (Math.random() < 0.5) {
            currentPlayer = GOLD_TOKEN;
            goldButtons();
        } else {
            currentPlayer = BLUE_TOKEN;
            blueButtons();
        }

        gameBoard = new Matrix<>(6, 7, 0);

        winningPlayer = -1;

        playAgain.setVisible(false);
    }

    public void goldButtons() {
        for (int i = 0; i < 7; i++) {
            JButton b = buttons.get(i);
            if (b.isEnabled()) {
                b.setForeground(NAVY);
                b.setBackground(GOLD);
            }
        }
    }

    public void blueButtons() {
        for (int i = 0; i < 7; i++) {
            JButton b = buttons.get(i);
            if (b.isEnabled()) {
                b.setForeground(GOLD);
                b.setBackground(NAVY);
            }
        }
    }

    public void checkHorizontal() {
        // to be completed by you...
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7 - 3; c++) {
                if (gameBoard.get(r, c) == gameBoard.get(r, c + 1) && gameBoard.get(r, c + 1) == gameBoard.get(r, c + 2)
                        && gameBoard.get(r, c + 2) == gameBoard.get(r, c + 3) && gameBoard.get(r, c) != 0) {
                    winningPlayer = gameBoard.get(r, c);
                }
            }
        }

    }

    public void checkVertical() {
        // to be completed by you...
        for (int r = 0; r < 6 - 3; r++) {
            for (int c = 0; c < 7; c++) {
                if (gameBoard.get(r, c) == gameBoard.get(r + 1, c) && gameBoard.get(r + 1, c) == gameBoard.get(r + 2, c)
                        && gameBoard.get(r + 2, c) == gameBoard.get(r + 3, c) && gameBoard.get(r, c) != 0) {
                    winningPlayer = gameBoard.get(r, c);
                }
            }
        }

    }

    public void checkDiagonals() {
        // to be completed by you...
        for (int r = 0; r < 6 - 3; r++) {
            for (int c = 0; c < 7 - 3; c++) {
                if (gameBoard.get(r, c) == gameBoard.get(r + 1, c + 1)
                        && gameBoard.get(r + 1, c + 1) == gameBoard.get(r + 2, c + 2)
                        && gameBoard.get(r + 2, c + 2) == gameBoard.get(r + 3, c + 3) && gameBoard.get(r, c) != 0) {
                    winningPlayer = gameBoard.get(r, c);
                }
            }
        }
        for (int r = 0 + 3; r < 6; r++) {
            for (int c = 0; c < 7 - 3; c++) {
                if (gameBoard.get(r, c) == gameBoard.get(r - 1, c + 1)
                        && gameBoard.get(r - 1, c + 1) == gameBoard.get(r - 2, c + 2)
                        && gameBoard.get(r - 2, c + 2) == gameBoard.get(r - 3, c + 3) && gameBoard.get(r, c) != 0) {
                    winningPlayer = gameBoard.get(r, c);
                }
            }
        }
    }

    @Override
    public void onRecieve(String message) {
        System.out.println("[N]: " + message);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // keep this as the first line of this method
        g.setColor(BLACK);
        g.drawImage(BACKGROUND, 0, 0, this);

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                int gameBoardValue = gameBoard.get(r, c);
                int x = 83 * c + 103;
                int y = 71 * r + 50;
                if (gameBoardValue == BLUE_TOKEN) {
                    g.drawImage(BLUE_IMAGE, x, y, this);
                }
                if (gameBoardValue == GOLD_TOKEN) {
                    g.drawImage(GOLD_IMAGE, x, y, this);
                }
            }
        }

        g.setFont(turn);
        if (winningPlayer == BLUE_TOKEN) {
            g.setColor(NAVY);
            g.drawString("Blue Wins!", 254, 520);
            // playGame.setVisible(true);
        } else if (winningPlayer == GOLD_TOKEN) {
            g.setColor(GOLD);
            g.drawString("Gold Wins!", 254, 520);
            // playGame.setVisible(true);
        } else if (winningPlayer == 3) {
            g.setColor(BLACK);
            g.drawString("Tie Game!", 254, 520);
            // playGame.setVisible(true);
        } else {
            if (currentPlayer == BLUE_TOKEN) {
                g.setColor(NAVY);
                g.drawString("Blue's Turn...", 254, 520);
            } else {
                g.setColor(GOLD);
                g.drawString("Gold's Turn...", 254, 520);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == playAgain) {
            newGame();
        }

        // Check buttons
        for (int i = 0; i < 7; i++) {
            JButton b = buttons.get(i);
            if (ae.getSource() == b) {
                for (int r = 5; r >= 0; r--) {
                    if (gameBoard.get(r,i) == 0) {
                        gameBoard.set(r, i, currentPlayer);
                        // send(currentPlayer + "," + r + "," + i);
                        break;
                    }
                }

                if (currentPlayer == GOLD_TOKEN) {
                    currentPlayer = BLUE_TOKEN;
                    blueButtons();
                } else {
                    currentPlayer = GOLD_TOKEN;
                    goldButtons();
                }
            }
        }

        // Disable buttons for filled columns
        int countColumnsFilled = 0;
        for (int i = 0; i < 7; i++) {
            if (gameBoard.get(0, i) != 0) {
                countColumnsFilled++;
                buttons.get(i).setEnabled(false);
                buttons.get(i).setForeground(BLACK);
                buttons.get(i).setBackground(LIGHT_GRAY);
            }
        }

        checkDiagonals();
        checkHorizontal();
        checkVertical();

        if (countColumnsFilled == 7 && winningPlayer == -1) {
            winningPlayer = 3;
        }

        if (winningPlayer != -1) {
            playAgain.setVisible(true);
            for (int i = 0; i < 7; i++) {
                buttons.get(i).setEnabled(false);
                buttons.get(i).setForeground(BLACK);
                buttons.get(i).setBackground(LIGHT_GRAY);
            }
        }

        super.repaint(); // keep this as the last line of this method
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame fr = new JFrame("Lab08C");
                fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fr.setLocation(10, 10);
                fr.setResizable(false);
                fr.setIconImage(GBSApp.ICON);
                fr.setContentPane(new Lab08C(fr));
                fr.pack();
                fr.setVisible(true);
            }
        });
    }
}