//package advancedHW4;
import java.util.*;
public class Daycare{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		
		while(true) {
			if(!s.hasNextInt()) {
				break;
			}
			int rooms = s.nextInt();
			ArrayList<classRoom> sort = new ArrayList<classRoom>();
			PriorityQueue<classRoom> q = new PriorityQueue<classRoom>(new Comparator<classRoom>() {
					public int compare(classRoom room1, classRoom room2) {
						double c = room1.ratio - room2.ratio;
						if(c > 0) {
							return 1;
						}
						else if(c < 0) {
							return -1;
						}
						return 0;
					}
				}
			);
			
			for(int i = 0; i < rooms; i++) {
				classRoom r = new classRoom(s.nextInt(),s.nextInt());
				sort.add(r);
			}
			
			Collections.sort(sort,new Comparator<classRoom>() {
				public int compare(classRoom room1, classRoom room2) {
					return room1.before - room2.before;
				}
			});
			//System.out.println(sort.toString());
			classRoom leastPopulated = sort.get(0);
			//System.out.println(leastPopulated);
			for(int j = 1; j < sort.size(); j++) {
				q.add(sort.get(j));
			}
			System.out.println(extraChildren(leastPopulated, q));
		}
		s.close();
	}
	public static int extraChildren(classRoom initial, PriorityQueue<classRoom> q) {
		int capacityOfClasses = initial.after;
		int trailerCapacity = initial.before;
		while(q.size() != 0) {
			classRoom r = q.remove();
			if(r.before > capacityOfClasses) {//there's too many children to fit into empty spaces
				trailerCapacity += r.before - capacityOfClasses;//add these children that are leftovers into trailer
				capacityOfClasses = 0;//set spaces left in classes to 0
			}
			else {
				capacityOfClasses -= r.before;//current room's children moved into other rooms
			}
			capacityOfClasses += r.after;//add to capacity because we just renovated. If smaller, the children are in another room still
		}
		return trailerCapacity;
	}
	
}

class classRoom {
	int before;
	int after;
	double ratio;
	public classRoom(int b, int a) {
		before = b;
		after = a;
		ratio = ((double) b)/a;
	}
	@Override
	public String toString() {
		return "Before capacity: " + this.before + " And After Capacity: "+ this.after;
	}
}
