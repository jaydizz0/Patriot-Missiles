import java.util.List;

public class Interceptor {
    private int startX;
    private int x;
    private int y;
    private Missile target;
    private int speed;
    private boolean destroyed = false;
    private int panelHeight;

    public Interceptor(int startX, int speed) {
        this.startX = startX;
        this.x = startX;
        this.y = 0;
        this.speed = speed;
    }

    public void setTarget(Missile target) {
        this.target = target;
    }

    public void updatePosition(List<Missile> missiles, int panelHeight) {
        this.panelHeight = panelHeight;
        if (!destroyed) {
            if (target == null || target.isDestroyed()) {
                for (Missile missile : missiles) {
                    if (!missile.isDestroyed()) {
                        target = missile;
                        break;
                    }
                }
                if (target == null || target.isDestroyed()) {
                    this.destroyed = true;
                    return;
                }
            }

            int targetX = target.getX();
            int targetY = target.getY();

            if (x < targetX) {
                x += speed;
            } else if (x > targetX) {
                x -= speed;
            }

            if (y < targetY) {
                y += speed;
            } else if (y > targetY) {
                y -= speed;
            }

            if (Math.abs(x - targetX) < speed && Math.abs(y - targetY) < speed) {
                target.destroy(target.getY(), targetX);
                this.destroyed = true;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void destroy() {
        this.destroyed = true;
    }
}
