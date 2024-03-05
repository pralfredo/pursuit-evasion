
/**
 * File: Vertex.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: It represents a vertex in a graph. It also has methods which are 
 * useful for manipulating graphs and implementing algorithms that operate on graphs.
 */
import java.util.ArrayList;

public class Vertex {

    private ArrayList<Edge> edges; // the edges

    /**
     * 
     * Constructs a new vertex.
     */
    public Vertex() {
        edges = new ArrayList<>();
    }

    /**
     * 
     * Returns the edge between this vertex and the given vertex.
     * 
     * @param vertex the vertex to which the edge is connected
     * @return the edge between this vertex and the given vertex, or null if there
     *         is no edge between them
     */
    public Edge getEdgeTo(Vertex vertex) {
        for (Edge edge : edges) {
            if (edge.other(this).equals(vertex)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * 
     * Adds the given edge to the list of edges incident to this vertex.
     * 
     * @param edge the edge to add
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * 
     * Removes the given edge from the list of edges incident to this vertex.
     * 
     * @param edge the edge to remove
     * @return true if the edge was successfully removed, false otherwise
     */
    public boolean removeEdge(Edge edge) {
        for (Edge otherEdge : edges) {
            if (otherEdge.equals(edge)) {
                edges.remove(otherEdge);
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * Returns a list of vertices adjacent to this vertex (i.e., the vertices that
     * are connected to this vertex by an edge).
     * 
     * @return a list of vertices adjacent to this vertex
     */
    public ArrayList<Vertex> adjacentVertices() {
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex vertex = edge.other(this);
            vertices.add(vertex);
        }
        return vertices;
    }

    /**
     * 
     * Returns a list of edges incident to this vertex.
     * 
     * @return a list of edges incident to this vertex
     */
    public ArrayList<Edge> incidentEdges() {
        return edges;
    }

    /**
     * 
     * Returns the degree of this vertex (i.e., the number of edges incident to it).
     * 
     * @return the degree of this vertex
     */
    public int degree() {
        return edges.size();
    }
}