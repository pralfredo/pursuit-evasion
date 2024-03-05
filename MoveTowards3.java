
/**
 * File: MoveTowards3.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * @extension
 * Purpose: This class represents a player algorithm that moves towards another player 
 * and triggers a different behavior if it has visited the same vertex 4 times in a row.
 */
import java.util.ArrayList;

public class MoveTowards3 extends AbstractPlayerAlgorithm {

    private int sameMoveCounter = 0; // The number of same moves
    private Vertex last = null; // The last vertex

    /**
     * Constructs a new MoveTowards3 object with the specified graph.
     *
     * @param graph the graph used for navigation.
     */
    public MoveTowards3(Graph graph) {
        super(graph);
    }

    /**
     * Chooses the vertex with the maximum number of edges as the starting vertex.
     *
     * @return the starting vertex.
     */
    public Vertex chooseStart() {
        int maxEdges = Integer.MIN_VALUE;
        Vertex start = null;
        for (Vertex v : graph.getVertices()) {
            int numEdges = v.degree();
            if (numEdges > maxEdges) {
                maxEdges = numEdges;
                start = v;
            }
        }
        this.setCurrentVertex(start);
        return start;
    }

    /**
     * Chooses a starting vertex closest to another player.
     *
     * @param other the other player.
     * @return the starting vertex.
     */
    public Vertex chooseStart(Vertex other) {
        HashMap<Vertex, Double> minDistances = this.getGraph().distanceFrom(other);
        double closest = Double.POSITIVE_INFINITY;
        Vertex toStart = null;
        for (Vertex ver : this.getGraph().getVertices()) {
            if (minDistances.get(ver) != null && minDistances.get(ver) < closest) {
                closest = minDistances.get(ver);
                toStart = ver;
            }
        }
        this.setCurrentVertex(toStart);
        return toStart;
    }

    private ArrayList<Vertex> visitedVertices = new ArrayList<>(); // the visited vertices

    /**
     * Chooses the next vertex to move to. If the player has visited the same vertex
     * 4 times in a row, it triggers a different behavior.
     *
     * @param otherPlayer the other player.
     * @return the next vertex to move to.
     */
    public Vertex chooseNext(Vertex otherPlayer) {
        if (sameMoveCounter >= 4) {
            sameMoveCounter = 0;
            visitedVertices.clear(); // clear the list of visited vertices
            return chooseNextWithMostConnections();
        } else {
            Vertex next = chooseNextTowardsPlayer(otherPlayer);
            visitedVertices.add(next); // add the next vertex to the list of visited vertices
            System.out
                    .println("Repeated Move Counter: " + visitedVertices.stream().filter(v -> v.equals(next)).count());
            if (visitedVertices.stream().filter(v -> v.equals(next)).count() == 4) {
                sameMoveCounter = 4; // trigger the move counter if the player has visited the same vertex 6 times
            }
            if (next.equals(last)) {
                sameMoveCounter++;
            } else {
                last = next;
            }
            return next;
        }
    }

    /**
     * Chooses the next vertex towards another player.
     *
     * @param otherPlayer the other player.
     * @return the next vertex towards the other player.
     */
    private Vertex chooseNextTowardsPlayer(Vertex otherPlayer) {
        HashMap<Vertex, Double> minDistances = this.getGraph().distanceFrom(otherPlayer);
        double closest = Double.POSITIVE_INFINITY;
        Vertex next = null;
        for (Vertex ver : this.getCurrentVertex().adjacentVertices()) {
            if (minDistances.get(ver) != null && minDistances.get(ver) < closest) {
                closest = minDistances.get(ver);
                next = ver;

            }
        }
        if (next == null) {
            next = this.getCurrentVertex();
        }
        this.setCurrentVertex(next);
        return next;

    }

    /**
     * Finds the vertex with the most connections
     * 
     * @return the vertex with the most connections
     */
    private Vertex chooseNextWithMostConnections() {
        int maxConnections = 0;
        Vertex next = null;
        for (Vertex ver : this.getCurrentVertex().adjacentVertices()) {
            if (ver.adjacentVertices().size() > maxConnections) {
                maxConnections = ver.adjacentVertices().size();
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