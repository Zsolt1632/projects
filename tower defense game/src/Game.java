import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class Game extends JFrame {
    private Image offScreenImage;
    private int NrEnemiesPassed;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final List<Wall> walls;

    private final List<Tower> towers;
    private final List<Enemy> enemies;
    private final int maxEnemies;
    private  int maxTowers;
    private int coinBalance;
    private final Balance balance;
    private final Timer gameTimer;
    public Game(int maxEnemies) {
        walls = new ArrayList<>();
        createWalls();

        NrEnemiesPassed = maxEnemies/10;
        if(NrEnemiesPassed == 0){
            NrEnemiesPassed = 1;
        }

        coinBalance = maxEnemies* 3/10 *25;
        if(coinBalance < 51){
            coinBalance = 75;
        }

        JTextField text1, text2;
        setLayout(new BorderLayout());
        text1 = new JTextField("Money:");
        text2 = new JTextField(String.valueOf(coinBalance));
        balance = new Balance(text1, text2);
        add(balance, BorderLayout.SOUTH);

        this.maxEnemies = maxEnemies;
        this.maxTowers = maxEnemies/2;
        if(this.maxTowers < 1){
            this.maxTowers = 1;
        }

        setTitle("Desktop Tower Defense");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        towers = new ArrayList<>();
        enemies = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(coinBalance >= 25){
                    balance.setText3("");
                    placeTower(e.getX(), e.getY());
                    balance.setText2(String.valueOf(coinBalance));
                }
            }
        });

        gameTimer = new Timer(0, e -> {
            update();
            repaint();
        });
        gameTimer.start();
    }

    private void spawnEnemies() {
        Random random = new Random();
        if (random.nextInt(100) < 5 && enemies.size() < maxEnemies) { // 5% chance to spawn an enemy in each iteration
            enemies.add(new Enemy(0, random.nextInt(HEIGHT-50)+20, 20, 1, WIDTH));
        }
    }

    private void createWalls() {
        // Adjust these values based on your game's layout
        int wallWidth = 40;
        int wallHeight = 40;
        // Number of columns in the grid
        int rows = HEIGHT / wallHeight; // Number of rows in the grid

            for (int row = 0; row < rows; row++) {
                int x = WIDTH/3;
                int y = row * wallHeight;
                walls.add(new Wall(x, y, wallWidth, wallHeight, 500));
            }
            for (int row = 0; row < rows; row++) {
                int x = 2*WIDTH/3;
                int y = row * wallHeight;
                walls.add(new Wall(x, y, wallWidth, wallHeight, 500));
            }
    }
    private void update() {
        spawnEnemies();
        updateTowers();
        updateEnemies();
        checkCollisions();
        if(checkIfLost()){
            balance.setText3("Game Over!\tYou Lose!");
            towers.clear();
            enemies.clear();
            walls.clear();
            gameTimer.stop();
        }
    }


    private void updateTowers() {
        for (Tower tower : towers) {
            tower.update();
            tower.updateBullets(enemies);
        }
    }

    private void updateEnemies() {
        for (Enemy enemy : enemies) {
            boolean range = false;
            for(Wall wall : walls) {
                if(enemy.isInMeleeRange(wall)){
                    enemy.update(-1);
                    range = true;
                }
            }
            for(Tower tower:towers){
                if(enemy.isInMeleeRange(tower)){
                    enemy.update(-1);
                    range = true;
                }
            }
            if(!range){
                enemy.update(enemy.getSpeed());
            }
        }
    }

    private void checkCollisions() {
        for (Enemy enemy : enemies) {
            for (Wall wall : walls) {
                enemy.attack(wall);
            }
        }
        walls.removeIf(wall -> wall.getHealth() < 1);

        for (Tower tower : towers) {
            for (Enemy enemy : enemies) {
                if (tower.isInRange(enemy)) {
                    tower.attack(enemy);
                    if (enemy.getHealth() <= 0) {
                        if(coinBalance < 9999){
                            coinBalance += 5;
                        } else{
                            coinBalance = 9999;
                        }
                        balance.setText2(String.valueOf(coinBalance));
                    }
                }
            }
        }
        enemies.removeIf(enemy -> enemy.getHealth() < 1);

        for (Enemy enemy : enemies) {
            for (Tower tower : towers) {
                    enemy.attack(tower);
            }
        }
        towers.removeIf(tower -> tower.getHealth() < 1);
        enemies.removeIf(enemy -> enemy.getFinish() > WIDTH);
    }


    private boolean checkIfLost(){
        int count = 0;
        for(Enemy enemy : enemies){
            if(enemy.getX() > WIDTH){
                count++;
            }
        }
        return count >= NrEnemiesPassed;
    }

    private void placeTower(int x, int y) {
        if(towers.size() < maxTowers){
            balance.setText3("");
            towers.add(new Tower(x, y, 200, 100, 10, WIDTH)); // Adjust range and damage values as needed
            coinBalance -= 25;
        } else {
            balance.setText3("Can't place towers");
        }
    }

    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(WIDTH, HEIGHT-30);
        }

        Graphics offScreenGraphics = offScreenImage.getGraphics();
        offScreenGraphics.setColor(Color.BLACK);
        offScreenGraphics.fillRect(0, 0, WIDTH, HEIGHT);

        for (Tower tower : towers) {
            tower.draw(offScreenGraphics);
        }

        for (Tower tower : towers) {
            tower.drawBullets(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(offScreenGraphics);
        }

        for (Wall wall : walls) {
            wall.draw(offScreenGraphics);
        }

        g.drawImage(offScreenImage, 0, 0, this);
        balance.setText1("Money:");
        balance.setText2(String.valueOf(coinBalance));
    }


}
