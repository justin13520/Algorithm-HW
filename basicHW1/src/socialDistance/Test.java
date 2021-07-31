package socialDistance;

import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = new int[5];
		arr[0] = 6;
		System.out.println(Arrays.toString(arr));
		c(arr);
		System.out.println(Arrays.toString(arr));
		int a = 5;
		int b = a;
		b++;
		System.out.println(b);
		System.out.println(a);
	}
	public static void c(int[] arr) {
		arr[0] = 20;
	}
}
