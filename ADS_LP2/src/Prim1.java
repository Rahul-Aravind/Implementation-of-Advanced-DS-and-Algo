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
 * Implementation of Prim's Minimum Spanning tree algorithm using Binary Heap
 *
 */
public class Prim1 {
	
	// Finding minimum spanning tree using Prim's algorithm. Implemented using BinaryHeap
	static int PrimMST(Graph g) {
		int wmst = 0;
		Vertex src = g.verts.get(1);
		src.seen = true;
		
		// initialize a priority queue to store the edges
		BinaryHeap<Edge> pq = new BinaryHeap<Edge>(g.numNodes * 2, new Edge());
		for(Edge e : src.Adj) {
			pq.add(e);
		}
		
		//Loop through each and every edge
		while(!pq.isEmpty()) {
			Edge e = pq.remove();
			if(e.From.seen && e.To.seen) continue;
			
			Vertex v;
			if(!e.From.seen) v = e.From;
			else v = e.To;
			
			v.seen = true;
			wmst += e.Weight;
			
			// add the edge only if the other end vertex has not been visited
			for(Edge edge : v.Adj) {
				Vertex u = edge.otherEnd(v);
				if(!u.seen) pq.add(edge);
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
		int wmst = PrimMST(g);
		long endTime = System.currentTimeMillis();
		System.out.println("Weight of the Minimum spanning tree " + wmst);
		long elapsedtime = endTime - startTime;
		System.out.println("Time taken " + elapsedtime + " ms");
	}

}
