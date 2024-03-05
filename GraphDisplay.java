
/**
 * File: GraphDisplay.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: Displays a Landscape graphically using Swing. The Landscape
 * contains a grid which can be displayed at any scale factor. 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;

/**
 * Displays a Landscape graphically using Swing. The Landscape
 * contains a grid which can be displayed at any scale factor.
 * 
 * @author bseastwo
 */
public class GraphDisplay {

	/**
	 * 
	 * A class representing a 2D coordinate point.
	 */
	final class Coord {
		double x, y;

		/**
		 * 
		 * Constructor for a Coord object.
		 * 
		 * @param a the x-coordinate of the point.
		 * @param b the y-coordinate of the point.
		 */
		Coord(double a, double b) {
			x = a;
			y = b;
		}

		/**
		 * 
		 * Calculates the Euclidean norm of the vector defined by this point.
		 * 
		 * @return the Euclidean norm of the vector defined by this point.
		 */
		double norm() {
			return Math.sqrt(x * x + y * y);
		}

		/**
		 * 
		 * Calculates the difference vector between this point and another given point.
		 * 
		 * @param c the other point.
		 * @return the difference vector between this point and the other given point.
		 */
		Coord diff(Coord c) {
			return new Coord(x - c.x, y - c.y);
		}

		/**
		 * 
		 * Calculates the sum vector between this point and another given point.
		 * 
		 * @param c the other point.
		 * @return the sum vector between this point and the other given point.
		 */
		Coord sum(Coord c) {
			return new Coord(x + c.x, y + c.y);
		}

		/**
		 * 
		 * Adds another coordinate to this coordinate.
		 * 
		 * @param c the other coordinate.
		 */
		void addBy(Coord c) {
			x += c.x;
			y += c.y;
		}

		/**
		 * 
		 * Scales the vector defined by this point by a given factor.
		 * 
		 * @param d the scaling factor.
		 * @return the scaled vector.
		 */
		Coord scale(double d) {
			return new Coord(x * d, y * d);
		}

		/**
		 * 
		 * Returns a string representation of this coordinate.
		 * 
		 * @return a string representation of this coordinate.
		 */
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	/**
	 * 
	 * A class representing a GUI window with a graph visualization.
	 */
	JFrame win;
	/**
	 * 
	 * The graph object representing the game board.
	 */
	protected Graph graph;
	/**
	 * 
	 * The canvas object representing the graphical grid.
	 */
	private LandscapePanel canvas;
	/**
	 * 
	 * The scale of each square in the grid.
	 */
	private int gridScale;
	/**
	 * 
	 * The pursuer player algorithm.
	 */
	AbstractPlayerAlgorithm pursuer;
	/**
	 * 
	 * The evader player algorithm.
	 */
	AbstractPlayerAlgorithm evader;
	/**
	 * 
	 * A hashmap containing the coordinates of each vertex in the graph.
	 */
	HashMap<Vertex, Coord> coords;

	/**
	 * Initializes a display window for a Landscape.
	 * 
	 * @param scape the Landscape to display
	 * @param scale controls the relative size of the display
	 * @throws InterruptedException
	 */
	public GraphDisplay(Graph g, int scale) throws InterruptedException {
		this(g, null, null, scale);
	}

	/**
	 * Initializes a display window for a Landscape.
	 * 
	 * @param g       the graphics component
	 * @param pursuer the pursuer
	 * @param evader  the evader
	 * @param scale   controls the relative size of the display
	 * @throws InterruptedException
	 */
	public GraphDisplay(Graph g, AbstractPlayerAlgorithm pursuer, AbstractPlayerAlgorithm evader, int scale)
			throws InterruptedException {

		// setup the window
		this.win = new JFrame("Pursuit");
		this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pursuer = pursuer;
		this.evader = evader;

		this.graph = g;
		this.gridScale = scale;

		// create a panel in which to display the Landscape
		// put a buffer of two rows around the display grid
		this.canvas = new LandscapePanel((int) (this.graph.size()) * this.gridScale,
				(int) (this.graph.size()) * this.gridScale);

		// add the panel to the window, layout, and display
		this.win.add(this.canvas, BorderLayout.CENTER);
		this.win.pack();
		createCoordinateSystem();
		this.win.setVisible(true);
		repaint();
	}

