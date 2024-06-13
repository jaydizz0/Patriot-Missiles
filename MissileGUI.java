import javax.swing.*;
import java.awt.*;

public class MissileGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Parabola Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 650));
        frame.setResizable(false);

        Color inputPanelColor = new Color(42, 74, 16);
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(inputPanelColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JSlider startXSlider = new JSlider(JSlider.HORIZONTAL, 0, 250, 0);
        JSlider amplitudeSlider = new JSlider(JSlider.HORIZONTAL, 1, 4, 4);

        startXSlider.setMajorTickSpacing(50);
        startXSlider.setMinorTickSpacing(10);
        startXSlider.setPaintTicks(true);
        startXSlider.setPaintLabels(true);
        startXSlider.setForeground(Color.WHITE);

        amplitudeSlider.setMajorTickSpacing(1);
        amplitudeSlider.setMinorTickSpacing(1);
        amplitudeSlider.setPaintTicks(true);
        amplitudeSlider.setPaintLabels(true);
        amplitudeSlider.setForeground(Color.WHITE);

        JButton enemyButton = new JButton("Launch Enemy Missiles");
        enemyButton.setBackground(Color.RED);
        enemyButton.setForeground(Color.BLACK);
        enemyButton.setOpaque(true);
        enemyButton.setBorderPainted(false);

        JButton fireButton = new JButton("Fire Interceptors");
        fireButton.setBackground(Color.GREEN);
        fireButton.setForeground(Color.BLACK);
        fireButton.setOpaque(true);
        fireButton.setBorderPainted(false);

        DrawingPanel drawingPanel = new DrawingPanel(startXSlider, amplitudeSlider);

        enemyButton.addActionListener(e -> drawingPanel.launchMissile());
        fireButton.addActionListener(e -> drawingPanel.launchInterceptor());

        JLabel startXLabel = new JLabel("Start X:");
        startXLabel.setForeground(Color.white);

        JLabel ampLabel = new JLabel("Amplitude: ");
        ampLabel.setForeground(Color.white);

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(startXLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(startXSlider, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(ampLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(amplitudeSlider, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(enemyButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(fireButton, gbc);

        frame.getContentPane().add(BorderLayout.SOUTH, inputPanel);
        frame.getContentPane().add(BorderLayout.CENTER, drawingPanel);

        JMenuBar menuBar = new JMenuBar();
        JMenu speedMenu = new JMenu("Speed");

        JMenu missileSpeedMenu = new JMenu("Missile Speed");
        JMenuItem missileSlow = new JMenuItem("Slow");
        JMenuItem missileMedium = new JMenuItem("Medium");
        JMenuItem missileFast = new JMenuItem("Fast");

        missileSlow.addActionListener(e -> drawingPanel.setMissileSpeed(1));
        missileMedium.addActionListener(e -> drawingPanel.setMissileSpeed(3));
        missileFast.addActionListener(e -> drawingPanel.setMissileSpeed(5));

        missileSpeedMenu.add(missileSlow);
        missileSpeedMenu.add(missileMedium);
        missileSpeedMenu.add(missileFast);

        JMenu interceptorSpeedMenu = new JMenu("Interceptor Speed");
        JMenuItem interceptorSlow = new JMenuItem("Slow");
        JMenuItem interceptorMedium = new JMenuItem("Medium");
        JMenuItem interceptorFast = new JMenuItem("Fast");

        interceptorSlow.addActionListener(e -> drawingPanel.setInterceptorSpeed(2));
        interceptorMedium.addActionListener(e -> drawingPanel.setInterceptorSpeed(5));
        interceptorFast.addActionListener(e -> drawingPanel.setInterceptorSpeed(8));

        interceptorSpeedMenu.add(interceptorSlow);
        interceptorSpeedMenu.add(interceptorMedium);
        interceptorSpeedMenu.add(interceptorFast);

        speedMenu.add(missileSpeedMenu);
        speedMenu.add(interceptorSpeedMenu);
        menuBar.add(speedMenu);
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setVisible(true);
    }
}
