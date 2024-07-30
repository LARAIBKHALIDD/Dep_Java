import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class chat_client extends JFrame {
    private JTextField msg_text;
    private JTextArea msg_area;
    private JButton button;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public chat_client() {
        setTitle("Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        msg_text = new JTextField();
        msg_area = new JTextArea();
        button = new JButton("Send");

        setLayout(new BorderLayout());
        add(msg_text, BorderLayout.SOUTH);
        add(new JScrollPane(msg_area), BorderLayout.CENTER);
        add(button, BorderLayout.EAST);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        initializeClient();
    }

    private void initializeClient() {
        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new ReceiveMessage()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = msg_text.getText();
        if (!message.isEmpty()) {
            out.println("Client: " + message);
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
                new chat_client().setVisible(true);
            }
        });
    }
}