import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tower {
    private final BufferedImage img;
    private AudioInputStream audioInputStream;
    private final int x;
    private final int y;
    private final int Width;
    private final int Height;
    private int health;
    private final int WIDTH;
    private final int range;
    private final int damage;
    private final int cooldown; // Time between shots
    private int cooldownTimer;
    private final List<Bullet> bullets;

    public Tower(int x, int y, int health, int range, int damage, int WIDTH) {
        try {
            img = ImageIO.read(new File("src/tower.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            //File URL relative to project folder
            audioInputStream  = AudioSystem.getAudioInputStream(new File("src/gunshot.wav"));

        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("IO error");
        }
        this.WIDTH = WIDTH;
        this.x = x;
        this.y = y;
        this.health = health;
        this.range = range;
        this.damage = damage;
        this.cooldown = 50; // Set the initial cooldown time
        this.cooldownTimer = 0;
        bullets = new ArrayList<>();
        Width = 30;
        Height = 30;
    }

    public void update() {
        if (cooldownTimer > 0) {
            cooldownTimer--;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(img, x-10, y-10, null);
    }

    public void attack(Enemy enemy) {
        if (cooldownTimer == 0 && isInRange(enemy)) {
            try{
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }

            enemy.takeDamage(damage);
            cooldownTimer = cooldown;

            Bullet bullet = new Bullet(x, y, 10, 5, 5, damage);
            bullets.add(bullet);
        }
    }

    public void updateBullets(List<Enemy> enemies) {
        for (Bullet bullet : bullets) {

            // Check for collisions with enemies
            for (Enemy enemy : enemies) {
                bullet.update(enemy);
                if (collisionDetected(bullet, enemy)) {
                    enemy.takeDamage(bullet.getDamage());
                    bullet.setX(WIDTH + 1); // Move the bullet out of bounds to mark it for removal
                }
            }
        }

        // Remove bullets that have gone out of bounds or hit their target
        bullets.removeIf(bullet -> bullet.getX() > WIDTH);
    }

    private boolean collisionDetected(Bullet bullet, Enemy enemy) {
        // Check if the bullet's rectangle intersects with the enemy's rectangle
        return bullet.getX() < enemy.getX() + enemy.getWidth() &&
                bullet.getX() + bullet.getWidth() > enemy.getX() &&
                bullet.getY() < enemy.getY() + enemy.getHeight() &&
                bullet.getY() + bullet.getHeight() > enemy.getY();
    }

    public void drawBullets(Graphics g) {
        // Draw each bullet
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    public boolean isInRange(Enemy enemy) {
        double distance = Math.sqrt(Math.pow(x - enemy.getX(), 2) + Math.pow(y - enemy.getY(), 2));
        return distance <= range;
    }

    public int getX() {
        return x;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getWidth() {
        return Width;
    }

    public int getHeight() {
        return Height;
    }
}