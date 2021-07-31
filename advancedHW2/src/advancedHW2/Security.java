//package advancedHW2;
import java.util.*;


public class Security {
	//algorithm: get the intervals from input, test each interval, add valid interval size, return
	//question: how get to get the interval from the input I'm looking through the edges
	Hashtable<Integer,room> locks;
	ArrayList<int[]> badgeRanges;
	ArrayList<int[]> uniqueRanges;
	int rooms;
	int start;
	int destination;
	int badgeLimit;
	
	public Security(int r, int s, int d, int b) {
		locks = new Hashtable<Integer, room>();//key is room number, values are those rooms
		badgeRanges = new ArrayList<int[]>();
		uniqueRanges = new ArrayList<int[]>();
		rooms = r;
		start = s;
		destination = d;
		badgeLimit = b;
	}
	public void quickSort(ArrayList<Integer> walls, int low, int high) {
		if(low < high) {
			int pivot = partition(walls,low,high);
			
			quickSort(walls,low,pivot-1);
			quickSort(walls,pivot+1,high);
		}
	}
	public int partition(ArrayList<Integer> e, int low, int high) {
		double pivot = e.get(high);
		int i = low - 1;
		for(int j = low; j <= high-1; j++) {
			if(e.get(j) <= pivot) {
				i++;
				int temp = e.get(i);
				e.set(i, e.get(j));
				e.set(j, temp);
			}
		}
		int temp = e.get(i+1);
		e.set(i+1,e.get(high));
		e.set(high, temp);
		return i+1;
	}
	
	public void quickSortEdges(ArrayList<lock> edges, int low, int high) {
		if(low < high) {
			int pivot = partitionEdges(edges,low,high);
			
			quickSortEdges(edges,low,pivot-1);
			quickSortEdges(edges,pivot+1,high);
		}
	}
	public int partitionEdges(ArrayList<lock> e, int low, int high) {
		double pivot = e.get(high).startBadge;
		int i = low - 1;
		for(int j = low; j <= high-1; j++) {
			if(e.get(j).startBadge <= pivot) {
				i++;
				lock temp = e.get(i);
				e.set(i, e.get(j));
				e.set(j, temp);
			}
		}
		lock temp = e.get(i+1);
		e.set(i+1,e.get(high));
		e.set(high, temp);
		return i+1;
	}
	
	public int DFS(int start, int dest) {
		int count = 0;
		ArrayList<int[]> unique = new ArrayList<int[]>();
		
		for(int i = 0; i < this.uniqueRanges.size(); i++) {
			int OGSize = unique.size();
			boolean[] visited = new boolean[this.rooms];
			DFSUtil(this.start, this.destination, this.uniqueRanges.get(i), unique,visited);
			if(OGSize < unique.size()) {
				count += unique.get(unique.size()-1)[1] - unique.get(unique.size()-1)[0] + 1;
				this.locks.get(this.destination).found = false;
			}
		}
		return count;
	}
	public void DFSUtil(int start, int dest, int[] currentRange, ArrayList<int[]> unique, boolean[] visited) {
		visited[this.locks.get(start).roomNumber-1] = true;
		if(start == dest) {
			unique.add(currentRange);
			//this.locks.get(this.destination).found = true;
			return;
		}		
		for(lock l : this.locks.get(start).edges) {//for every edge
			if(visited[this.destination-1]) {
				break;
			}
			if(!visited[l.to-1]) {
				if(currentRange[0] >= l.startBadge && currentRange[0] <= l.endBadge) {
					DFSUtil(l.to,dest,currentRange,unique, visited);
				}
			}
		}
		
	}
	
	
	public class room{
		int roomNumber;
		boolean visited;
		ArrayList<lock> edges;
		boolean found;
		public room(int rn) {
			found = false;
			edges = new ArrayList<lock>();
			roomNumber = rn;
			visited = false;
		}
		@Override
		public String toString() {
			return "Roomnumber: " + roomNumber;
		}
	};
	public class lock{//edge
		int startBadge;
		int endBadge;
		int to;
		int from;
		public lock(int s, int e, int t, int f) {
			startBadge = s;
			endBadge = e;
			to = t;
			from = f;
		}
		@Override
		public String toString() {
			return this.from + "-->" + to;
		}
	};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int rooms = s.nextInt();
		int locks = s.nextInt();
		int badgeNums = s.nextInt();
		int start = s.nextInt();
		int destination = s.nextInt();
		Security e = new Security(rooms,start, destination, badgeNums);
		HashSet<Integer> wall = new HashSet<Integer>();
		for(int i = 0; i < locks; i++) {
			int startNode = s.nextInt();
			int endNode = s.nextInt();
			int startBadge = s.nextInt();
			int endBadge = s.nextInt();
			room sr = e.new room(startNode);
			room er = e.new room(endNode);
			lock l = e.new lock(startBadge,endBadge,endNode, startNode);
			int[] badgeRange = {startBadge, endBadge};
			e.badgeRanges.add(badgeRange);
			wall.add(startBadge);
			wall.add(endBadge);
			if(e.locks.keySet().contains(startNode)) {//just add the room its pointing to to the arraylist
				e.locks.get(startNode).edges.add(l);
			}
			if(!e.locks.containsKey(startNode)){//creates a new start 
				sr.edges.add(l);
				e.locks.put(startNode, sr);
			}
			if(!e.locks.containsKey(endNode)){//creates a new end
				e.locks.put(endNode, er);
			}
		}
		ArrayList<Integer> walls = new ArrayList<Integer>(wall); 
		e.quickSort(walls, 0, walls.size()-1);
		
		for(room r:e.locks.values()) {
			e.quickSortEdges(r.edges, 0, r.edges.size()-1);
			
		}
		e.makeUniqueRanges(walls);
		System.out.println(e.DFS(start,destination));
		
		
		s.close();
	}
	private void makeUniqueRanges(ArrayList<Integer> walls) {
		for(int i = 0; i < walls.size()-1; i++) {
			int[] r = {walls.get(i),walls.get(i)};
			this.uniqueRanges.add(r);
			if(walls.get(i)+1 <= walls.get(i+1)-1) {
				int[] r1 = {walls.get(i)+1,walls.get(i+1)-1};
				this.uniqueRanges.add(r1);
			}
		}
		
		int r[] = {walls.get(walls.size()-1),walls.get(walls.size()-1)};
		this.uniqueRanges.add(r);
		
	}
}
