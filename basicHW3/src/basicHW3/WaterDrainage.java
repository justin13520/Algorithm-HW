//package basicHW3;
import java.util.*;
public class WaterDrainage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int numOfCase = s.nextInt();
		String[] cities = new String[numOfCase];
		ArrayList<int[][]> cases = new ArrayList<int[][]>();
		ArrayList<int[][]> visited = new ArrayList<int[][]>();
		for(int i = 0; i < numOfCase; i++) {
			cities[i] = s.next();
			int rows = s.nextInt();
			int columns = s.nextInt();
			int[][] grid = new int[rows][columns];//row column
			int[][] dynamic = new int[rows][columns];
			for(int k = 0; k < rows; k++) {
				for(int j = 0; j < columns; j++) {
					grid[k][j] = s.nextInt();
				}
			}
			for(int x = 0; x < rows; x++) {
				for(int y = 0; y < columns; y++) {
					dynamic[x][y] = -1;
				}
			}
			cases.add(grid);
			visited.add(dynamic);
		}
		
		WaterDrainage wd = new WaterDrainage(cases, cities, visited);
		int currentMax = 0;
		for(int i = 0; i < wd.cases.size(); i++) {
			int[][] currentCityGrid = wd.cases.get(i);
			for(int k = 0; k < currentCityGrid.length; k++) {
				for(int j = 0; j < currentCityGrid[0].length; j++) {
					int possibleMax = wd.findMaxes(currentCityGrid,k,j,i,wd.visited.get(i));
					if(wd.max[i] < possibleMax) {
						wd.max[i] = possibleMax;
					}
				}
			}
		}
		//System.out.println(Arrays.deepToString(wd.cities));
//		for(int j = 0; j < wd.cases.size(); j++) {
//			System.out.println(Arrays.deepToString(wd.cases.get(j)));
//		}
		for(int i = 0; i < wd.visited.size(); i++) {
			System.out.println(wd.cities[i] + ": " + wd.max[i]);
			//System.out.println(Arrays.deepToString(wd.visited.get(i)));
		}
		s.close();
	}
	
	
	ArrayList<int[][]> cases;//grids of each case
	String[] cities;//city names of each case
	int[] max;//store the max from each case
	ArrayList<int[][]> visited;
	public WaterDrainage(ArrayList<int[][]> g, String[] c, ArrayList<int[][]> v) {
		cases = g;
		cities = c;
		visited = v;
		max = new int[cities.length];
	}
	
	
	//a[row][column] -> a[y][x]
	public int findMaxes(int[][] grid, int startRow, int startColumn, int currentCity, int[][] visited) {//grid im using, where im starting, 
		//issues: updates the path too early, doesn't add to the current path recusively right now
		//visited stores the best length for that node
		int current = grid[startRow][startColumn];
		int topPath = 0;
		int bottomPath = 0;
		int leftPath = 0;
		int rightPath = 0;
		if(visited[startRow][startColumn] != -1) {
			return Math.max(rightPath, Math.max(leftPath, Math.max(topPath, bottomPath)))+1;
		}
		else {
			if(this.exists(grid, startRow-1, startColumn)) {//checks top 
				if(grid[startRow-1][startColumn] < current) {
					if(visited[startRow-1][startColumn] != -1) {//visited
						if(grid[startRow-1][startColumn] < current) {//go up if it's smaller
							topPath += visited[startRow-1][startColumn] + 1;//add 
						}
					}
					else {//not visited yet
						int pathLength = findMaxes(grid, startRow-1, startColumn, currentCity, visited);
	//					if(pathLength > visited[startRow][startColumn]) {
							topPath += pathLength;
	//					}
					}
				}
			}
			if(this.exists(grid, startRow+1, startColumn)) {//checks bottom
				if(grid[startRow+1][startColumn] < current) {
					if(visited[startRow+1][startColumn] != -1) {
						if(grid[startRow+1][startColumn] < current) {//go up if it's smaller
							bottomPath += visited[startRow+1][startColumn] + 1;//add 
						}				
					}
					else {
						int pathLength = findMaxes(grid, startRow+1, startColumn, currentCity, visited);
						bottomPath += pathLength;
	//					if(pathLength > visited[startRow][startColumn]) {
	//						
	//					}
					}
				}
			}
			if(this.exists(grid, startRow, startColumn-1)) {//checks left
				if(grid[startRow][startColumn-1] < current) {
					if(visited[startRow][startColumn-1] != -1 ) {
						if(grid[startRow][startColumn-1] < current) {//go up if it's smaller
							leftPath += visited[startRow][startColumn-1] + 1;//add 
						}					
					}
					else {
						int pathLength = findMaxes(grid, startRow, startColumn-1, currentCity, visited);
						leftPath += pathLength;
	//					if(pathLength > visited[startRow][startColumn]) {
	//						
	//					}
					}
				}
			}
			if(this.exists(grid, startRow, startColumn+1)) {//checks right 
				if(grid[startRow][startColumn+1] < current) {
					if(visited[startRow][startColumn+1] != -1) {
						if(grid[startRow][startColumn+1] < current) {//go up if it's smaller
							rightPath += visited[startRow][startColumn+1] + 1;//add 
						}					
					}
					else {
						int pathLength = findMaxes(grid, startRow, startColumn+1, currentCity, visited);
						rightPath += pathLength;
	//					if(pathLength > visited[startRow][startColumn]) {
	//						
	//					}
					}
				}
			}
			visited[startRow][startColumn] = Math.max(rightPath, Math.max(leftPath, Math.max(topPath, bottomPath)));
			if(this.max[currentCity] < Math.max(rightPath, Math.max(leftPath, Math.max(topPath, bottomPath)))) {
				this.max[currentCity] = Math.max(rightPath, Math.max(leftPath, Math.max(topPath, bottomPath)));
			}
			return Math.max(rightPath, Math.max(leftPath, Math.max(topPath, bottomPath)))+1;
		}
	}
	public boolean exists(int[][] grid, int startRow, int startColumn) {
		try {
			int a = grid[startRow][startColumn];
			return true;
		}
		catch(IndexOutOfBoundsException e) {
			return false;
		}
	}
}
