import processing.core.*;

public class GUI extends PApplet {
    private Simulator sim;
    private DisplayWindow gridDisplay;

    public void settings() {
        size(640, 550); // set the size of the screen.
    }

    public void setup() {
        // Create a simulator
        sim = new Simulator(100, 100);
        sim.initialize( 50 );
        sim.setFire();

        // Create the display
        // parameters: (10,10) is upper left of display
        // (620, 530) is the width and height
        gridDisplay = new DisplayWindow(this, 10, 10, 620, 530);

        gridDisplay.setNumCols(sim.getWidth());       // NOTE:  these must match your simulator!!
        gridDisplay.setNumRows(sim.getHeight());

        // Set different grid values to different colors
        gridDisplay.setColor(Simulator.BURNING_TREE, color(200, 0, 0));
        gridDisplay.setColor(Simulator.ASH, color(180, 180, 180));
        gridDisplay.setColor(Simulator.LIVING_TREE, color(0, 180, 0));
        gridDisplay.setColor(Simulator.EMPTY_SPACE, color(255, 255, 255));
    }

    @Override
    public void draw() {
        background(200);
        gridDisplay.drawGrid(sim.getDisplayGrid()); // display the game
        sim.runOneStep();
        sim.displayStats();
        if ( sim.isOver() ) {
            delay(2000);
            exit();
        } else {
            delay(100);
        }
    }

    public static void main(String[] args) {
        PApplet.main("GUI");
    }
}
