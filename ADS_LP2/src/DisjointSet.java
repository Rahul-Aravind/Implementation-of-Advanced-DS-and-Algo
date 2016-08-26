/**
 * 
 * @author Rahul Aravind
 * @author Ramesh
 * @author Anandhan
 * @author Sanjana
 * 
 * Disjoint set data structure that contains implementations for
 * 
 * make_set()
 * union()
 * find()
 *
 */
public class DisjointSet {
	
	private int[] rank; // rank of a component
	private Vertex[] parent; // parent of a component
	private int size; // count of components
	
	
	// initialize a disjoint set of size n (Make set operation)
	DisjointSet(int n) {
		size = n;
		rank = new int[n + 1];
		parent = new Vertex[n + 1];
		
		rank[0] = 0;
		parent[0] = null;
		for(int i = 1; i <= n; i++) {
			rank[i] = 0;
			parent[i] = new Vertex(i);
		}
	}
	
	// retrieve the prime identifier of the component present in the set
	public Vertex find(Vertex v) {
		while(v != parent[v.name]) {
			// perform path compression
			
			Vertex pVertex = parent[v.name];
			parent[v.name] = parent[pVertex.name];
			v = parent[v.name];
		}
		return v;
	}
	
	// union operation
	public void union(Vertex u, Vertex v) {
		Vertex pU = find(u);
		Vertex pV = find(v);
		
		// Get the names of the parent vertices
		int uName = pU.name;
		int vName = pV.name;
		
		// If both the components are in the same set, then return
		if(uName == vName) {
			return;
		}
		
		// Assign the parent of a component based on the rank
		if(rank[uName] < rank[vName]) {
			parent[uName] = pV;
		}
		else if(rank[uName] > rank[vName]) {
			parent[vName] = pU;
		}
		else {
			parent[vName] = pU;
			rank[uName]++;
		}
		
		size--;
	}

}
