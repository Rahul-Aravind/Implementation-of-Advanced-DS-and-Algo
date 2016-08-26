import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class EdmondsDirectedMST {
	
	public static long wmst=0;
	
	

	public static ArrayList<Edge> createSuperVertex(Graph g,
			ArrayList<Edge> path) {
		// Remove the unnecessary edge from the path to capture the cycle
		Vertex cycleStartVertex = path.get(path.size() - 1).From;

		while (cycleStartVertex != path.get(0).To) {
			path.remove(0);
		}
		

		Vertex superVertex = new Vertex(++g.numNodes);
		superVertex.isSuper = true;
		g.verts.add(superVertex);

		for (Edge cycleEdge : path) {
			Vertex u = cycleEdge.From;
			System.out.print(" DeActivate: " + u);
			u.isActive = false;
			u.superVert = superVertex;
		}

		//HashSet<Vertex> cycleVertex=new HashSet<>();
		//HashSet<Vertex> toVertSet=new HashSet<Vertex>();
		for (Edge cycleEdge : path) {
			Vertex u = cycleEdge.From;
			
			for(Edge e:u.Adj){
				if (e.To.isActive) {
					Edge newEdge=g.addDirectedEdge(superVertex.name, e.To.name, e.currWeight);
					//updating zeroRevEdge for the other Node
					if(e.currWeight==0){
						e.To.zeroRevEdge=newEdge;
					}
				}
				
				 
			}
			
			for (Edge e : u.revAdj) {
				
				if (e.From.isActive) {
					g.addDirectedEdge(e.From.name,superVertex.name, e.currWeight);
				}
			}
		}
		
		// minimize the weight for the superVertex
		//printGraphAdj(g);
		//printGraphRevAdj(g);
		minimizeWeight(superVertex);

		return path;
	}

	public static ArrayList<Edge> findCycle(Graph g, Vertex start) {
		ArrayList<Edge> path = new ArrayList<Edge>();

		for (Vertex u : g) {
			u.visited = false;
		}

		Vertex u = start, v = null;
		int test=0;
		while (u != null) {

			Edge e = null;
			if ((e = u.zeroRevEdge) != null) {
				path.add(e);
				v = e.From;
				System.out.println(v);
				//special condition for super vertex 
				//vertex is not active and super vertex is not visited
				//Vertex superiorVertex=getSuperiorVertex(v);
				if(v.isActive==false){
					
					u=getSuperiorVertex(v);
					//System.out.println("Inactive Node: "+v + "<-" +u+ u.zeroRevEdge);
						continue;
				}
				//if (v.visited == true&&v.superVert.visited==true) {
				if (v.visited == true) {
					System.out.println("cycle found..." + path);
					return path;
				}
			}
			u.visited = true;
			u = v;

		}

		return null;

	}
	
	public static Vertex getSuperiorVertex(Vertex v){
		Vertex superVertex=null;
		while(v.superVert!=null){
			superVertex=v.superVert;
			v=v.superVert;
		}
		
		return superVertex;
	}

	public static Vertex mBFS(Graph g, Vertex src) {

		for (Vertex u : g) {
			u.seen = false;
		}

		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(src);
		src.seen = true;
		System.out.print("BFS ->");

		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			System.out.print(u.name + " ");
			// System.out.print(u.name+" "+u.isActive+" ");
			for (Edge e : u.Adj) {
				Vertex v = e.To;
				// including condition for Super Vertex
				if (v.isActive == false) {
					continue;
				}

				if (e.currWeight == 0 & !v.seen) {
					v.seen = true;
					v.parent = u;
					queue.add(v);
				}
			}
		}

		for (Vertex u : g) {
			if (u.isActive == true && u.seen == false) {
				return u;
			}
		}
		System.out.println();
		return null;
	}

	public static void minimizeWeight(Vertex v) {
		Edge minEdge = v.revAdj.get(0);
		for (Edge edge : v.revAdj) {
			if (minEdge.currWeight > edge.currWeight) {
				minEdge = edge;
			}
		}
		wmst+=minEdge.currWeight;

		for (Edge edge : v.revAdj) {
			edge.currWeight = edge.currWeight - minEdge.currWeight;
			if (edge.currWeight == 0) {
				v.zeroRevEdge=edge;
			}
		}
	}

	public static void init(Graph g, Vertex src) {

		for (Vertex v : g) {
			if (v != src) {
				minimizeWeight(v);

			}
		}

	}

	public static void printGraphRevAdj(Graph g) {
		System.out.println("Graph-->RevAdjList");
		for (Vertex v : g) {
			if(v.isActive==true)
			System.out.println(v + "-->" + v.revAdj);

		}
	}

	public static void printGraphAdj(Graph g) {
		System.out.println("Graph-->AdjList");
		for (Vertex v : g) {
			if (v.isActive == true)
				System.out.println(v + "-->" + v.Adj);

		}
	}

	public static void Edmond(Graph g) {

		Vertex root = g.verts.get(1);
		// System.out.println("Minimize");
		init(g, root);
		Vertex startNode = null;
		printGraphAdj(g);
		printGraphRevAdj(g);
		
		
		System.out.println("----------Starting Edmonds Algorithm----------");
		int iter=0;

		//&& ++iter!=3
		while ((startNode = mBFS(g, root)) != null) {

			System.out.println("unReachable Node: " + startNode);

			ArrayList<Edge> path = findCycle(g, startNode);
			System.out.println(createSuperVertex(g, path));
			System.out.println("After Shrinking...");
			printGraphAdj(g);
			printGraphRevAdj(g);
			iter++;
			System.out.println("iteration: "+iter);
		}
		System.out.println(wmst);

	}

	public static void test(Graph g) {

		printGraphAdj(g);
		Vertex root = g.verts.get(1);
		System.out.println("Minimize");
		init(g, root);

		printGraphAdj(g);
		Vertex startNode = mBFS(g, root);
		System.out.println("unReachable Node: " + startNode);

		ArrayList<Edge> path = findCycle(g, startNode);
		System.out.println(createSuperVertex(g, path));
		System.out.println("After Shrinking...");
		printGraphAdj(g);
		printGraphRevAdj(g);

		startNode = mBFS(g, root);
		System.out.println("unReachable Node: " + startNode);

		// startNode=mBFS(g,g.verts.get(1));
		// System.out.println("unReachable Node: "+startNode);

	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = null;

		/*
		 * if (args.length > 0) { File inputFile = new File(args[0]); in = new
		 * Scanner(inputFile); } else { in = new Scanner(System.in); }
		 */

		/*File inputFile = new File(
				"G:\\Course\\ImplOfAdvancedDS\\assignment\\LP2\\lp2-data\\1-lp2.txt");
		in = new Scanner(inputFile);*/
		
		in = new Scanner(System.in);

		// long startTime = System.currentTimeMillis();
		Graph g = Graph.readGraph(in, true);
		// BFS(g,g.verts.get(1));
		//test(g);
		Edmond(g);
	}

}
