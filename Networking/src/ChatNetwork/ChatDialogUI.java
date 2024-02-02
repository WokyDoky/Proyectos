package ChatNetwork;


// $Id: ChatDialogUI.java,v 1.3 2018/04/06 21:32:56 cheon Exp $

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

/** A simple chat dialog. */
@SuppressWarnings("serial")
public class ChatDialogUI extends JDialog {

    /** Default dimension of chat dialogs. */
    private final static Dimension DIMENSION = new Dimension(400, 400);

    private JButton connectButton;
    private JButton sendButton;
    private JTextField serverEdit;
    private JTextField portEdit;
    private JTextArea msgDisplay;
    private JTextField msgEdit;

    //My field variables.
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    /** Create a main dialog. */
    public ChatDialogUI() {
        this(DIMENSION);
    }

    /** Create a main dialog of the given dimension. */
    public ChatDialogUI(Dimension dim) {
        super((JFrame) null, "JavaChat");
        configureGui();
        setSize(dim);
        //setResizable(false);
        connectButton.addActionListener(this::connectClicked);
        sendButton.addActionListener(this::sendClicked);
        setLocationRelativeTo(null);
    }

    /** Configure UI of this dialog. */
    private void configureGui() {
        JPanel connectPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        connectButton = new JButton("Connect");
        connectButton.setFocusPainted(false);
        serverEdit = new JTextField("127.0.0.1", 18);
        serverEdit.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                serverEdit.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        portEdit = new JTextField("8000", 4);
        portEdit.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                portEdit.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        connectPanel.add(connectButton);
        connectPanel.add(serverEdit);
        connectPanel.add(portEdit);

        msgDisplay = new JTextArea(10, 30);
        msgDisplay.setEditable(false);
        DefaultCaret caret = (DefaultCaret)msgDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); // autoscroll
        JScrollPane msgScrollPane = new JScrollPane(msgDisplay);

        JPanel sendPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        msgEdit = new JTextField("Enter a message.", 27);
        msgEdit.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (msgEdit.getText().equals("Enter a message.")) {
                    msgEdit.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        sendButton = new JButton("Send");
        msgEdit.addActionListener(this::sendClicked);
        sendButton.setFocusPainted(false);
        sendPanel.add(msgEdit);
        sendPanel.add(sendButton);

        setLayout(new BorderLayout());
        add(connectPanel, BorderLayout.NORTH);
        add(msgScrollPane, BorderLayout.CENTER);
        add(sendPanel, BorderLayout.SOUTH);
    }

    /** Callback to be called when the connect button is clicked. */
    private void connectClicked(ActionEvent event){

        //This is my implementation
        //To be honest I don't really know how to use the professor class.
        //Don't know how to create more clients.
        //However, this does work but since I am running local server,
        //my computer doesn't allow me to run the code in parallel,
        //but it does send the message to the server and back.

        //I did add a method that prints what the servers see as proof.
        //If this is wrong, some clarification would be appreciated.


        String host = serverEdit.getText();
        int port = Integer.parseInt(portEdit.getText());

        try {
            System.out.println(host + host.getClass().getName());
            System.out.println(port);
            // Connect to the server
            socket = new Socket(host, port);

            // Initialize input and output streams for communication
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Create a separate thread to receive messages from the server
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();

            // Enable the UI components
            connectButton.setEnabled(false);
            msgEdit.setEnabled(true);
            sendButton.setEnabled(true);

        } catch (UnknownHostException e) {
            e.printStackTrace();
            warn("Unknown host: " + host);
        } catch (IOException e) {
            e.printStackTrace();
            warn("Error connecting to the server");
        }


    }
    private void receiveMessages() {
        try {
            String receivedMessage;
            while ((receivedMessage = reader.readLine()) != null) {
                updateMsgDisplay(receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            warn("Error receiving messages from the server");
        }
    }

    private void updateMsgDisplay(String receivedMessage) {
        msgDisplay.append("Server: " + receivedMessage + "\n");
    }

    /** Callback to be called when the send button is clicked. */
    private void sendClicked(ActionEvent event) {

        String txt = msgEdit.getText();
        writer.println(txt);
        msgDisplay.append(txt + "\n");
        msgEdit.setText("");
    }

    /** Show the given message in a dialog. */
    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "JavaChat",
                JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        ChatDialogUI dialog = new ChatDialogUI();
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
