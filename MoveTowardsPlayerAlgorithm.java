
/**
 * File: MoveTowardsPlayerAlgorithm.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: This represents a player that chooses its next move based on moving 
 * toward the other player. This algorithm chooses its starting vertex randomly 
 * or cloest to the other player if specified.
 */
import java.util.Random;

public class MoveTowardsPlayerAlgorithm extends AbstractPlayerAlgorithm{

    /**
     * Initializes a MoveTowardsPlayerAlgorithm using the given Graph.
     * @param graph the given Graph
     */
    public MoveTowardsPlayerAlgorithm(Graph graph){
        super(graph);
    }

    /**
     * Returns a random Vertex for the player to start at and updates the current Vertex to that location.
     */
    public Vertex chooseStart() {
        Random r = new Random();
        int rand = r.nextInt(this.getGraph().size());
        int counter = 0;
        Vertex toStart = null;
        for(Vertex ver : this.getGraph().getVertices()){
            if(counter == rand){
                toStart = ver;
                break;
            }
            counter++;
        }
        this.setCurrentVertex(toStart);
        return toStart;
    }

    /**
     * Returns a Vertex for the player to start at based on where the other player chose to start. Updates the current Vertex
     * to the chosen location.
     */
    public Vertex chooseStart(Vertex other) {
        HashMap<Vertex, Double> minDistances = this.getGraph().distanceFrom(other);
        double closest = Double.POSITIVE_INFINITY;
        Vertex toStart = null;
        for(Vertex ver : this.getGraph().getVertices()){
            if(minDistances.get(ver) != null && minDistances.get(ver) < closest){
                closest = minDistances.get(ver);
                toStart = ver;
            }
        }
        this.setCurrentVertex(toStart);
        return toStart;
    }

    /**
     * Returns an adjacent Vertex of the current Vertex that is closest to where the other player currently is. Updates the current
     * Vertext to the given next location.
     */
    public Vertex chooseNext(Vertex otherPlayer) {
        HashMap<Vertex, Double> minDistances = this.getGraph().distanceFrom(otherPlayer);
        double closest = Double.POSITIVE_INFINITY;
        Vertex next = null;
        for(Vertex ver : this.getCurrentVertex().adjacentVertices()){
            if(minDistances.get(ver) != null && minDistances.get(ver) < closest){
                closest = minDistances.get(ver);
                next = ver;
            }
        }
        if(next == null){
            next = this.getCurrentVertex();
        }
        this.setCurrentVertex(next);
        return next;
    }
}