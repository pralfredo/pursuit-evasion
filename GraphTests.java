
/**
 * File: GraphTests.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: It is the tester file for Graphs in this project.
 */
public class GraphTests {

    public static void main(String[] args) {
        // constructor(), size(), getVertices(), getEdges()
        {
            System.out.println("First let's create a default Graph with no vertices or no initial probability");
            Graph myGraph1 = new Graph();
            System.out.println("Number of vertices: " + myGraph1.size() + " == 0");
            assert myGraph1.size() == 0 : "Error in Graph::Graph() or Graph::size()";
            int vertices = 0;
            for (Vertex ver : myGraph1.getVertices()) {
                vertices++;
            }
            int edges = 0;
            for (Edge e : myGraph1.getEdges()) {
                edges++;
            }
            System.out.println("Number of vertices: " + vertices + " == 0");
            assert vertices == 0 : "Error in Graph::Graph() or Graph::getVertices()";
            System.out.println("Number of edges: " + edges + " == 0");
            assert edges == 0 : "Error in Graph::Graph() or Graph::getEdges()";

            System.out.println("Let's create a Graph with 10 vertices but no initial probability");
            Graph myGraph2 = new Graph(10);
            System.out.println("Number of vertices: " + myGraph2.size() + " == 10");
            assert myGraph2.size() == 10 : "Error in Graph::Graph() or Graph::size()";
            vertices = 0;
            for (Vertex ver : myGraph2.getVertices()) {
                vertices++;
            }
            edges = 0;
            for (Edge e : myGraph2.getEdges()) {
                edges++;
            }
            System.out.println("Number of vertices: " + vertices + " == 10");
            assert vertices == 10 : "Error in Graph::Graph() or Graph::getVertices()";
            System.out.println("Number of edges: " + edges + " == 0");
            assert edges == 0 : "Error in Graph::Graph() or Graph::getEdges()";

            System.out
                    .println("Let's create a Graph with 10 vertices and 100% initial probability for simplicity sake");
            Graph myGraph3 = new Graph(10, 1.0);
            System.out.println("Number of vertices: " + myGraph3.size() + " == 10");
            assert myGraph3.size() == 10 : "Error in Graph::Graph() or Graph::size()";
            vertices = 0;
            for (Vertex ver : myGraph3.getVertices()) {
                vertices++;
            }
            edges = 0;
            for (Edge e : myGraph3.getEdges()) {
                edges++;
            }
            System.out.println("Number of vertices: " + vertices + " == 10");
            assert vertices == 10 : "Error in Graph::Graph() or Graph::getVertices()";
            System.out.println("Number of edges: " + edges + " == 55");
            assert edges == 55 : "Error in Graph::Graph() or Graph::getEdges()";
        }

        // addVertex(), addEdge(), getEdge()
        {
            Graph myGraph = new Graph();
            System.out.println("After creating an empty Graph, there should be no vertices or edges");
            System.out.println("Size: " + myGraph.size() + " == 0");
            Vertex u = myGraph.addVertex();
            Vertex v = myGraph.addVertex();
            Vertex w = myGraph.addVertex();
            System.out.println("After adding three vertices {u, v, w}, the size should be 3, but no edges");
            System.out.println("Size: " + myGraph.size() + " == 3");
            assert myGraph.size() == 3 : "Erorr in Graph::addVertex()";
            System.out.println("To confirm, let's double check with getVertices() and getEdges()");
            int size = 0;
            for (Vertex c : myGraph.getVertices()) {
                size++;
            }
            System.out.println("Size: " + size + " == 3");
            assert size == 3 : "Erorr in Graph::addVertex() or Graph::getVertices()";
            size = 0;
            for (Edge e : myGraph.getEdges()) {
                size++;
            }
            System.out.println("Number of Edges: " + size + " == 0");
            assert size == 0 : "Erorr in Graph::addVertex() or Graph::getEdges()";
            Edge uv = myGraph.addEdge(u, v, 3.0);
            Edge uw = myGraph.addEdge(u, w, 2.0);
            System.out.println(
                    "After adding an edge between (u, v) and (u, w), there should 3 vertices and 2 edges in the Graph");
            System.out.println("Size: " + myGraph.size() + " == 3");
            assert myGraph.size() == 3 : "Erorr in Graph::addEdge()";
            size = 0;
            for (Edge e : myGraph.getEdges()) {
                size++;
            }
            System.out.println("Number of Edges: " + size + " == 2");
            assert size == 2 : "Erorr in Graph::addEdge() or Graph::getEdges()";
            System.out.println("To confirm the two edges, let's use the getEdge()");
            System.out.println("Edge u to v: " + myGraph.getEdge(u, v).equals(uv) + " == true");
            System.out.println("Edge u to w: " + myGraph.getEdge(u, w).equals(uw) + " == true");
            assert myGraph.getEdge(u, v).equals(uv) == true : "Error in Graph::getEdge() or Graph::addEdge()";
            assert myGraph.getEdge(u, w).equals(uw) == true : "Error in Graph::getEdge() or Graph::addEdge()";
        }

        // remove() for both vertex and edge
        {
            Graph myGraph = new Graph();
            Vertex u = myGraph.addVertex();
            Vertex v = myGraph.addVertex();
            Vertex w = myGraph.addVertex();
            Edge uv = myGraph.addEdge(u, v, 3.0);
            Edge uw = myGraph.addEdge(u, w, 2.0);
            System.out
                    .println("After creating an empty Graph and adding vertices {u, v, w} and edges {(u, v), (u, w)}");
            System.out.println("Size: " + myGraph.size() + " == 3");
            int size = 0;
            for (Edge e : myGraph.getEdges()) {
                size++;
            }
            System.out.println("Number of Edges: " + size + " == 2");
            myGraph.remove(uv);
            System.out.println("After removing the edge (u, v):");
            System.out.println("Does edge (u, v) exist? " + (myGraph.getEdge(u, v) != null) + " == false");
            assert myGraph.getEdge(u, v) == null : "Error in Graph::remove() or Graph::getEdge()";
            System.out.println(
                    "Let's confirm by checking if the number of edges changed and if number of vertices didn't changed");
            System.out.println("Size: " + myGraph.size() + " == 3");
            assert myGraph.size() == 3 : "Erorr in Graph::remove()";
            size = 0;
            for (Edge e : myGraph.getEdges()) {
                size++;
            }
            System.out.println("Number of Edges: " + size + " == 1");
            assert size == 1 : "Erorr in Graph::remove() or Graph::getEdges()";
            myGraph.remove(v);
            System.out.println("After removing the vertex v:");
            System.out.println("Did the size change? " + (myGraph.size() == 2) + " == true");
            assert myGraph.size() == 2 : "Erorr in Graph::remove()";
            System.out.println(
                    "Let's confirm by checking if the number of edges didn't change and if the number of vertices changed");
            size = 0;
            for (Vertex cur : myGraph.getVertices()) {
                size++;
            }
            System.out.println("Size: " + size + " == 2");
            assert size == 2 : "Error in Graph::remvoe() or Graph::getVertices()";
            size = 0;
            for (Edge e : myGraph.getEdges()) {
                size++;
            }
            System.out.println("Number of Edges: " + size + " == 1");
            assert size == 1 : "Erorr in Graph::remove() or Graph::getEdges()";
            Vertex x = myGraph.addVertex();
            System.out
                    .println("After adding vertex x, the size should be 3, but the number of edges should still be 1");
            System.out.println("Size: " + myGraph.size() + " == 3");
            assert myGraph.size() == 3 : "Erorr in Graph::addVertex()";
            size = 0;
            for (Edge e : myGraph.getEdges()) {
                size++;
            }
            System.out.println("Number of Edges: " + size + " == 1");
            assert size == 1 : "Erorr in Graph::remove() or Graph::getEdges()";
            myGraph.remove(w);
            System.out.println("After removing vertex w, the size should be 2 and the number of edges should be 0");
            System.out.println("Size: " + myGraph.size() + " == 2");
            assert myGraph.size() == 2 : "Erorr in Graph::remove()";
            size = 0;
            for (Edge e : myGraph.getEdges()) {
                size++;
            }
            System.out.println("Number of Edges: " + size + " == 1");
            assert size == 1 : "Erorr in Graph::remove() or Graph::getEdges()";
        }

        // distanceFrom()
        {
            Graph myGraph = new Graph();
            Vertex a = myGraph.addVertex();
            Vertex b = myGraph.addVertex();
            Vertex c = myGraph.addVertex();
            Vertex d = myGraph.addVertex();
            Vertex e = myGraph.addVertex();
            Vertex f = myGraph.addVertex();
            Vertex g = myGraph.addVertex();
            Vertex h = myGraph.addVertex();
            Edge ab = myGraph.addEdge(a, b, 4.5);
            Edge ac = myGraph.addEdge(a, c, 5);
            Edge ad = myGraph.addEdge(a, d, 5);
            Edge bc = myGraph.addEdge(b, c, 4.5);
            Edge be = myGraph.addEdge(b, e, 2);
            Edge cd = myGraph.addEdge(c, d, 6);
            Edge cf = myGraph.addEdge(c, f, 3.5);
            Edge dg = myGraph.addEdge(d, g, 7);
            Edge ef = myGraph.addEdge(e, f, 3.5);
            Edge eh = myGraph.addEdge(e, h, 5.5);
            Edge fh = myGraph.addEdge(f, h, 2.5);
            Edge fg = myGraph.addEdge(f, g, 4.5);
            Edge gh = myGraph.addEdge(g, h, 7);
            Vertex source = a;
            System.out.println("Let's see the minimumal distance of all the vertices from the source");
            HashMap<Vertex, Double> minDistances = myGraph.distanceFrom(source);
            System.out.println(minDistances);
            System.out
                    .println("Let's confirm if the minimumal distance of all the vertices from the source is correct:");
            System.out.println("Vertex a: " + a + " --> " + minDistances.get(a) + " == 0.0");
            System.out.println("Vertex b: " + b + " --> " + minDistances.get(b) + " == 4.5");
            System.out.println("Vertex c: " + c + " --> " + minDistances.get(c) + " == 5.0");
            System.out.println("Vertex d: " + d + " --> " + minDistances.get(d) + " == 5.0");
            System.out.println("Vertex e: " + e + " --> " + minDistances.get(e) + " == 6.5");
            System.out.println("Vertex f: " + f + " --> " + minDistances.get(f) + " == 8.5");
            System.out.println("Vertex g: " + g + " --> " + minDistances.get(g) + " == 12.0");
            System.out.println("Vertex h: " + h + " --> " + minDistances.get(h) + " == 11.0");
            assert minDistances.get(a) == 0.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(b) == 4.5 : "Error in Graph::distanceFrom()";
            assert minDistances.get(c) == 5.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(d) == 5.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(e) == 6.5 : "Error in Graph::distanceFrom()";
            assert minDistances.get(f) == 8.5 : "Error in Graph::distanceFrom()";
            assert minDistances.get(g) == 12.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(h) == 11.0 : "Error in Graph::distanceFrom()";
        }
    }
}