package game_win;

import javax.swing.*;
import java.awt.*;

public class SettingWindow extends JFrame {
    private static final int WIDTH = 230;
    private static final int HEIGHT = 350;
    String currentWinValue = "Установленная длина: ";
    String currentFieldValue = "Установленный размер поля: ";
    JRadioButton hUman;
    JRadioButton aI;
    ButtonGroup buttonGroup;
    JLabel choiceHA;
    JButton btnStart;
    JPanel mainPanel;
    JLabel fieldSize;
    JLabel currentFieldSize;
    JSlider sliderFieldSize;
    JLabel winSize;
    JLabel currentWinSize;
    JSlider sliderWinSize;
    final int minSize = 3;

    SettingWindow(GameWindow gameWindow) {
        hUman = new JRadioButton("ЧЕЛОВЕК VS ЧЕЛОВЕК");
        aI = new JRadioButton("РОБОТ VS ЧЕЛОВЕК");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(aI);
        buttonGroup.add(hUman);
        btnStart = new JButton("Start new game");
        mainPanel = new JPanel(new GridLayout(9, 1));
        fieldSize = new JLabel(currentFieldValue + minSize);
        choiceHA = new JLabel("Выберите режим игры");
        currentFieldSize = new JLabel(currentFieldValue + minSize);
        sliderFieldSize = new JSlider(minSize, 10, minSize);
        currentWinSize = new JLabel(currentWinValue + minSize);
        winSize = new JLabel("Выберите длину для победы");

        mainPanel.add(choiceHA);
        setLocationRelativeTo(gameWindow);
        setSize(WIDTH, HEIGHT);

        sliderWinSize = new JSlider(minSize, 10, minSize); // Инициализация sliderWinSize

        sliderWinSize.addChangeListener(e -> currentWinSize.setText(currentWinValue + sliderWinSize.getValue()));

        sliderFieldSize.addChangeListener(e -> currentFieldSize.setText(currentFieldValue + sliderFieldSize.getValue()));

        btnStart.addActionListener(e -> {
            setVisible(false);
            int mode = aI.isSelected() ? 1 : 0;
            int fieldSize = sliderFieldSize.getValue();
            int winSize = sliderWinSize.getValue();
            gameWindow.startNewGame(mode, fieldSize, fieldSize, winSize);
        });

        mainPanel.add(aI);
        aI.setSelected(true);
        mainPanel.add(hUman);
        mainPanel.add(fieldSize);
        mainPanel.add(currentFieldSize);
        mainPanel.add(sliderFieldSize);
        mainPanel.add(currentWinSize);
        mainPanel.add(winSize);
        mainPanel.add(sliderWinSize);
        add(mainPanel);
        add(btnStart, BorderLayout.SOUTH);
    }
}
