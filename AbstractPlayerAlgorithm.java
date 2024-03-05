
/**
 * File: AbstractPlayerAlgorithm.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: This abstract class represents the basic structure of a player algorithm for
 * a game played on a graph. The class contains a Graph object and a current vertex, 
 * which can be set and retrieved through getter and setter methods. The class also defines 
 * 3 abstract methods : chooseStart(), chooseStart(), and chooseNext().
 */

public abstract class AbstractPlayerAlgorithm {

    /**
     * 
     * The Graph object used in the game.
     */
    protected Graph graph;
    /**
     * 
     * The current vertex of the player.
     */
    protected Vertex current;

    /**
     * 
     * Constructs a new AbstractPlayerAlgorithm object with the specified Graph
     * object.
     * 
     * @param graph The Graph object used in the game.
     */
    public AbstractPlayerAlgorithm(Graph graph) {
        this.graph = graph;
        this.current = null;
    }

    /**
     * 
     * Retrieves the Graph object associated with this player algorithm.
     * 
     * @return The Graph object associated with this player algorithm.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * 
     * Retrieves the current vertex of the player.
     * 
     * @return The current vertex of the player.
     */
    public Vertex getCurrentVertex() {
        return current;
    }

    /**
     * 
     * Sets the current vertex of the player.
     * 
     * @param vertex The new current vertex of the player.
     */
    public void setCurrentVertex(Vertex vertex) {
        this.current = vertex;
    }

    /**
     * 
     * Abstract method that must be implemented by subclasses.
     * Defines the behavior of the algorithm when choosing the starting vertex for
     * the game.
     * 
     * @return The starting vertex chosen by the algorithm.
     */
    public abstract Vertex chooseStart();

    /**
     * 
     * Abstract method that must be implemented by subclasses.
     * Defines the behavior of the algorithm when choosing the starting vertex after
     * another player has already chosen one.
     * 
     * @param other The starting vertex chosen by the other player.
     * @return The starting vertex chosen by the algorithm.
     */
    public abstract Vertex chooseStart(Vertex other);

    /**
     * 
     * Abstract method that must be implemented by subclasses.
     * Defines the behavior of the algorithm when choosing the next vertex to move
     * to.
     * 
     * @param otherPlayer The current vertex of the other player.
     * @return The vertex chosen by the algorithm to move to.
     */
    public abstract Vertex chooseNext(Vertex otherPlayer);
}