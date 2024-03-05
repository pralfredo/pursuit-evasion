
/**
 * File: Driver.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: The purpose of the Driver class is to implement the main logic of the
 * Pursuit/Evasion game by creating a random graph, creating a pursuer and
 * evader using different algorithms, making each player choose a starting
 * location, creating a display to visualize the game, and playing the game
 * until the pursuer captures the evader.
 * @extension to accomodate new ExtendedDiplay
 */
public class Driver {
    // int count = 0;

    /**
     * The Driver for the whole Pursuit/Evasion
     * 
     * @param n
     * @param p
     * @throws InterruptedException
     */
    public Driver(int n, double p) throws InterruptedException {
        // Create a random graph on which to play
        Graph graph = new Graph(n, p);

        // Create the pursuer and evader
        AbstractPlayerAlgorithm pursuer = new MoveTowards3(graph);
        AbstractPlayerAlgorithm evader = new MoveAwayPlayerAlgorithm(graph);

        // Have each player choose a starting location
        pursuer.chooseStart();
        // Since the evader has a harder objective, they get to play second
        // and see where the pursuer chose
        evader.chooseStart(pursuer.getCurrentVertex());

        // Make the display
        ExtendedDisplay display = new ExtendedDisplay(graph, pursuer, evader, 40);
        display.repaint();

        // Play the game until the pursuer captures the evader
        while (pursuer.getCurrentVertex() != evader.getCurrentVertex()) {
            // count ++;
            Thread.sleep(50);
            Vertex pMoveFrom = pursuer.getCurrentVertex();
            Vertex eMoveFrom = evader.getCurrentVertex();
            pursuer.chooseNext(evader.getCurrentVertex());
            // display.repaint();
            if (pursuer.getCurrentVertex() != evader.getCurrentVertex()) {
                // Thread.sleep(200);
                evader.chooseNext(pursuer.getCurrentVertex());
                // display.repaint();
            }
            display.drawPath(pursuer.getCurrentVertex().getEdgeTo(pMoveFrom),
                    evader.getCurrentVertex().getEdgeTo(eMoveFrom));
            display.animateMoving(200, pMoveFrom, eMoveFrom);
        }
    }

    /**
     * The main method for the Driver class
     * 
     * @param args The arguments
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int n = 20;
        double p = 0.3;
        new Driver(n, p);
    }
}