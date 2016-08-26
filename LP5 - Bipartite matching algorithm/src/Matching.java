import java.util.LinkedList;
import java.util.Queue;

public class Matching {
	public static int matching(Graph g){
		Queue<Vertex> Q = new LinkedList<>();
		int count = 0;
		for (Vertex u : g) {
		    u.seen = false;
		    u.parent = null;
		    u.distance = Integer.MAX_VALUE;
		}

		// Run BFS on every component
		for (Vertex src : g) {
		    if (!src.seen) {
		    	count++;
			src.distance = 0;
			Q.add(src);
			src.seen = true;

			while (!Q.isEmpty()) {
			    Vertex u = Q.remove();
			    for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (!v.seen) {
				    v.seen = true;
				    v.parent = u;
				    v.distance = u.distance + 1;
				    if(u.isOuter == true)
				    	v.isOuter = false;
				    Q.add(v);
				} else {
				    if (u.distance == v.distance) {
					System.out.println("Graph is not bipartite");
					return 0;
				    }
				}
			    }
			}
		    }
		    
		}
		//System.out.println(count);
		Timer obj = new Timer();
		obj.start();
		int msize = 0;
		//find maximal matching using greedy algorithm
		for(Vertex u : g){
			for(Edge e : u.Adj){
				Vertex v = e.otherEnd(u);
//				System.out.print(u + " ");
//				System.out.println(v);
				if(v.mate == null && u.mate == null){
					u.mate = v;
					v.mate = u;
					msize++;
				}
			}
		}
		
//		for(Vertex u : g){
//			System.out.println(u.mate + " " + u.isOuter);
//		}
		
		while(true){
			Queue<Vertex> que = new LinkedList<>();
			for(Vertex u : g){
				u.seen = false;
				u.parent = null;
				if(u.mate == null && u.isOuter == true){
					u.seen = true;
					que.add(u);
				}
			}
//			for(Vertex v : que){
//				System.out.println(v);
//			}
			search:
			while(!que.isEmpty()){
				//System.out.println("hi");
				Vertex u = que.remove();
				for(Edge e : u.Adj){
					Vertex v = e.otherEnd(u);
					if(!v.seen){
						v.parent = u;
						v.seen = true;
						if(v.mate == null){
							ProcessAugPath(v);
							msize++;
							//System.out.println("broke");
							break search;
							
						}
						else
						{
							Vertex x = v.mate;
							x.parent = v;
							x.seen = true;
							que.add(x);
						}
					}
					
				}
			}
			//System.out.println("hello");
			if(que.isEmpty())
				break;
		}
		System.out.println(obj.end());
		return msize;
	}
	
	public static void ProcessAugPath(Vertex u){
		Vertex p = u.parent;
		Vertex x = p.parent;
		u.mate = p;
		p.mate = u;
		while(x != null){
			Vertex nmx = x.parent;
			Vertex y = nmx.parent;
			x.mate = nmx;
			nmx.mate = x;
			x = y;
		}
	}
}
