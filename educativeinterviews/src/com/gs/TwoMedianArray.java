package com.gs;

public class TwoMedianArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public double result(int[]num1,int[] num2) {
		int i = 0, j = 0, k = 0;
		int result[] = new int[num1.length + num2.length];
		while (i < num1.length && j < num2.length) {
			result[k++] = (num1[i] < num2[j]) ? num1[i++] : num2[j++];
		}
		while (i < num1.length)
			result[k++] = num1[i++];
		while (j < num2.length)
			result[k++] = num2[j++];
		if (result.length % 2 != 0)
			return result[result.length / 2];
		else
			return ((double) result[result.length / 2] + (double) result[result.length / 2 - 1]) / 2.0d;
	}
}
