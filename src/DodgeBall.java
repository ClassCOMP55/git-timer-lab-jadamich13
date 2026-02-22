import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class DodgeBall extends GraphicsProgram implements ActionListener {
    private ArrayList<GOval> balls;
    private ArrayList<GRect> enemies;
    private GLabel text;
    private Timer movement;
    private RandomGenerator rgen;

    private int numTimes; // <-- add this

    public static final int SIZE = 25;
    public static final int SPEED = 2;
    public static final int MS = 50;
    public static final int MAX_ENEMIES = 10;
    public static final int WINDOW_HEIGHT = 600;
    public static final int WINDOW_WIDTH = 300;

    public void run() {
        rgen = RandomGenerator.getInstance();
        balls = new ArrayList<GOval>();
        enemies = new ArrayList<GRect>();

        numTimes = 0; // <-- initialize in run()

        text = new GLabel("" + enemies.size(), 0, WINDOW_HEIGHT - 5);
        add(text);

        movement = new Timer(MS, this);
        movement.start();
        addMouseListeners();
    }

    public void actionPerformed(ActionEvent e) {
        numTimes++;

        moveAllBallsOnce();
        moveAllEnemiesOnce();
        checkForCollisions();

        if (numTimes % 40 == 0 && enemies.size() < MAX_ENEMIES) {
            addAnEnemy();
        }
    }

    public void mousePressed(MouseEvent e) {
        for (GOval b : balls) {
            if (b.getX() < SIZE * 2.5) {
                return;
            }
        }
        addABall(e.getY());
    }

    private void addABall(double y) {
        GOval ball = makeBall(SIZE / 2, y);
        add(ball);
        balls.add(ball);
    }

    public GOval makeBall(double x, double y) {
        GOval temp = new GOval(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
        temp.setColor(Color.RED);
        temp.setFilled(true);
        return temp;
    }

    private void addAnEnemy() {
        GRect e = makeEnemy(rgen.nextInt(SIZE / 2, WINDOW_HEIGHT - SIZE / 2));
        enemies.add(e);
        text.setLabel("" + enemies.size());
        add(e);
    }

    public GRect makeEnemy(double y) {
        // right side of the window
        GRect temp = new GRect(WINDOW_WIDTH - SIZE, y - SIZE / 2, SIZE, SIZE);
        temp.setColor(Color.GREEN);
        temp.setFilled(true);
        return temp;
    }

    private void moveAllBallsOnce() {
        for (GOval ball : balls) {
            ball.move(SPEED, 0);
        }
    }

    private void moveAllEnemiesOnce() {
        for (GRect enemy : enemies) {
            enemy.move(0, rgen.nextInt(-2, 2));
        }
    }
    
    private void checkForCollisions() {
        // For each ball, check several points around it
        for (GOval ball : balls) {
            removeEnemyIfHitAt(ball.getX() + SIZE,     ball.getY() + SIZE / 2); // right middle (front)
            removeEnemyIfHitAt(ball.getX() + SIZE,     ball.getY() + 2);        // right top
            removeEnemyIfHitAt(ball.getX() + SIZE,     ball.getY() + SIZE - 2); // right bottom

            removeEnemyIfHitAt(ball.getX() + SIZE / 2, ball.getY() + 2);        // top middle
            removeEnemyIfHitAt(ball.getX() + SIZE / 2, ball.getY() + SIZE - 2); // bottom middle

            removeEnemyIfHitAt(ball.getX() + 2,        ball.getY() + SIZE / 2); // left middle
            removeEnemyIfHitAt(ball.getX() + 2,        ball.getY() + 2);        // top-left corner
            removeEnemyIfHitAt(ball.getX() + 2,        ball.getY() + SIZE - 2); // bottom-left corner
            removeEnemyIfHitAt(ball.getX() + SIZE - 2, ball.getY() + 2);        // top-right corner
            removeEnemyIfHitAt(ball.getX() + SIZE - 2, ball.getY() + SIZE - 2); // bottom-right corner
        }
    }
    
    private void removeEnemyIfHitAt(double x, double y) {
        GObject obj = getElementAt(x, y);

        if (obj instanceof GRect) {
            GRect enemy = (GRect) obj;

            // Only remove if it’s one of OUR enemies (not any random GRect)
            if (enemies.contains(enemy)) {
                remove(enemy);
                enemies.remove(enemy);
                text.setLabel("" + enemies.size());
            }
        }
    }
    
    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public static void main(String args[]) {
        new DodgeBall().start();
    }
}
