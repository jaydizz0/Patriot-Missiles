import java.awt.Image;

public class Missile {
    private int startX;
    private int x;
    private int y;
    private int endX;
    private double amplitude;
    private int panelHeight;
    private boolean destroyed;
    private int speed;
    private Image explosionImage;
    private boolean explosionVisible;
    private int explosionTimer;
    private int explosionX;
    private int explosionY;

    public Missile(int startX, int endX, double amplitude, int panelHeight, int speed, Image explosionImage) {
        this.startX = startX;
        this.x = startX;
        this.y = 0;
        this.endX = endX;
        this.amplitude = amplitude;
        this.panelHeight = panelHeight;
        this.destroyed = false;
        this.speed = speed;
        this.explosionImage = explosionImage;
        this.explosionVisible = false;
        this.explosionTimer = 0;
    }

    public void updatePosition() {
        if (!destroyed && x <= endX) {
            y = (int) (amplitude * (x - startX) * (endX - x));
            x += speed;
            if (x > endX || y < 0 || y > panelHeight) {
                destroyed = true;
            }
        } else if (destroyed && explosionVisible) {
            explosionTimer++;
            if (explosionTimer > 10) {
                explosionVisible = false;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void destroy(int explosionY, int explosionX) {
        destroyed = true;
        explosionVisible = true;
        explosionTimer = 0;
        this.explosionX = x;
        this.explosionY = explosionY;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isExplosionVisible() {
        return explosionVisible;
    }

    public Image getExplosionImage() {
        return explosionImage;
    }

    public int getExplosionX() {
        return explosionX;
    }

    public int getExplosionY() {
        return explosionY;
    }

    public boolean isExplosionExpired() {
        return destroyed && !explosionVisible;
    }
}
