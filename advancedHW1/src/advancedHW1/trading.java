package advancedHW1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class trading {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		//System.out.println("apple10");
		//System.out.println("apple");
		while(s.hasNextInt()) {
			int starCount = s.nextInt();
			if(starCount == 0) {
				break;//System.exit(0) didn't work, s.close didn't work
			}
			else {
				Point[] points = new Point[starCount];
				for(int i = 0; i < starCount; i++) {
					Point a = new Point(s.nextDouble(),s.nextDouble(),s.nextDouble());
					points[i] = a;
				}
				Point[] px = points.clone();
				quickSortX(px,0,px.length-1);
				if(divide(px) < 10000.0) {
					System.out.format("%.4f",divide(px));
					System.out.println();
				}
				else {
					System.out.println("infinity");
				}
			}
		//System.out.println(closestDist(points));
		}
		s.close();
	}
	
	public static double divide(Point[] px) {
		int mid = px.length/2;
		if(px.length <= 3) {
			return baseCase(px);
		}
		double minL = divide(Arrays.copyOfRange(px, 0, mid));
		double minR = divide(Arrays.copyOfRange(px, mid, px.length));
		double stripBound = Math.min(minL, minR);
		ArrayList<Point> stripY = new ArrayList<Point>();
		ArrayList<Point> stripZ = new ArrayList<Point>();
		for(int i = 0; i < px.length; i++) {
			if(px[i].getX() >= px[mid].x-stripBound && px[i].getX() <= px[mid].x+stripBound) {
				stripY.add(px[i]);
				stripZ.add(px[i]);
			}
		}
		quickSortY(stripY,0,stripY.size()-1);
		quickSortZ(stripZ,0,stripZ.size()-1);
		double stripMin = Math.min(stripMinY(stripY,stripBound), stripMinZ(stripZ,stripBound));
		return Math.min(stripBound, stripMin);
		
	}
	public static double baseCase(Point[] p) {
		double smallest = 100001;
		for(int i = 0; i < p.length; i++) {
			for(int j = i+1; j < p.length; j++) {
				if(smallest > dist(p[i],p[j])) {
					smallest = dist(p[i],p[j]);
				}
			}
		}
		return smallest;
	}
	
	public static double dist(Point p1, Point p2) {
		double a = (p1.x - p2.x)*(p1.x - p2.x);
		double b = (p1.y - p2.y)*(p1.y - p2.y);
		double c = (p1.z - p2.z)*(p1.z - p2.z);
		return Math.sqrt(a+b+c);
	}
	
	public static double stripMinY(ArrayList<Point> strip, double bound) {
		double min = bound;
		for(int i = 0; i < strip.size(); i++) {
			for(int j = i+1; j < strip.size() &&Math.abs(strip.get(j).y - strip.get(i).y) < bound; j++) {
				if(dist(strip.get(j),strip.get(i)) < min) {
					min = dist(strip.get(j),strip.get(i));
				}
			}
		}
		return min;
	}

	public static double stripMinZ(ArrayList<Point> strip, double bound) {
		double min = bound;
		for(int i = 0; i < strip.size(); i++) {
			for(int j = i+1; j < strip.size() && Math.abs(strip.get(j).z - strip.get(i).z) < bound; j++) {
				if(dist(strip.get(j),strip.get(i)) < min) {
					min = dist(strip.get(j),strip.get(i));
				}
			}
		}
		return min;
	}
	
	public static void quickSortX(Point[] p, int low, int high) {
		if(low < high) {
			int pivot = partitionX(p,low,high);
			
			quickSortX(p,low,pivot-1);
			quickSortX(p,pivot+1,high);
		}
	}
	public static int partitionX(Point[] p, int low, int high) {
		double pivot = p[high].getX();
		int i = low - 1;
		for(int j = low; j <= high-1; j++) {
			if(p[j].getX() <= pivot) {
				i++;
				Point temp = p[i];
				p[i] = p[j];
				p[j] = temp;
			}
		}
		Point temp = p[i+1];
		p[i+1] = p[high];
		p[high] = temp;
		return i+1;
	}

	public static void quickSortY(ArrayList<Point> p, int low, int high) {
		if(low < high) {
			int pivot = partitionY(p,low,high);
			
			quickSortY(p,low,pivot-1);
			quickSortY(p,pivot+1,high);
		}
	}
	public static int partitionY(ArrayList<Point> p, int low, int high) {
		double pivot = p.get(high).getY();
		int i = low - 1;
		for(int j = low; j <= high-1; j++) {
			if(p.get(j).getY() <= pivot) {
				i++;
				Point temp = p.get(i);
				p.set(i, p.get(j));
				p.set(j, temp);
			}
		}
		Point temp = p.get(i+1);
		p.set(i+1, p.get(high));
		p.set(high, temp);
		return i+1;
	}
	
	public static void quickSortZ(ArrayList<Point> p, int low, int high) {
		if(low < high) {
			int pivot = partitionZ(p,low,high);
			
			quickSortZ(p,low,pivot-1);
			quickSortZ(p,pivot+1,high);
		}
	}
	public static int partitionZ(ArrayList<Point> p, int low, int high) {
		double pivot = p.get(high).getZ();
		int i = low - 1;
		for(int j = low; j <= high-1; j++) {
			if(p.get(j).getZ() <= pivot) {
				i++;
				Point temp = p.get(i);
				p.set(i, p.get(j));
				p.set(j, temp);
			}
		}
		Point temp = p.get(i+1);
		p.set(i+1, p.get(high));
		p.set(high, temp);
		return i+1;
	}
}

class Point{
	double x;
	double y;
	double z;
	
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public double getZ() {
		return this.z;
	}
	public String toString() {
		return "("+this.x+", "+this.y+", "+this.z+")";
	}
}