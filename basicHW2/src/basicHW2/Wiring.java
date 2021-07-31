package basicHW2;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class Wiring {

	int nodes, connections;
	Hashtable<String, node> set;
	//node[] set;
	ArrayList<wire> edgesWithoutSL;
	ArrayList<wire> edgesWithSL;
	int nodesSL;
	
	public Wiring(int n, int c) {
		set = new Hashtable<String,node>();
		//set = new node[n];
		this.nodes = n;
		this.nodesSL = 0;
		this.connections = c;
		this.edgesWithoutSL = new ArrayList<wire>();
		this.edgesWithSL = new ArrayList<wire>();
	}
	
	public String findSet(String j) {//returns label
		//System.out.println("Entering findset");
		if(!this.set.get(j).label.equals(j)) {
			//System.out.println("apple");
			j = findSet(this.set.get(j).label);
		}
		return this.set.get(j).label;
	}
//	public int findSet(int i) {
//		if(this.set[i].label != i) {
//			this.set[i].label = this.findSet(this.set[i].label);
//		}
//		return this.set[i].label;
//	}
		
	public void union(String parent, String child) {//child changes to point to parent
		String label1 = this.findSet(child);
		//System.out.println("Label of child " + label1);
		String label2 = this.findSet(parent);
		//System.out.println("Label of Parent " + label2);
		this.set.get(label1).label = this.set.get(label2).label;
	}
//	public void union(int parent, int child) {//child changes to point to parent
//		int label1 = this.findSet(child);
//		int label2 = this.findSet(parent);
//		this.set[label1].label = this.set[label2].label;
//		
//	}
	
	public class node{
		String label;
		String name;//part of what set
		String type;
		boolean visited;
		ArrayList<String> edges;//name of nodes it's connected to "outwards"
		String switchNeeded;//only really for lights
		public node(String n, String t) {
			this.label = n;
			this.name = n;
			this.type = t;
			this.edges = new ArrayList<String>();
			this.visited = false;
			this.switchNeeded = "";
		}
		public void addEdge(String s) {
			this.edges.add(s);
		}
		public void setSwtichNeeded(String s) {
			this.switchNeeded = s;
		}
		@Override
		public String toString() {
			return name;
		}
	}
	
	public class wire{
		//String source;
		String source;
		//String to;
		String to;
		int weight;
		public wire(String n,String t){
			this.source = n;
			this.to = t;
		}
		
		public void setSource(String s) {
			this.source = s;
		}
		
		public void pointTo(String t) {
			this.to = t;
		}
		
		public void setWeight(int w) {
			this.weight = w;
		}

		@Override
		public String toString() {
			return "The edge's source is " + this.source + " and pointing to " + this.to +". The weight of the edge is: " + this.weight; 
			
		}
	};
	public static void quickSort(ArrayList<wire> edges, int low, int high) {
		if(low < high) {
			int pivot = partition(edges,low,high);
			
			quickSort(edges,low,pivot-1);
			quickSort(edges,pivot+1,high);
		}
	}
	public static int partition(ArrayList<wire> e, int low, int high) {
		double pivot = e.get(high).weight;
		int i = low - 1;
		for(int j = low; j <= high-1; j++) {
			if(e.get(j).weight <= pivot) {
				i++;
				wire temp = e.get(i);
				e.set(i, e.get(j));
				e.set(j, temp);
			}
		}
		wire temp = e.get(i+1);
		e.set(i+1,e.get(high));
		e.set(high, temp);
		return i+1;
	}
	
//	public int findLabel(String name) {
//		for(int i = 0; i < this.edges.length; i++) {
//			if(this.set[i].name.equals(name)) {
//				return i;
//			}
//		}
//		return -1;
//	}
	
	public int kruskalAlgo() {
		//without switches and lights
		quickSort(this.edgesWithoutSL, 0, this.edgesWithoutSL.size()-1);
		//System.out.println(Arrays.toString(this.edgesWithoutSL.toArray()));
		int i = 0;
		int count = 0;
		int edgesAccepeted = 0;
		while(edgesAccepeted < this.nodes-1-this.nodesSL) {
			wire w = this.edgesWithoutSL.get(i);
			//System.out.println(w.toString());
			String uset = this.findSet(w.source);
			//System.out.println(uset);
			String vset = this.findSet(w.to);
			//System.out.println(uset + " - " + vset);
			i++;
			if(!(uset.equals(vset))) {
				//System.out.println("different sets");
				//switch -> light only
				//Break...Switch...Light
				//Break...Outlet, NO switches in between
				//light matches switch
				//Junction boxes can have multiple outgoing edges
				//Lights can connect to other lights of the same switch
				
				count += w.weight;
				edgesAccepeted++;
				this.union(w.source, w.to);
				//System.out.println("Accepted^");
				
			}
		}
		i = 0;
		edgesAccepeted = 0;
		quickSort(this.edgesWithSL, 0, this.edgesWithSL.size()-1);
		//System.out.println(Arrays.toString(this.edgesWithSL.toArray()));
		while(edgesAccepeted < this.nodesSL) {
			wire w = this.edgesWithSL.get(i);
			//System.out.println(w.toString());
			String uset = this.findSet(w.source);
			//System.out.println(uset);
			String vset = this.findSet(w.to);
			//System.out.println(uset + " - " + vset);
			i++;
			if(!(uset.equals(vset))) {
				//System.out.println("different sets");
				//switch -> light only
				//Break...Switch...Light
				//Break...Outlet, NO switches in between
				//light matches switch
				//Junction boxes can have multiple outgoing edges
				//Lights can connect to other lights of the same switch
				if(this.set.get(w.source).type.contentEquals("light") && this.set.get(w.to).type.contentEquals("light")) {
					//catches edge with two lights with different switch
					if(!this.set.get(w.source).switchNeeded.contentEquals(this.set.get(w.to).switchNeeded)) {
						continue;
					}
					
					count += w.weight;
					edgesAccepeted++;
					this.union(w.source, w.to);
					//System.out.println("Accepted^");
					continue;
				}
				if(this.set.get(w.source).type.contentEquals("switch") && this.set.get(w.to).type.contentEquals("switch")) {
					continue;
				}
				if(this.set.get(w.source).type.contentEquals("switch") && this.set.get(w.to).type.contentEquals("light")) {
					if(!this.set.get(w.to).switchNeeded.contentEquals(w.source)) {
						//catches edge with switch connected to a wrong light
						continue;
					}
				}
				if(this.set.get(w.source).type.contentEquals("light") && this.set.get(w.to).type.contentEquals("switch")) {
					if(!this.set.get(w.source).switchNeeded.contentEquals(w.to)) {
						//catches edge with switch connected to a wrong light
						continue;
					}
				}
				if(!this.set.get(w.source).type.contentEquals("switch") && this.set.get(w.to).type.contentEquals("light")){
					//catches edge with something that's not a switch pointing to a light
					//breaker -> light, junction -> light, outlet -> light
					continue;
				}
				if(this.set.get(w.source).type.contentEquals("light") && !this.set.get(w.to).type.contentEquals("switch")){
					//catches edge with light pointing to something not a switch
					//light -> breaker, light -> junction, light -> outlet
					continue;
				}

//				if(this.set.get(w.to).type.contentEquals("switch")) {
//					if(this.set.get(w.source).type.contentEquals("outlet") || this.set.get(w.source).type.contentEquals("box")) {
//						continue;
//					}
//				}
				
				
				count += w.weight;
				edgesAccepeted++;
				this.union(w.source, w.to);
				//System.out.println("Accepted^");
			}
		}
		return count;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int nodes = s.nextInt();
		int connections = s.nextInt();
		Wiring w = new Wiring(nodes,connections);
		String switchNeededForLight = null;
		for(int i = 0; i < nodes; i++) {
			String name = s.next();//name
			String type = s.next();//type
			node n = w.new node(name,type);
			if(type.contentEquals("switch")) {
				switchNeededForLight = name;
				w.nodesSL++;
			}
			if(type.contentEquals("light")) {
				n.switchNeeded = switchNeededForLight;
				w.nodesSL++;
			}
			w.set.put(name, n);
//			node n = w.new node(name,type,i);
//			w.set[i] = n;
		}

		
		for(int i = 0; i < connections; i++) {
			String name = s.next();//source
			String t = s.next();//to
			//System.out.println(name + " - " + t);
			if(w.set.get(name).type.equals("switch") || w.set.get(name).type.equals("light") || 
					w.set.get(t).type.equals("switch") || w.set.get(t).type.equals("light")) {
				//if there's a light or a switch
				wire e = w.new wire(name, t);
				w.set.get(name).edges.add(t);
				e.setSource(name);
				e.pointTo(t);
				e.setWeight(s.nextInt());
				w.edgesWithSL.add(e);
			}
			else {
				wire e = w.new wire(name, t);
				w.set.get(name).edges.add(t);
				e.setSource(name);
				e.pointTo(t);
				e.setWeight(s.nextInt());
				w.edgesWithoutSL.add(e);
			}
//			String source = s.next();
//			String pointTo = s.next();
//			wire e = w.new wire(source,pointTo);
//			e.setWeight(s.nextInt());
//			w.edges[i] = e;
			
		}
		
		
		s.close();
		System.out.println(w.kruskalAlgo());
		
	}
}