package testing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private JPanel mainPanel;
    private JButton startButton;
    private JRadioButton mode1RadioButton;
    private JRadioButton mode2RadioButton;
    private JRadioButton mode3RadioButton;
    private JButton playButton;
    private JButton restartButton;
    private JButton menuButton;

    public GameGUI() {
        initializeComponents();
        setupLayout();
        setupActions();
    }

    private void initializeComponents() {
        mainPanel = new JPanel();
        startButton = new JButton("Start Game");
        mode1RadioButton = new JRadioButton("Game Mode 1");
        mode2RadioButton = new JRadioButton("Game Mode 2");
        mode3RadioButton = new JRadioButton("Game Mode 3");
        playButton = new JButton("Play Game");
        restartButton = new JButton("Restart");
        menuButton = new JButton("Menu");

        // Group the radio buttons so that only one can be selected at a time
        ButtonGroup group = new ButtonGroup();
        group.add(mode1RadioButton);
        group.add(mode2RadioButton);
        group.add(mode3RadioButton);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        mainPanel.setLayout(new GridLayout(4, 1));

        mainPanel.add(startButton);
        mainPanel.add(mode1RadioButton);
        mainPanel.add(mode2RadioButton);
        mainPanel.add(mode3RadioButton);
        mainPanel.add(playButton);
        mainPanel.add(restartButton);
        mainPanel.add(menuButton);

        add(mainPanel, BorderLayout.CENTER);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupActions() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show game mode selection options
                startButton.setVisible(false);
                mode1RadioButton.setVisible(true);
                mode2RadioButton.setVisible(true);
                mode3RadioButton.setVisible(true);
                playButton.setVisible(true);
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check selected game mode and perform actions accordingly
                if (mode1RadioButton.isSelected()) {
                    // Show red circle (example action)
                    mainPanel.setBackground(Color.RED);
                } else if (mode2RadioButton.isSelected()) {
                    // Show square (example action)
                    mainPanel.setBackground(Color.BLUE);
                } else if (mode3RadioButton.isSelected()) {
                    // Handle game mode 3
                    // ...
                }

                // Show restart and menu buttons
                restartButton.setVisible(true);
                menuButton.setVisible(true);
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Restart the game (example action)
                mainPanel.setBackground(null);
            }
        });

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the main menu (example action)
                startButton.setVisible(true);
                mode1RadioButton.setVisible(false);
                mode2RadioButton.setVisible(false);
                mode3RadioButton.setVisible(false);
                playButton.setVisible(false);
                restartButton.setVisible(false);
                menuButton.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameGUI();
            }
        });
    }
}
