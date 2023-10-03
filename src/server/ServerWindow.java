package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerWindow extends JFrame {
    private JTextArea logTextArea;
    private JButton startButton;
    private JButton stopButton;
    private ServerSocket serverSocket;
    private boolean isServerRunning = false;
    private ClientGUI clientGUI; // Добавьте поле для хранения экземпляра ClientGUI

    public ServerWindow(ClientGUI clientGUI) { // Передайте экземпляр ClientGUI в конструктор
        this.clientGUI = clientGUI;

        setTitle("Server Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerRunning) {
                    int port = 8189; // Порт, который вы хотите слушать
                    try {
                        serverSocket = new ServerSocket(port);
                        logTextArea.append("Server started on port " + port + "\n");
                        isServerRunning = true;

                        // Уведомьте ClientGUI о подключении к серверу
                        clientGUI.setServerConnected(true);

                        // В этом месте можно начать принимать клиентские подключения
                    } catch (IOException ex) {
                        logTextArea.append("Error starting server: " + ex.getMessage() + "\n");
                    }
                } else {
                    logTextArea.append("Server is already running\n");
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerRunning) {
                    try {
                        serverSocket.close();
                        logTextArea.append("Server stopped\n");
                        isServerRunning = false;

                        // Уведомьте ClientGUI о отключении от сервера
                        clientGUI.setServerConnected(false);
                    } catch (IOException ex) {
                        logTextArea.append("Error stopping server: " + ex.getMessage() + "\n");
                    }
                } else {
                    logTextArea.append("Server is not running\n");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientGUI clientGUI = new ClientGUI();
                ServerWindow serverWindow = new ServerWindow(clientGUI);
                clientGUI.setServerConnected(false); // Изначально сервер не подключен
            }
        });
    }
}