//package basicHw4;


import java.util.*;
public class courseFlow {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		while(s.hasNext()) {
			
			int requests = s.nextInt();
			if(requests == 0)
				break;
			int classes = s.nextInt();
			int minClasses = s.nextInt();
			
			HashMap<String, ArrayList<String>> students = new HashMap<String, ArrayList<String>>();
			HashMap<String, Integer> classCapacity = new HashMap<String, Integer>();
			HashMap<String, Integer> studentIndex1 = new HashMap<String, Integer>();
			HashMap<String, Integer> classIndex1 = new HashMap<String, Integer>();
			HashMap<Integer, String> classIndex2 = new HashMap<Integer, String>();

			for(int i = 0; i < requests; i++) {//gets students with their desired courses
				String name = s.next();
				if(students.containsKey(name)) {
					students.get(name).add(s.next());
				}
				else {
					ArrayList<String> courses = new ArrayList<String>();
					courses.add(s.next());
					students.put(name, courses);
				}
			}
			int index = 0;
			for(String name:students.keySet()) {//Set index for students
				studentIndex1.put(name, index);
				index++;
			}

			int numStudent = students.keySet().size();
			int totalCapacity = 0;
			for(int j = 0; j < classes; j++) {//get classes with its capacity
				String n = s.next();
				int c = s.nextInt();
				totalCapacity += c;
				classCapacity.put(n,c);
			}
			
			//System.out.println(minClasses * numStudent);
			if(totalCapacity < minClasses * numStudent) {//base case
				System.out.println("No");
				break;
			}
			
			
			index = 0;
			for(String name:classCapacity.keySet()) {//set index for classes
				classIndex1.put(name, index);
				classIndex2.put(index, name);
				index++;
			}
			
			int[][] studentsToClasses = new int[numStudent][classes];
			for(int a = 0; a < numStudent; a++) {
				for(int b = 0; b < classes; b++) {
					studentsToClasses[a][b] = 0;
				}
			}
			for(String student:students.keySet()) {
				for(String course:students.get(student)) {
					studentsToClasses[studentIndex1.get(student)][classIndex1.get(course)] = 1;
				}
			}
			
			int[] sink = new int[classes];
			int[] source = new int[numStudent];

//			System.out.println(students.toString());
//			System.out.println(classCapacity.toString());
			if(sendFlow(numStudent, classes, studentsToClasses, sink, source, minClasses, classCapacity, classIndex2) == minClasses * numStudent) {
				//System.out.println(sendFlow(numStudent, classes, studentsToClasses, sink, source, minClasses, classCapacity, classIndex2));
				System.out.println("Yes");
			}
			else {
				//System.out.println(sendFlow(numStudent, classes, studentsToClasses, sink, source, minClasses, classCapacity, classIndex2));
				System.out.println("No");
			}
		}
		s.close();
	}
	public static int sendFlow(int numStudent, int classes, int[][] edgeGraph, int[] sink, int[] source, int required,
			HashMap<String,Integer> classCapacity, HashMap<Integer,String> classIndex) {
		int flow = 0;
		for(int i = 0; i < numStudent; i++) {
			for(int j = 0; j < classes; j++){
				if(edgeGraph[i][j] == 1 && sink[j] <  classCapacity.get(classIndex.get(j)) && source[i] < required) {//if there is an edge
					sink[j] += 1;
					source[i] += 1;
					flow += 1;
				}
			}
		}
		return flow;
	}

}
