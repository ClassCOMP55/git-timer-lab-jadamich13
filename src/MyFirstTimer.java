import acm.graphics.*;
import acm.program.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class MyFirstTimer extends GraphicsProgram implements ActionListener {
    public static final int PROGRAM_HEIGHT = 600;
    public static final int PROGRAM_WIDTH = 800;

    private GLabel myLabel;
    private int numTimes;
    private Timer t;

    public void init() {
        setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
        requestFocus();
    }

    public void run() {
        myLabel = new GLabel("times called? 0", 0, 100);
        add(myLabel);

        numTimes = 0;

        // Timer fires every 1000 ms (1 second) and calls actionPerformed on "this"
        t = new Timer(1000, this);

        // Exercise: 3 second delay before it starts firing
        t.setInitialDelay(3000);

        // Start the timer
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        numTimes++;

        // Move label 5 pixels right each time
        myLabel.move(5, 0);

        // Update label text
        myLabel.setLabel("times called? " + numTimes);

        // Exercise: stop when numTimes == 10
        if (numTimes == 10) {
            t.stop();
        }
    }

    public static void main(String[] args) {
        new MyFirstTimer().start();
    }
}