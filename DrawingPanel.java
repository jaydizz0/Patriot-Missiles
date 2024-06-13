import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private int startX;
    private int endX = 600;
    private double amplitude = 0.004; // Adjusted default amplitude
    private List<Missile> missiles = new ArrayList<>();
    private List<Interceptor> interceptors = new ArrayList<>();
    private int missileSpeed = 1;
    private int interceptorSpeed = 2;
    private Image explosionImage;
    private Image backgroundImage;

    public DrawingPanel(JSlider startXSlider, JSlider amplitudeSlider) {
        startXSlider.addChangeListener(e -> {
            startX = startXSlider.getValue();
            repaint();
        });

        amplitudeSlider.addChangeListener(e -> {
            amplitude = amplitudeSlider.getValue() * 0.001; // Adjust scaling factor for amplitude
            repaint();
        });

        try {
            explosionImage = new ImageIcon(getClass().getResource("bomb-png-46610.png")).getImage();
            backgroundImage = new ImageIcon(getClass().getResource("desert.jpeg")).getImage();
            System.out.println("Images loaded successfully");
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

        Timer timer = new Timer(30, e -> {
            for (Missile missile : missiles) {
                missile.updatePosition();
            }
            for (Interceptor interceptor : interceptors) {
                interceptor.updatePosition(missiles, getHeight());
            }
            checkCollisions();
            missiles.removeIf(missile -> missile.isExplosionExpired() || checkBuildingCollision(missile));
            interceptors.removeIf(Interceptor::isDestroyed);
            repaint();
        });
        timer.start();
    }

    public void launchMissile() {
        missiles.add(new Missile(startX, endX, amplitude, getHeight(), missileSpeed, explosionImage));
    }

    public void launchInterceptor() {
        int midX = (startX + endX) / 2;
        Interceptor interceptor = new Interceptor(midX, interceptorSpeed);
        for (Missile missile : missiles) {
            if (!missile.isDestroyed()) {
                interceptor.setTarget(missile);
                break;
            }
        }
        interceptors.add(interceptor);
    }

    private void checkCollisions() {
        int buildingX = endX - 35;
        int buildingY = getHeight() - 50;
        int buildingWidth = 30;
        int buildingHeight = 50;

        for (Interceptor interceptor : interceptors) {
            if (!interceptor.isDestroyed()) {
                if (interceptor.getX() >= buildingX && interceptor.getX() <= buildingX + buildingWidth && interceptor.getY() >= buildingY) {
                    interceptor.destroy();
                }
            }
        }
    }

    private boolean checkBuildingCollision(Missile missile) {
        int buildingX = endX - 25;
        int buildingY = 30;
        int buildingWidth = 30;
        int buildingHeight = 1;

        if (!missile.isDestroyed()) {
            if (missile.getX() >= buildingX && missile.getX() <= buildingX + buildingWidth && missile.getY() >= buildingHeight) {
                missile.destroy(missile.getY(), buildingX - 20);
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(Color.RED);
        for (int x = startX; x <= endX; x++) {
            int y = (int) (amplitude * (x - startX) * (endX - x));
            g.fillRect(x, getHeight() - y, 3, 3);
        }

        g.setColor(Color.BLACK);
        for (Missile missile : missiles) {
            if (!missile.isDestroyed()) {
                g.fillOval(missile.getX(), getHeight() - missile.getY(), 15, 15);
            } else if (missile.isExplosionVisible()) {
                g.drawImage(missile.getExplosionImage(), missile.getX() - 20, getHeight() - missile.getExplosionY() - 20, 60, 60, null);
            }
        }

        g.setColor(Color.BLUE);
        for (Interceptor interceptor : interceptors) {
            if (!interceptor.isDestroyed()) {
                g.fillOval(interceptor.getX(), getHeight() - interceptor.getY(), 10, 10);
            }
        }

        g.setColor(Color.GRAY);
        g.fillRect(endX - 30, getHeight() - 50, 30, 50);

        g.setColor(Color.BLACK);
        g.fillRect(startX - 20, getHeight() - 20, 30, 50);

        int midX = (startX + endX) / 2;
        g.setColor(Color.ORANGE);
        g.fillRect(midX - 15, getHeight() - 50, 30, 50);

        // Draw explosions last to ensure they appear in front of the buildings
        for (Missile missile : missiles) {
            if (missile.isExplosionVisible()) {
                g.drawImage(missile.getExplosionImage(), missile.getX() - 20, getHeight() - missile.getExplosionY() - 20, 60, 60, null);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    public void setMissileSpeed(int missileSpeed) {
        this.missileSpeed = missileSpeed;
        for (Missile missile : missiles) {
            missile.setSpeed(missileSpeed);
        }
    }

    public void setInterceptorSpeed(int interceptorSpeed) {
        this.interceptorSpeed = interceptorSpeed;
        for (Interceptor interceptor : interceptors) {
            interceptor.setSpeed(interceptorSpeed);
        }
    }
}
