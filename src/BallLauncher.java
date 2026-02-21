import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class BallLauncher extends GraphicsProgram implements ActionListener {

    public static final int PROGRAM_HEIGHT = 600;
    public static final int PROGRAM_WIDTH = 800;
    public static final int SIZE = 25;

    // Step 4: Instance constants
    private static final int MS = 50;
    private static final int SPEED = 2;

    // Step 1: ArrayList instance variable
    private ArrayList<GOval> balls;

    private Timer t;

    public void init() {
        setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
        requestFocus();
    }

    public void run() {
        addMouseListeners();

        // Step 2: Initialize list
        balls = new ArrayList<GOval>();

        // Step 7: Timer setup
        t = new Timer(MS, this);
        t.start();
    }

    public void mousePressed(MouseEvent e) {
        GOval ball = makeBall(e.getX(), e.getY());
        add(ball);
    }

    public GOval makeBall(double x, double y) {
        GOval temp = new GOval(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
        temp.setColor(Color.RED);
        temp.setFilled(true);

        // Step 3: Add ball to list
        balls.add(temp);

        return temp;
    }

    // Step 8: Move every ball each timer tick
    public void actionPerformed(ActionEvent e) {
        for (GOval ball : balls) {
            ball.move(SPEED, 0);
        }
    }

    public static void main(String[] args) {
        new BallLauncher().start();
    }
}