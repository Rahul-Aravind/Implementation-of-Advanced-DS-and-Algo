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
 * Implementation of Prim's algorithm using Binary Indexed Heap
 *
 */
public class Prim2 {
	
	//Finding MST using Binary Indexed Heap
	static long PrimMST(Graph g) {
		long wmst = 0;
		
		// create a vertices array.
		// set the distance of each vertex to infinity
		
		Vertex[] vertices_arr = new Vertex[g.numNodes + 1];
		vertices_arr[0] = null; // to avoid edge case
		
		int idx = 1;
		for(Vertex v : g.verts) {
			if(v != null) {
				v.distance = Integer.MAX_VALUE;
				vertices_arr[idx++] = v;
			}
		}
		
		// set the distance of the source vertex to zero.
		vertices_arr[1].distance = 0;
		
		for(int j = 1; j < vertices_arr.length; j++) {
			vertices_arr[j].index = j;
		}
		
		// Build a priority queue of vertices using vertex.distance as priority
		IndexedHeap<Vertex> pq = new IndexedHeap<Vertex>(vertices_arr, new Vertex(0));
		
		//Loop through each and every vertex until for all vertices, the minimum
		// distance is set
		
		while(!pq.isEmpty()) {
			Vertex from = pq.remove();
			
			from.seen = true;
			wmst += from.distance;
			
			// Percolate Up once the vertex's min distance is set.
			for(Edge e : from.Adj) {
				Vertex to = e.otherEnd(from);
				if(!to.seen && to.distance > e.Weight) {
					to.distance = e.Weight;
					to.parent = from;
					pq.decreaseKey(to);
				}
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
		long wmst = PrimMST(g);
		long endTime = System.currentTimeMillis();
		System.out.println("Weight of the Minimum spanning tree " + wmst);
		long elapsedtime = endTime - startTime;
		System.out.println("Time taken " + elapsedtime + " ms");
	}

}
