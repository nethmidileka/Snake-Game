import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JFrame implements ActionListener, KeyListener {

    private static final int TILE_SIZE = 20;
    private static final int GRID_SIZE = 20;

    private LinkedList<Point> snake;
    private Point food;
    private int direction; // 0: up, 1: right, 2: down, 3: left

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snake = new LinkedList<>();
        snake.add(new Point(GRID_SIZE / 2, GRID_SIZE / 2));
        direction = 1; // start moving to the right

        spawnFood();

        Timer timer = new Timer(200, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    private void spawnFood() {
        int x, y;
        do {
            x = new Random().nextInt(GRID_SIZE);
            y = new Random().nextInt(GRID_SIZE);
        } while (snake.contains(new Point(x, y)));

        food = new Point(x, y);
    }

    private void move() {
        Point head = snake.getFirst();
        Point newHead = new Point(head);

        switch (direction) {
            case 0: // up
                newHead.y--;
                break;
            case 1: // right
                newHead.x++;
                break;
            case 2: // down
                newHead.y++;
                break;
            case 3: // left
                newHead.x--;
                break;
        }

        // Check for collision with walls or itself
        if (newHead.x < 0 || newHead.x >= GRID_SIZE || newHead.y < 0 || newHead.y >= GRID_SIZE || snake.contains(newHead)) {
            JOptionPane.showMessageDialog(this, "Game Over!");
            System.exit(0);
        }

        // Check for food
        if (newHead.equals(food)) {
            snake.addFirst(food);
            spawnFood();
        } else {
            snake.addFirst(newHead);
            snake.removeLast();
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Draw snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * TILE_SIZE, point.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw food
        g.setColor(Color.RED);
        g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_UP) && (direction != 2)) {
            direction = 0;
        } else if ((key == KeyEvent.VK_RIGHT) && (direction != 3)) {
            direction = 1;
        } else if ((key == KeyEvent.VK_DOWN) && (direction != 0)) {
            direction = 2;
        } else if ((key == KeyEvent.VK_LEFT) && (direction != 1)) {
            direction = 3;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeGame().setVisible(true));
    }
}
