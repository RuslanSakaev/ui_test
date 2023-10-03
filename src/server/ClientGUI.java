package server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientGUI extends JFrame {
    private final JTextArea chatArea;
    private final JTextField messageField;
    private final JButton sendButton;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private boolean loggedIn = false;
    private boolean serverConnected = false;
    private final File chatHistoryFile;
    private final List<String> chatHistory;

    public ClientGUI() {
        setTitle("Chat Client");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatHistoryFile = new File("chat_history.txt");
        chatHistory = new ArrayList<>();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));

        JLabel ipAddressLabel = new JLabel("IP Address:");
        JTextField ipAddressField = new JTextField("127.0.0.1");
        JLabel portLabel = new JLabel("Port:");
        JTextField portField = new JTextField("8189");

        topPanel.add(ipAddressLabel);
        topPanel.add(ipAddressField);
        topPanel.add(portLabel);
        topPanel.add(portField);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 1));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        infoPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        infoPanel.add(loginPanel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            if (serverConnected && !loggedIn && !username.isEmpty() && !password.isEmpty()) {
                loggedIn = true;
                usernameField.setEditable(false);
                passwordField.setEditable(false);
                loginButton.setEnabled(false);
                sendButton.setEnabled(true);
                showMessage("Пользователь " + usernameField.getText() + " присоединился к чату.");
                loadChatHistory(); // Загрузка истории чата из файла при успешном подключении
                updateChatAreaFromHistory(); // Обновление chatArea после загрузки истории
            } else if (!serverConnected) {
                showMessage("Server is not connected. Please start the server first.");
            }
        });

        messageField.addActionListener(e -> sendMessage());

        sendButton.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    public void setServerConnected(boolean isConnected) {
        serverConnected = isConnected;
    }

    private void showMessage(String message) {
        chatArea.append(message + "\n");
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatArea.append(usernameField.getText() + ": " + message + "\n");
            chatHistory.add(usernameField.getText() + ": " + message);
            messageField.setText("");
            saveChatHistory(); // Сохранение истории чата в файл
        }
    }

    private void loadChatHistory() {
        if (chatHistoryFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(chatHistoryFile), StandardCharsets.UTF_8)
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    chatHistory.add(line);
                }
                reader.close();
                showMessage("Loaded chat history from file.");
            } catch (IOException e) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, "Error loading chat history", e);
            }
        }
    }

    private void saveChatHistory() {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(chatHistoryFile), StandardCharsets.UTF_8)
            );
            for (String line : chatHistory) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, "Error saving chat history", e);
        }
    }

    private void updateChatAreaFromHistory() {
        // chatArea.setText(""); // Очистим chatArea перед добавлением сообщений из истории
        for (String message : chatHistory) {
            chatArea.append(message + "\n"); // Добавим каждое сообщение из истории
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }
}


