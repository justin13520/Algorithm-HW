package test;

import java.util.Arrays;

public class Simulation {
	int x0 = 1000; //starting seed
	int a = 24693; //multiplier
	int c = 3967; //increment
	double K = Math.pow(2, 18);  //modulus
	double xi = x0; //starts at x0 and will start start to change as generateProbability is called
	double randNum;
	
	/*
	 * STUFF THAT NEEDS TO BE PRINTED OUT
	 */
	// mean distance
	final static double TRUEMEAN = (57.0) * Math.pow(Math.PI/2, .5);
	//arrays to store all the Mn with sample size n
	double[] M5 = new double[550];
	double[] M10 = new double[550];
	double[] M15 = new double[550];
	double[] M30 = new double[550];
	
	public static void main(String[] args) {
		Simulation sim = new Simulation();
//		for(int i = 1; i <= 53; i++) {
//			double rand = sim.generateProbability();
//			
//			if(i == 53 || i == 52 || i == 51)
//				System.out.println(i + "th random # was: " + rand);
//		}
		System.out.println("Expected distance: " + TRUEMEAN);
		
//		//storing and printing M10
		System.out.println("-----------------now printing M5-----------------------");
		for(int i = 0; i < 550; i++) {
			sim.M5[i] = sim.generateMeanDistance(5);
			System.out.println(sim.M5[i]);
		}
//		
//		//storing and printing M30
		System.out.println("-----------------now printing M10-----------------------");
		for(int i = 0; i < 550; i++) {
			sim.M10[i] = sim.generateMeanDistance(10);
			System.out.println(sim.M10[i]);
		}
//		
//		//storing and printing M50
		System.out.println("-----------------now printing M15-----------------------");
		for(int i = 0; i < 550; i++) {
			sim.M15[i] = sim.generateMeanDistance(15);
			System.out.println(sim.M15[i]);
		}
//		
//		//storing and printing M100
		System.out.println("-----------------now printing M30-----------------------");
		for(int i = 0; i < 550; i++) {
			sim.M30[i] = sim.generateMeanDistance(30);
			System.out.println(sim.M30[i]);
		}
//		
//		//storing and printing M250
//		System.out.println("-----------------now printing M250-----------------------");
//		for(int i = 0; i < 110; i++) {
//			sim.M250[i] = sim.generateMeanDistance(250);
//			System.out.println(sim.M250[i]);
//		}
//		
//		//storing and printing M500
//		System.out.println("-----------------now printing M500-----------------------");
//		for(int i = 0; i < 110; i++) {
//			sim.M500[i] = sim.generateMeanDistance(500);
//			System.out.println(sim.M500[i]);
//		}
//		
//		//storing and printing M1000
//		System.out.println("-----------------now printing M1000-----------------------");
//		for(int i = 0; i < 110; i++) {
//			sim.M1000[i] = sim.generateMeanDistance(1000);
//			System.out.println(sim.M1000[i]);
//		}
	}
	
//	public void printArray(double[] array) {
//		//prints array out
//		System.out.println(Arrays.toString(array));
//	}
	
	public double generateProbability() {
		//generates random probabilities
		xi = (a*xi + c) % K; 
		return xi/K;
	}
	
	public double generateRandomDistance() {
		//formula for getting distance from probability:
		//distance(x) = sqrt(-2ln(1-randomProbability)/a^2)
		//generates random distance with the formula
		double prob = generateProbability();
		double numerator = -2 * Math.log(1-prob);
		double denominator = Math.pow(1/57.0, 2);
		
		return Math.pow(numerator/denominator, .5);
	}
	
	public double generateMeanDistance(int sampleSize){
		//generates Mn with sample size n
		double sumOfDistances = 0;
		
		for(int i = 0; i < sampleSize; i++) {
			sumOfDistances += generateRandomDistance();
		}
		
		return sumOfDistances/sampleSize;
	}
}
