import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LP3Level2 {

	public static void driver(Graph g) {
		Vertex src = g.verts.get(1);
		NumberOfShortestPaths numberOfShortestPaths=new NumberOfShortestPaths(g, src);
		
		if (ShortestPathAlgorithms.findShortestPath(g, src)) {
			//shortest path Exist
			if(numberOfShortestPaths.countTotalNumberOfShortestPaths()){
			//shortest path is countable
				numberOfShortestPaths.printNoOfShortestPaths();
			}
			
		} else {
			System.out.println( "Non-positive cycle in graph. DAC is not applicable");
			numberOfShortestPaths.printNegativeWeightedCycle();
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

		LP3Level2.driver(g);

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
