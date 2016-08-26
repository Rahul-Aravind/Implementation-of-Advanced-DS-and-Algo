import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LP3Level1 {
	
	
	
	public static void driver(Graph g){
		Vertex src = g.verts.get(1);
		if(ShortestPathAlgorithms.findShortestPath(g, src)){
			ShortestPathAlgorithms.printShortestPath(g, src);
		}else{
			System.out.println("Unable to solve problem. Graph has a negative cycle");
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

		Graph g = Graph.readGraph(in, true);

		LP3Level1.driver(g);

		/*
		 * for (File inputFile : new
		 * File("C://Users//RahulAravind//Downloads//lp3-no//lp3-data//lp3-no").
		 * listFiles()) { System.out.println(
		 * "-----------------------------------------------------------------------------------------"
		 * );
		 * 
		 * }
		 */
	}

}
