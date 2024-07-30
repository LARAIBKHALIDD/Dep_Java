import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class chat_server extends JFrame {
    private JTextArea msg_area;
    private JTextField msg_text;
    private JButton msg_send;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public chat_server() {
        setTitle("Chat Server");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        msg_area = new JTextArea();
        msg_text = new JTextField();
        msg_send = new JButton("Send");

        setLayout(new BorderLayout());
        add(new JScrollPane(msg_area), BorderLayout.CENTER);
        add(msg_text, BorderLayout.SOUTH);
        add(msg_send, BorderLayout.EAST);

        msg_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        initializeServer();
    }

    private void initializeServer() {
        try {
            serverSocket = new ServerSocket(12345);
            msg_area.append("Server started. Waiting for a client...\n");

            clientSocket = serverSocket.accept();
            msg_area.append("Client connected.\n");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            new Thread(new ReceiveMessage()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = msg_text.getText();
        if (!message.isEmpty()) {
            msg_area.append("Server: " + message + "\n");
            out.println("Server: " + message);
            msg_text.setText("");
        }
    }

    private class ReceiveMessage implements Runnable {
        @Override
        public void run() {
            try {
                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    msg_area.append(receivedMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new chat_server().setVisible(true);
            }
        });
    }
}