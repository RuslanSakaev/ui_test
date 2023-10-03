package game_win;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;

    JButton btnStart, btnExit;
    SettingWindow settingWindow;
    Map map;

    GameWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        setTitle("TicTacToe");
        setResizable(false);
        btnStart = new JButton("New Game");
        btnExit = new JButton("Exit");
        settingWindow = new SettingWindow(this);
        map = new Map();

        btnExit.addActionListener(e -> System.exit(0));

        btnStart.addActionListener(e -> settingWindow.setVisible(true));

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);

        add(panBottom, BorderLayout.SOUTH);
        add(map);

        setVisible(true);
    }

    void startNewGame(int mode, int sizeX, int sizeY, int winLen){
        map.startNewGame(mode, sizeX, sizeY, winLen);
    }
}
