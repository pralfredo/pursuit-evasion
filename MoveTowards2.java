
/**
 * File: MoveTowards2.java
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
import java.util.Random;

public class MoveTowards2 extends AbstractPlayerAlgorithm {

    private int sameMoveCounter = 0; // The number of same moves
    private Vertex last = null; // The last vertex

    /**
     * Constructs a new MoveTowards2 object with the specified graph.
     *
     * @param graph the graph used for navigation.
     */
    public MoveTowards2(Graph graph) {
        super(graph);
    }

    /**
     * Chooses the vertex with the least number of edges as the starting vertex.
     *
     * @return the starting vertex.
     */
    public Vertex chooseStart() {
        // Choose the vertex with the least number of edges
        int minEdges = Integer.MAX_VALUE;
        Vertex start = null;

        for (Vertex v : graph.getVertices()) {
            int numEdges = v.degree();
            if (numEdges < minEdges) {
                minEdges = numEdges;
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
        // Choose a random vertex with minimum distance to the other player
        HashMap<Vertex, Double> minDistances = this.getGraph().distanceFrom(other);
        double minDistance = Double.POSITIVE_INFINITY;
        ArrayList<Vertex> candidates = new ArrayList<>();

        for (Vertex ver : this.getGraph().getVertices()) {
            Double distance = minDistances.get(ver);
            if (distance != null && distance < minDistance) {
                minDistance = distance;
                candidates.clear();
                candidates.add(ver);
            } else if (distance != null && distance == minDistance) {
                candidates.add(ver);
            }
        }

        Random r = new Random();
        Vertex toStart = candidates.get(r.nextInt(candidates.size()));

        this.setCurrentVertex(toStart);
        return toStart;
    }

    private ArrayList<Vertex> visitedVertices = new ArrayList<>();// the visited vertices

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
            return chooseNextWithLeastEdges();
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
     * Finds the vertex with the least edges
     * 
     * @return the vertex with the least edges
     */
    private Vertex chooseNextWithLeastEdges() {
        int minEdges = Integer.MAX_VALUE;
        Vertex next = null;
        for (Edge e : getCurrentVertex().incidentEdges()) {
            Vertex neighbor = e.getNeighbor(getCurrentVertex());
            int numEdges = neighbor.degree();
            if (numEdges < minEdges) {
                minEdges = numEdges;
                next = neighbor;
            }
        }
        setCurrentVertex(next);
        return next;
    }
}