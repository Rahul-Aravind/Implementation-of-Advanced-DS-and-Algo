package testV1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Euler {
	
	public static long joinTime=0;

	public static Vertex getNextVertexWithUnusedEdges(
			LinkedList<Vertex> vertexWithUnusedEdgeList) {
		Vertex v = null;
		
		if (vertexWithUnusedEdgeList.size() == 0) {
			return v;
		}

		while ((v = vertexWithUnusedEdgeList.pollLast()) != null) {
			if (v.Adj.size() > 0) {
				return v;
			}
		}
		return v;
	}

	public static void joinTour(LinkedList<Edge> fullTour,
			LinkedList<Edge> currentTour, Vertex joiningVertex) {
		
		//For first join the full tour is emtpy we can add as it is
		if (fullTour.size() == 0) {
			fullTour.addAll(currentTour);
			return;
		}
		
		//using descending iterator because the node chosen for joining is
		//near the end of the previous circuit
		Iterator<Edge> it = fullTour.descendingIterator();
		int i = fullTour.size() - 1;
		while (it.hasNext()) {
			Edge e = it.next();
			if (joiningVertex == e.To || joiningVertex == e.From) {
				break;
			}
			i--;
		}
		
		fullTour.addAll(i, currentTour);
	}

	public static LinkedList<Edge> hierholzerHolzer(Graph g, Vertex sVertex) {
		Vertex currVertex = sVertex;
		LinkedList<Edge> fullTour = new LinkedList<Edge>();
		LinkedList<Edge> currentTour = new LinkedList<Edge>();

		LinkedList<Vertex> vertexWithUnusedEdgeList = new LinkedList<Vertex>();
		Vertex nextVertex = null;

		while (currVertex != null) {
			if (currVertex.Adj.size() - 1 > 0) {
				vertexWithUnusedEdgeList.add(currVertex);
			}
			
			Edge edge = currVertex.Adj.remove(0);
			nextVertex = edge.otherEnd(currVertex);
			currentTour.add(edge);
			nextVertex.Adj.remove(edge);
			currVertex = nextVertex;
			
			if (nextVertex == sVertex) {
				// System.out.println("Found circuit Starting at:"+sVertex);
				joinTour(fullTour, currentTour, sVertex);
				currentTour.clear();
				sVertex = getNextVertexWithUnusedEdges(vertexWithUnusedEdgeList);
				currVertex = sVertex;
				// fullTour.addAll(currentTour);

			}
		}

		return fullTour;

	}

	public static int isEuler(Graph g) {
		Iterator<Vertex> it = g.iterator();
		int noOfOddEdge = 0;
		while (it.hasNext()) {
			Vertex u = it.next();
			if (u.Adj.size() % 2 != 0) {
				noOfOddEdge++;
			}
		}

		if (noOfOddEdge == 0) {
			System.out.println("Graph has a Euler Tour");
			return 0;
		} else if (noOfOddEdge == 2) {
			System.out.println("Graph has a Euler Path");
			return 1;
		} else {
			System.out.println("Graph doen't have a Euler Tour or Euler Path");
			return -1;
		}
	}

	public static boolean isConnected(Graph g) {
		// Do BFS - if the graph is connected all the vertex in the should have
		// seen flag set to true
		BFS(g, g.verts.get(1));
		Iterator<Vertex> it = g.iterator();
		while (it.hasNext()) {
			Vertex u = it.next();
			if (u.seen == false) {
				// atleast one of the node in the graph has seen flag set to
				// false
				// graph is disconnected.
				System.out.println("Graph is disconnected");
				return false;
			}
		}
		System.out.println("Graph is connected");
		return true;
	}

	public static void BFS(Graph g, Vertex src) {

		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(src);
		src.seen = true;

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			// System.out.print(u.name+" ");
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (!v.seen) {
					v.seen = true;
					v.parent = u;
					queue.add(v);
				}
			}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = null;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		long startTime = System.currentTimeMillis();
		Graph g = Graph.readGraph(in, false);
		long endTime = System.currentTimeMillis();
		System.out.println("Time to read the graph: " + (endTime - startTime)+" ms");

		//check the connectedness and eulerian or not.
		startTime = System.currentTimeMillis();
		if(!isConnected(g)||isEuler(g)==-1){
			System.out.println("Not a Euler graph");
			return;
		}
		endTime = System.currentTimeMillis();		
		System.out.println("Time to find the connectedness of the graph and Eulerian graph:"+(endTime-startTime)+" ms");
		
		startTime = System.currentTimeMillis();
		LinkedList<Edge> eulerTour = hierholzerHolzer(g, g.verts.get(1));
		endTime = System.currentTimeMillis();
		System.out.println("Number of edges: " + eulerTour.size());
		System.out.println("Time to find the Euler Tour:"
				+ (endTime - startTime) + " ms of size: " + g.numNodes);
		//System.out.println("joinTime(ms): "+joinTime);

	}

}
