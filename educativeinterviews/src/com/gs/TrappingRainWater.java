package com.gs;

public class TrappingRainWater {
	
	public static int trap(int[] height) {
		int n = height.length;
		int left = 0;
		int right = n - 1;
		int maxLeft = 0;
		int maxRight = 0;
		int result = 0;
		while (left < right) {
			int currLeft = height[left];
			int currRight = height[right];

 			if (maxLeft < currLeft) {
				maxLeft = currLeft;
			} else {
				result += (maxLeft - currLeft);
			}

			if (maxRight < currRight) {
				maxRight = currRight;
			} else {
				result += (maxRight - currRight);
			}

			if (currRight > currLeft)
				left += 1;
			else
				right -= 1;
		}

		return result;
	}

	public static void main(String[] args) {
		System.out.println(TrappingRainWater.trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
	}
}
