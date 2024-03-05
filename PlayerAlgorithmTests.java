
/**
 * File: PlayerAlgorithmTests.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: This class provides tests for RandomPlayer, MoveTowardsPlayerAlgorithm,
 * and MoveAwayPlayerAlgorithm (all which, in turn, test AbstractPlayerAlgorithm as well).
 */
public class PlayerAlgorithmTests {
    public static void main(String args[]) throws InterruptedException {

        // RandomPlayer Tests
        {
            Graph g = new Graph();
            Vertex[] vertices = new Vertex[7];
            for (int i = 0; i < 7; i++) {
                vertices[i] = g.addVertex();
            }
            for (int i = 0; i < 7; i++) {
                g.addEdge(vertices[i], vertices[(i + 1) % 7], 1);
            }
            g.addEdge(vertices[0], vertices[2], 1);
            g.addEdge(vertices[0], vertices[3], 1);
            g.addEdge(vertices[3], vertices[6], 1);
            g.addEdge(vertices[4], vertices[6], 1);

            AbstractPlayerAlgorithm pursuer = new RandomPlayerAlgorithm(g);
            AbstractPlayerAlgorithm evader = new RandomPlayerAlgorithm(g);
            pursuer.chooseStart();
            evader.chooseStart(pursuer.getCurrentVertex());
            GraphDisplay gd = new GraphDisplay(g, pursuer, evader, 80);
            while (pursuer.getCurrentVertex() != evader.getCurrentVertex()) {
                Thread.sleep(300);
                pursuer.chooseNext(evader.getCurrentVertex());
                gd.repaint();
                if (pursuer.getCurrentVertex() == evader.getCurrentVertex())
                    break;
                Thread.sleep(300);
                evader.chooseNext(pursuer.getCurrentVertex());
                gd.repaint();
            }
            System.out.println("Done testing the Random Algorithm");
        }

        // MoveTowardsPlayerAlgorithm and MoveAwayPlayerAlgorithm Tests
        {
            Graph g = new Graph();
            Vertex[] vertices = new Vertex[7];
            for (int i = 0; i < 7; i++) {
                vertices[i] = g.addVertex();
            }
            for (int i = 0; i < 7; i++) {
                g.addEdge(vertices[i], vertices[(i + 1) % 7], 1);
            }
            g.addEdge(vertices[0], vertices[2], 1);
            g.addEdge(vertices[0], vertices[3], 1);
            g.addEdge(vertices[3], vertices[6], 1);
            g.addEdge(vertices[4], vertices[6], 1);
            AbstractPlayerAlgorithm pursuer = new MoveTowardsPlayerAlgorithm(g);
            AbstractPlayerAlgorithm evader = new MoveAwayPlayerAlgorithm(g);
            pursuer.chooseStart();
            evader.chooseStart(pursuer.getCurrentVertex());
            GraphDisplay gd = new GraphDisplay(g, pursuer, evader, 80);
            while (pursuer.getCurrentVertex() != evader.getCurrentVertex()) {
                Thread.sleep(300);
                pursuer.chooseNext(evader.getCurrentVertex());
                gd.repaint();
                if (pursuer.getCurrentVertex() == evader.getCurrentVertex())
                    break;
                Thread.sleep(300);
                evader.chooseNext(pursuer.getCurrentVertex());
                gd.repaint();
            }
            assert pursuer.getCurrentVertex() == evader.getCurrentVertex()
                    : "Error in MoveTowardsPlayerAlgorithm or in MoveAwayPlayerAlgorithm";
            System.out.println("Done testing the MoveAway/Towards Algorithms");
        }
        System.out.println("Done testing the player algorithms!!!");
    }
}