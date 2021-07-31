package test;

import java.util.Arrays;

public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] test = new int[5][5];
		if(test[test.length] == null) {
			System.out.println("fuck");
		}
		System.out.println(Arrays.deepToString(test));
	}

}
