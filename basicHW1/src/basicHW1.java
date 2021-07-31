import java.util.Scanner;
public class basicHW1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		//System.out.println("Please enter number of tables: ");
		int tbl = s.nextInt();
		//System.out.println("Please enter number of vistors: ");
		int ppl = s.nextInt();
		int[] tblPos = new int[tbl];
		for(int i = 0; i < tbl; i++) {
			//System.out.println("Please enter position of the tables: ");
			tblPos[i] = s.nextInt();
		}
		//System.out.println("apple");
		s.close();
		int mid = (tblPos[tblPos.length-1]-tblPos[0])/2 + 1;
		System.out.println(minDist(tblPos, ppl, 1, tblPos[tblPos.length-1]-tblPos[0],mid,-1));
		//System.out.println("This is the max min dist for the tables and vistors entered: " + minDist(tblPos, ppl, tblPos[0], tblPos[tblPos.length-1],mid));
	}
	//ppl-1 represents the first person being placed at the first table
	public static int minDist(int[] tblPos, int ppl, int lower, int upper, int dist, int works) {
		//System.out.println("This is lower bound: " + lower + ". And this is Upper Bound: " + upper);
		if(upper >= lower) {
			int mid = ((upper + lower) / 2);
			if(upper == lower) {
				//System.out.println("Trying out this distance: "+mid);
				if(doesItWork(tblPos,ppl,mid)) {
					//System.out.println("Since the bound ended here and it worked, "+mid+" is the best distance.");
					return mid;
				}
				else {
					//System.out.println("Didn't work. The last distance was the best.");
					return works;
				}
			}
			if(ppl == 2) {
				return upper-lower;
			}
		
			//System.out.println("Trying out this distance: "+ mid);
			if(doesItWork(tblPos,ppl,mid)) {//if true, we want to see if it can be increased
				return minDist(tblPos,ppl,mid+1,upper,mid,mid);
			}
			else {//if false, that means the distance tried was too big
				return minDist(tblPos,ppl,lower, mid-1, mid,works); // (mid + lower - 1)/2
			}
		}
		return works;
	}
	
	public static Boolean doesItWork(int[] tblPos, int ppl, int dist) {
		int posVal = tblPos[0];
		int pplLeft = ppl-1;//minus one because the first seat is always taken
		if(pplLeft == 1) {//two ppl means just put them on the far ends
			return true;
		}
		for(int i = 0; i < tblPos.length; i++) {
			if(tblPos[i]-posVal >= dist) {
				posVal = tblPos[i];
				pplLeft--;
				if(pplLeft == 0) {
					return true;
				}
			}
		}
		return false;
	}
}
