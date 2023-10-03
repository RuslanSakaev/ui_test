package server;

import javax.swing.*;

public class Main {
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
