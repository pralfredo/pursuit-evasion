
/**
 * File: Edge.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: This class represents an edge in a graph. An edge 
 * connects two vertices and has a distance value associated with it.
 */
public class Edge {

    private Vertex firstVertex; // One of the vertices the edge connects

    private Vertex secondVertex; // The other vertex the edge connects

    private double distance; // The distance between the two vertices

    /**
     * 
     * Constructs an edge with two vertices and a distance value.
     * 
     * @param u        the first vertex that the edge connects
     * @param v        the second vertex that the edge connects
     * @param distance the distance between the two vertices
     */
    public Edge(Vertex u, Vertex v, double distance) {
        this.firstVertex = u;
        this.secondVertex = v;
        this.distance = distance;
    }

    /**
     * 
     * Returns the distance value of the edge.
     * 
     * @return the distance value of the edge
     */
    public double distance() {
        return distance;
    }

    /**
     * 
     * Returns the vertex connected to the given vertex by the edge.
     * 
     * @param vertex the vertex to find the neighbor of
     * @return the vertex connected to the given vertex by the edge
     */
    public Vertex other(Vertex vertex) {
        if (vertex.equals(firstVertex)) {
            return secondVertex;
        } else {
            return firstVertex;
        }
    }

    /**
     * 
     * Returns an array of the two vertices connected by the edge.
     * 
     * @return an array of the two vertices connected by the edge
     */
    public Vertex[] vertices() {
        Vertex[] vertices = new Vertex[2];
        vertices[0] = firstVertex;
        vertices[1] = secondVertex;
        return vertices;
    }

    /**
     * 
     * Returns the neighbor vertex of the given vertex connected by the edge.
     * 
     * @param v the vertex to find the neighbor of
     * @return the neighbor vertex of the given vertex connected by the edge
     */
    public Vertex getNeighbor(Vertex v) {
        if (v.equals(this.firstVertex)) {
            return this.secondVertex;
        } else {
            return this.firstVertex;
        }
    }
}