import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
    private final BufferedImage img;
    private int x;
    private final int y;
    private final int WIDTH;
    private final  int HEIGHT;
    private final int finish;
    private int health;
    private final int Speed;
    private AudioInputStream audioInputStream;

    public Enemy(int x, int y, int health, int speed, int finish) {
        try {
            img = ImageIO.read(new File("src/enemy.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            //File URL relative to project folder
            audioInputStream = AudioSystem.getAudioInputStream(new File("src/hit.wav"));

        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("IO error");
        }
        this.finish = finish;
        this.x = x;
        this.y = y;
        this.health = health;
        this.Speed = speed;
        WIDTH = 20;
        HEIGHT = 20;
    }

    public void update(int speed) {
        x+= speed;
    }
    public void attack(Tower tower) {
        if (isInMeleeRange(tower)) {
            try{
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
            tower.takeDamage(10);// You can adjust the damage value as needed
        }
    }
    public boolean isInMeleeRange(Tower tower) {
        double enemyLeft = x;
        double enemyRight = x + getWidth();  // Assuming getWidth() returns the width of the enemy
        double enemyTop = y;
        double enemyBottom = y + getHeight();  // Assuming getHeight() returns the height of the enemy

        double towerLeft = tower.getX();
        double towerRight = tower.getX() + tower.getWidth();  // Assuming getWidth() returns the width of the tower
        double towerTop = tower.getY();
        double towerBottom = tower.getY() + tower.getHeight();  // Assuming getHeight() returns the height of the tower

        // Check for collision by checking if the enemy's bounding box intersects with the tower's bounding box
        return (enemyRight > towerLeft && enemyLeft < towerRight && enemyBottom > towerTop && enemyTop < towerBottom);
    }

    public void draw(Graphics g) {
        g.drawImage(img, x-10, y-10, null);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }


    public void attack(Wall wall) {
        if(isInMeleeRange(wall)) {
            try{
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
            wall.takeDamage(10);// You can adjust the damage value as needed
        }
    }

    public boolean isInMeleeRange(Wall wall) {
        double enemyLeft = x;
        double enemyRight = x + getWidth();  // Assuming getWidth() returns the width of the enemy
        double enemyTop = y;
        double enemyBottom = y + getHeight();  // Assuming getHeight() returns the height of the enemy

        double wallLeft = wall.getX();
        double wallRight = wall.getX() + wall.getWidth();
        double wallTop = wall.getY();
        double wallBottom = wall.getY() + wall.getHeight();

        // Check for collision by checking if the enemy's bounding box intersects with the wall's bounding box
        return (enemyRight > wallLeft && enemyLeft < wallRight && enemyBottom > wallTop && enemyTop < wallBottom);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed(){
        return Speed;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getFinish() {
        return finish;
    }
}