	/**
	 * Sets the Graph up
	 * 
	 * @param graph the graph
	 * @throws InterruptedException
	 */
	public void setGraph(Graph graph) throws InterruptedException {
		this.graph = graph;
		createCoordinateSystem();
	}

	/**
	 * Saves an image of the display contents to a file. The supplied
	 * filename should have an extension supported by javax.imageio, e.g.
	 * "png" or "jpg".
	 *
	 * @param filename the name of the file to save
	 */
	public void saveImage(String filename) {
		// get the file extension from the filename
		String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

		// create an image buffer to save this component
		Component tosave = this.win.getRootPane();
		BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		// paint the component to the image buffer
		Graphics g = image.createGraphics();
		tosave.paint(g);
		g.dispose();

		// save the image
		try {
			ImageIO.write(image, ext, new File(filename));
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	/**
	 * Creates the Coordinate System for the Graph
	 * 
	 * @throws InterruptedException
	 */
	public void createCoordinateSystem() throws InterruptedException {

		// draw the graph
		// see http://yifanhu.net/PUB/graph_draw_small.pdf for more details
		Random rand = new Random();
		HashMap<Vertex, HashMap<Vertex, Double>> distances = new HashMap<>();
		for (Vertex v : graph.getVertices())
			distances.put(v, graph.distanceFrom(v));

		// Calculate degree centrality for each vertex
		HashMap<Vertex, Integer> degreeCentrality = new HashMap<>();
		int maxDegree = 0;
		for (Vertex v : graph.getVertices()) {
			int degree = v.degree();
			degreeCentrality.put(v, degree);
			maxDegree = Math.max(maxDegree, degree);
		}

		coords = new HashMap<>();
		for (Vertex v : graph.getVertices()) {
			double norm = degreeCentrality.get(v) / (double) maxDegree;
			double radius = norm * canvas.getWidth() / 2.5;
			double angle = rand.nextDouble() * 2 * Math.PI;
			double x = radius * Math.cos(angle);
			double y = radius * Math.sin(angle);
			coords.put(v, new Coord(x, y));
		}

		double step = 1000;
		for (int i = 0; i < 100; i++) {
			HashMap<Vertex, Coord> newCoords = new HashMap<>();
			for (Vertex v : graph.getVertices()) {
				Coord f = new Coord(0, 0);
				boolean pickRandom = false;
				for (Vertex u : graph.getVertices()) {
					if (u == v)
						continue;
					Coord xv = coords.get(v);
					Coord xu = coords.get(u);
					if ((Math.abs(xv.x - xu.x) > .1 / i) && (Math.abs(xv.y - xu.y) > .1 / i))
						f.addBy(xu.diff(xv).scale((xu.diff(xv).norm()
								- (distances.get(u).get(v) == Double.POSITIVE_INFINITY ? 1000
										: distances.get(u).get(v) * 100))
								/ (xu.diff(xv).norm())));
					else
						pickRandom = true;
				}
				if (!pickRandom)
					newCoords.put(v,
							f.x == 0 && f.y == 0 ? coords.get(v) : coords.get(v).sum(f.scale(step / f.norm())));
				else
					newCoords.put(v, new Coord(rand.nextInt(canvas.getWidth() / 2) - canvas.getWidth() / 2,
							rand.nextInt(canvas.getHeight() / 2) - canvas.getHeight() / 2));

			}
			step *= .9;
			Coord average = new Coord(0, 0);
			for (Vertex v : graph.getVertices())
				average.addBy(coords.get(v));
			average = average.scale(1.0 / graph.size());
			for (Coord c : newCoords.values()) {
				c.x -= average.x;
				c.x = Math.min(Math.max(c.x, -canvas.getWidth() / 2), canvas.getWidth() / 2);
				c.y -= average.y;
				c.y = Math.min(Math.max(c.y, -canvas.getHeight() / 2), canvas.getHeight() / 2);
			}
			coords = newCoords;
			// Uncomment below to see how the coordinates are formed!
			// repaint();
			// Thread.sleep(50);
		}
		Coord average = new Coord(0, 0);
		for (Vertex v : graph.getVertices())
			average.addBy(coords.get(v));
		average = average.scale(1.0 / graph.size());
		double maxNorm = 0;
		for (Vertex v : graph.getVertices()) {
			Coord newCoord = (new Coord(coords.get(v).x - average.x, coords.get(v).y - average.y));
			coords.put(v, newCoord);
			maxNorm = Math.max(maxNorm, newCoord.norm());
		}
		for (Vertex v : graph.getVertices())
			coords.put(v, coords.get(v)
					.scale((Math.min(canvas.getWidth() / 2, canvas.getHeight() / 2) - gridScale / 2) / maxNorm));

		int singletonCount = 0;
		for (Vertex v : graph.getVertices())
			if (!v.adjacentVertices().iterator().hasNext())
				coords.put(v, new Coord(-canvas.getWidth() / 2 + gridScale * ++singletonCount,
						-canvas.getHeight() / 2 + gridScale));

	}

	/**
	 * This inner class provides the panel on which Landscape elements
	 * are drawn.
	 */
	private class LandscapePanel extends JPanel {
		/**
		 * Creates the panel.
		 * 
		 * @param width  the width of the panel in pixels
		 * @param height the height of the panel in pixels
		 */
		public LandscapePanel(int width, int height) {
			super();
			this.setPreferredSize(new Dimension(width, height));
			this.setBackground(Color.lightGray);
		}

		/**
		 * Method overridden from JComponent that is responsible for
		 * drawing components on the screen. The supplied Graphics
		 * object is used to draw.
		 * 
		 * @param g the Graphics object used for drawing
		 */
		public void paintComponent(Graphics g) {
			// take care of housekeeping by calling parent paintComponent
			super.paintComponent(g);
			if (pursuer != null && pursuer.getCurrentVertex() == evader.getCurrentVertex())
				setBackground(new Color(0, 255, 0));
			g.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
			for (Edge e : graph.getEdges()) {
				g.setColor(Color.BLACK);
				g.drawLine((int) coords.get(e.vertices()[0]).x + gridScale / 4,
						(int) coords.get(e.vertices()[0]).y + gridScale / 4,
						(int) coords.get(e.vertices()[1]).x + gridScale / 4,
						(int) coords.get(e.vertices()[1]).y + gridScale / 4);
			}
			for (Vertex v : graph.getVertices()) {
				g.setColor(Color.BLACK);
				g.fillOval((int) coords.get(v).x, (int) coords.get(v).y, gridScale / 2, gridScale / 2);
			}
			if (curPursueCoord == null || !animated)
				curPursueCoord = coords.get(pursuer.getCurrentVertex());
			if (curEvaderCoord == null || !animated)
				curEvaderCoord = coords.get(evader.getCurrentVertex());
			double dist = Math.sqrt(Math.pow(curPursueCoord.x - curEvaderCoord.x, 2)
					+ Math.pow(curPursueCoord.y - curEvaderCoord.y, 2));
			int c = 0;
			if (dist <= 25) {
				c = 255 - (int) dist;
			}
			g.setColor(new Color(c, 0, 255));
			g.fillOval((int) curPursueCoord.x, (int) curPursueCoord.y, gridScale / 2, gridScale / 2);
			g.setColor(new Color(255, 0, c));
			g.fillOval((int) curEvaderCoord.x, (int) curEvaderCoord.y, gridScale / 2, gridScale / 2);
		} // end paintComponent

	} // end LandscapePanel

	/**
	 * Repaints the Graph
	 */
	public void repaint() {
		this.win.repaint();
	}

	private Coord curPursueCoord; // Current Pursuer Coords
	private Coord curEvaderCoord; // Current Evader Coords
	private boolean animated = false; // If animated

	/**
	 * Animates the movement of the Players
	 * 
	 * @param delay     the delay
	 * @param pMoveFrom the start vertex
	 * @param eMoveFrom the end vertex
	 * @throws InterruptedException
	 */
	public void animate_moving(int delay, Vertex pMoveFrom, Vertex eMoveFrom) throws InterruptedException {
		animated = true;
		Coord pTo = coords.get(pursuer.getCurrentVertex());
		Coord eTo = coords.get(evader.getCurrentVertex());
		Coord pFrom = coords.get(pMoveFrom);
		Coord eFrom = coords.get(eMoveFrom);
		for (int a = 0; a < delay; a++) {
			float t = a / (float) delay;
			curPursueCoord = new Coord((1 - t) * pFrom.x + t * pTo.x, (1 - t) * pFrom.y + t * pTo.y);
			curEvaderCoord = new Coord((1 - t) * eFrom.x + t * eTo.x, (1 - t) * eFrom.y + t * eTo.y);
			if (a % 10 == 0) {
				Thread.sleep(10);
				this.repaint();
			}
		}
	}
}