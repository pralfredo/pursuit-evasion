
/**
 * File: MoveAwayPlayerAlgorithm.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: This represents a player that chooses its next move based on moving 
 * away from the other player. This algorithm chooses its starting vertex randomly 
 * or farthest from the other player if specified.
 */
import java.util.Random;

public class MoveAwayPlayerAlgorithm extends AbstractPlayerAlgorithm {

    /**
     * Constructs a MoveAwayPlayerAlgorithm object with the given Graph object.
     *
     * @param graph the graph that the player will play on.
     */
    public MoveAwayPlayerAlgorithm(Graph graph) {
        super(graph);
    }

    /**
     * Chooses a random vertex from the graph as the starting vertex for the player
     * and sets the current vertex.
     *
     * @return the randomly chosen vertex.
     */
    public Vertex chooseStart() {
        Random r = new Random();
        int rand = r.nextInt(this.getGraph().size());
        int counter = 0;
        Vertex toStart = null;
        for (Vertex ver : this.getGraph().getVertices()) {
            if (counter == rand) {
                toStart = ver;
                break;
            }
            counter++;
        }
        this.setCurrentVertex(toStart);
        return toStart;
    }

    /**
     * Chooses the farthest vertex from the other player as the starting vertex for
     * the player and sets the current vertex.
     *
     * @param other the other player's starting vertex.
     * @return the farthest vertex from the other player.
     */
    public Vertex chooseStart(Vertex other) {
        HashMap<Vertex, Double> minDistances = this.getGraph().distanceFrom(other);
        double farthest = -1.0;
        Vertex toStart = null;
        for (Vertex ver : this.getGraph().getVertices()) {
            if (minDistances.get(ver) != null && minDistances.get(ver) > farthest) {
                farthest = minDistances.get(ver);
                toStart = ver;
            }
        }
        this.setCurrentVertex(toStart);
        return toStart;
    }

    /**
     * Chooses the next vertex to move to based on moving away from the other
     * player.
     * Calculates the distances of all adjacent vertices from the other player's
     * vertex and chooses the farthest vertex.
     *
     * @param otherPlayer the other player's current vertex.
     * @return the chosen vertex to move to.
     */
    public Vertex chooseNext(Vertex otherPlayer) {
        HashMap<Vertex, Double> minDistances = this.getGraph().distanceFrom(otherPlayer);
        double farthest = minDistances.get(getCurrentVertex());
        Vertex next = getCurrentVertex();
        for (Vertex ver : this.getCurrentVertex().adjacentVertices()) {
            if (minDistances.get(ver) != null && minDistances.get(ver) > farthest) {
                farthest = minDistances.get(ver);
                next = ver;
            }
        }
        if (next == null) {
            next = this.getCurrentVertex();
        }
        this.setCurrentVertex(next);
        return next;
    }
}