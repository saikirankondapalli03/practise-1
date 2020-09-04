package com.educative.interviewrefersher.chapter3;

import java.util.Stack;

public class RectangleArea {
	public int largestRectangleArea(int[] heights) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(-1);
		int maxarea = 0;
		for (int i = 0; i < heights.length; ++i) {
			while (stack.peek() != -1 && heights[stack.peek()] >= heights[i])
				maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
			stack.push(i);
		}
		while (stack.peek() != -1)
			maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
		return maxarea;
	}

	public int largestRectangleAreaDup(int[] heights) {
		int maxarea = 0;
		for (int i = 0; i < heights.length; i++) {
			for (int j = i; j < heights.length; j++) {
				int minheight = Integer.MAX_VALUE;
				for (int k = i; k <= j; k++) {
					System.out.println("i ==" + i + "j==" + j + "k==" + k);
					minheight = Math.min(minheight, heights[k]);
				}

				maxarea = Math.max(maxarea, minheight * (j - i + 1));
			}
		}
		return maxarea;
	}

	public int calculateArea(int[] heights, int start, int end) {
		if (start > end)
			return 0;
		int minindex = start;
		for (int i = start; i <= end; i++)
			if (heights[minindex] > heights[i])
				minindex = i;
		int localArea = heights[minindex] * (end - start + 1);
		int calculateArea1 = calculateArea(heights, start, minindex - 1);
		int calculateArea2 = calculateArea(heights, minindex + 1, end);
		int maxArea=Math.max(localArea,Math.max(calculateArea1, calculateArea2));
		return maxArea;
	}

	public int largestRectangleAreaValue(int[] heights) {
		return calculateArea(heights, 0, heights.length - 1);
	}

	public static void main(String[] aargs) {
		int[] array = { 6, 7, 5,  4, 5, 9,2, 3 };
		RectangleArea r = new RectangleArea();
		int area = r.largestRectangleArea(array);
		System.out.println(area);
	}
}
