import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Rahul Aravind
 * @author Ramesh
 * @author Anandhan
 * @author Sanjana
 * 
 * Implementation of Kruskal's algorithm using Disjoint set and Priority Queue Data Structure
 *
 */
public class Kruskal {
	
	static long kruskalMST(Graph g) {
		long wmst = 0;
		
		BinaryHeap<Edge> pq = new BinaryHeap<Edge>(g.numNodes * 2, new Edge());
		
		// add all edges to the minHeap
		for(Vertex vertex : g.verts) {
			if(vertex != null && !vertex.seen) {
				vertex.seen = true;
				for(Edge e : vertex.Adj) {
					if(e.otherEnd(vertex).seen) {
						pq.add(e);
					}
				}
			}
		}
		
		// Initialze a disjoint set data structure
		DisjointSet disjointSet = new DisjointSet(g.verts.size());
		
		// loop through each and every edge
		while(!pq.isEmpty()) {
			Edge e = pq.remove();
			
			Vertex u = e.From;
			Vertex v = e.otherEnd(u);
			
			// Get the root identifiers of the components
			Vertex ru = disjointSet.find(u);
			Vertex rv = disjointSet.find(v);
			
			// If u and v are in different components, then union them together
			if(ru.name != rv.name) {
				disjointSet.union(u, v);
				wmst += e.Weight;
			}
		}
		
		return wmst;
	}
	
	/**
	 * Driver program
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	
	public static void main(String args[]) throws FileNotFoundException {
		
		Scanner in;
		in = new Scanner(new File("C:\\Users\\RahulAravind\\workspace\\java\\ADS_SP_PQ\\src\\BigData.txt"));
		Graph g = Graph.readGraph(in, false);
		
		long startTime = System.currentTimeMillis();
		long wmst = kruskalMST(g);
		long endTime = System.currentTimeMillis();
		System.out.println("Weight of the Minimum spanning tree " + wmst);
		long elapsedtime = endTime - startTime;
		System.out.println("Time taken " + elapsedtime + " ms");
	}

}
