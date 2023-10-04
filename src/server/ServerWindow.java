package server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerWindow extends JFrame {
    private final JTextArea logTextArea;
    private ServerSocket serverSocket;
    private boolean isServerRunning = false;

    public ServerWindow(ClientGUI clientGUI) {


        setTitle("Server Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Server");
        JButton stopButton = new JButton("Stop Server");
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            if (!isServerRunning) {
                int port = 8189; // Порт, который слушаем
                try {
                    serverSocket = new ServerSocket(port);
                    logTextArea.append("Server started on port " + port + "\n");
                    isServerRunning = true;

                    // Уведомление ClientGUI о подключении к серверу
                    clientGUI.setServerConnected(true);

                    // В этом месте начинаем принимать клиентские подключения
                } catch (IOException ex) {
                    logTextArea.append("Error starting server: " + ex.getMessage() + "\n");
                }
            } else {
                logTextArea.append("Server is already running\n");
            }
        });

        stopButton.addActionListener(e -> {
            if (isServerRunning) {
                try {
                    serverSocket.close();
                    logTextArea.append("Server stopped\n");
                    isServerRunning = false;

                    // Уведомление ClientGUI об отключении от сервера
                    clientGUI.setServerConnected(false);
                } catch (IOException ex) {
                    logTextArea.append("Error stopping server: " + ex.getMessage() + "\n");
                }
            } else {
                logTextArea.append("Server is not running\n");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientGUI clientGUI = new ClientGUI();
            ServerWindow serverWindow = new ServerWindow(clientGUI);
            clientGUI.setServerConnected(false); // Изначально сервер не подключен
        });
    }
}