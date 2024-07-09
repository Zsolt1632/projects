import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int damage;

    public Bullet(int x, int y, int width, int height, int speed, int damage) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
        // You can customize the appearance of the bullet based on your game's design
    }

    public void update(Enemy targetEnemy) {
        // Check if the target enemy is still alive
        if (targetEnemy.getHealth() > 0) {
            // Calculate the direction from the current position to the target enemy's position
            double directionX = targetEnemy.getX() - x;
            double directionY = targetEnemy.getY() - y;

            // Calculate the magnitude (length) of the direction vector
            double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);

            // Normalize the direction vector (make it a unit vector)
            if (magnitude > 0) {
                directionX /= magnitude;
                directionY /= magnitude;
            }

            // Move the bullet towards the target enemy based on the normalized direction and speed
            x += (int) (directionX * speed);
            y += (int) (directionY * speed);
        } else {
            // Handle the case where the target enemy is no longer valid (e.g., it's dead)
            // This might involve marking the bullet for removal or other actions
        }
    }


    // Getters and setters (if needed)

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDamage() {
        return damage;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}