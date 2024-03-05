
/**
 * File: Graph.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: A class representing an undirected graph, which is a collection 
 * of vertices and edges. The graph is implemented using an arrarylist.
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Graph {

    private ArrayList<Vertex> vertices; // list of vertices in the graph
    private ArrayList<Edge> edges; // list of edges in the graph

    /**
     * 
     * Constructs an empty graph.
     */
    public Graph() {
        this(0);
    }

    /**
     * 
     * Constructs a graph with n vertices and no edges.
     * 
     * @param n the number of vertices in the graph
     */
    public Graph(int n) {
        this(n, 0.0);
    }

    /**
     * 
     * Constructs a graph with n vertices and edges between each pair of vertices
     * with the specified probability.
     * 
     * @param n           the number of vertices in the graph
     * @param probability the probability that an edge is added between each pair of
     *                    vertices
     */
    public Graph(int n, double probability) {
        Random random = new Random();
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Vertex vertex = addVertex();
            for (Vertex otherVertex : vertices) {
                if (random.nextDouble() < probability) {
                    addEdge(vertex, otherVertex, 1);
                }
            }
        }
    }

    /**
     * 
     * Returns the number of vertices in the graph.
     * 
     * @return the number of vertices in the graph
     */
    public int size() {
        return vertices.size();
    }

    /**
     * 
     * Returns a list of the vertices in the graph.
     * 
     * @return a list of the vertices in the graph
     */
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    /**
     * 
     * Returns a list of the edges in the graph.
     * 
     * @return a list of the edges in the graph
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * 
     * Adds a new vertex to the graph.
     * 
     * @return the newly added vertex
     */
    public Vertex addVertex() {
        Vertex vertex = new Vertex();
        vertices.add(vertex);
        return vertex;
    }

    /**
     * Adds the edge to the list of edges in the graph
     * 
     * @return the edge being added in the list
     */
    public Edge addEdge(Vertex u, Vertex v, double distance) {
        Edge edge = new Edge(u, v, distance);
        u.addEdge(edge);
        v.addEdge(edge);
        edges.add(edge);
        return edge;
    }

    /**
     * Gets the edge connecting the two vertices
     * 
     * @param u the first vertex
     * @param v the second vertex
     * @return the edge that connects the first and second vertex and returns null
     *         if no edge connects the vertices
     */
    public Edge getEdge(Vertex u, Vertex v) {
        Edge edge = u.getEdgeTo(v);
        return edge;
    }

    /**
     * To remove a Vertex
     * @param vertex the vertex to be removed
     * @return the removed vertex
     */
    public boolean remove(Vertex vertex) {
        for (Vertex otherVertex : vertices) {
            if (otherVertex.equals(vertex)) {
                vertices.remove(otherVertex);
                ArrayList<Vertex> neighbors = (ArrayList<Vertex>) otherVertex.adjacentVertices();
                for (Vertex neighbor : neighbors) {
                    Edge edge = otherVertex.getEdgeTo(neighbor);
                    boolean removeStatus = neighbor.removeEdge(edge);
                    return removeStatus;
                }
            }
        }
        return false;
    }

    /**
     * Removes the edge from the list of edges in the graph
     * 
     * @param edge the edge to be removed from the graph
     * @return true if edge was found and removed successfully or else returns false
     */
    public boolean remove(Edge edge) {

        Vertex[] vertices = edge.vertices();
        vertices[0].removeEdge(edge);
        vertices[1].removeEdge(edge);

        boolean removeStatus = edges.remove(edge);
        return removeStatus;
    }

    /**
     * The distance from a Vertex to all other vertices
     * @param source the source to calculate distance from
     * @return the distance
     */
    public HashMap<Vertex, Double> distanceFrom(Vertex source) {
        HashMap<Vertex, Double> hashMap = new HashMap<>();
        Comparator<Vertex> comparator = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return hashMap.get(o1).compareTo(hashMap.get(o2));
            }
        };
        PriorityQueue<Vertex> priorityQueue = new Heap<>(comparator, false);
        for (Vertex v : vertices) {
            if (!v.equals(source)) {
                hashMap.put(v, Double.POSITIVE_INFINITY);
            } else {
                hashMap.put(source, 0.0);
            }
            priorityQueue.offer(v);
        }
        while (priorityQueue.size() != 0) {
            Vertex u = priorityQueue.poll();
            for (Vertex neighbor : u.adjacentVertices()) {
                double alt = hashMap.get(u) + u.getEdgeTo(neighbor).distance();
                if (alt < hashMap.get(neighbor)) {
                    hashMap.put(neighbor, alt);
                    priorityQueue.updatePriority(neighbor);
                }
            }
        }
        return hashMap;
    }
}