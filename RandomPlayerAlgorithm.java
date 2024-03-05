
/**
 * File: RandomPlayerAlgorithm.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: The RandomPlayerAlgorithm class is an implementation of the
 * AbstractPlayerAlgorithm that randomly selects start & next vertex.
 */
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayerAlgorithm extends AbstractPlayerAlgorithm {

    /**
     * 
     * Constructs a new RandomPlayerAlgorithm object for a given graph.
     * 
     * @param graph The graph to play on.
     */
    public RandomPlayerAlgorithm(Graph graph) {
        super(graph);
    }

    /**
     * 
     * Chooses a random starting vertex for the player.
     * 
     * @return The chosen starting vertex.
     */
    @Override
    public Vertex chooseStart() {
        Random random = new Random();
        Vertex start = ((ArrayList<Vertex>) getGraph().getVertices()).get(random.nextInt(getGraph().size()));
        setCurrentVertex(start);
        return start;
    }

    /**
     * 
     * Chooses a random starting vertex for the player, ignoring the other player's
     * starting vertex.
     * 
     * @param other Ignored.
     * @return The chosen starting vertex.
     */
    @Override
    public Vertex chooseStart(Vertex other) {
        return chooseStart();
    }

    /**
     * 
     * Chooses a random neighbor of the current vertex as the next vertex to move
     * to.
     * 
     * @param otherPlayer The other player's current vertex.
     * @return The chosen next vertex.
     */
    @Override
    public Vertex chooseNext(Vertex otherPlayer) {
        Random random = new Random();
        ArrayList<Vertex> neighbors = (ArrayList<Vertex>) getCurrentVertex().adjacentVertices();
        Vertex next = neighbors.get(random.nextInt(neighbors.size()));
        setCurrentVertex(next);
        return next;
    }
}