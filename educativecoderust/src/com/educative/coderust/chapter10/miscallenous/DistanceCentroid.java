package com.educative.coderust.chapter10.miscallenous;

import java.util.ArrayList;

class DistanceCentroid {
	public static Point shortestDistanceTraveled(int m, ArrayList<Point> points) {
		Point minPt = new Point(0, 0);
		double x = 0;
		double y = 0;

		// calculate the centroid
		Point centroid = new Point(0, 0);
		for (int i = 0; i < points.size(); i++) {
			x += points.get(i).getX();
			y += points.get(i).getY();
		}
		centroid.setX((int) Math.round(x / points.size()));
		centroid.setY((int) Math.round(y / points.size()));

		// initialize the minPt to centroid
		minPt.setX(centroid.getX());
		minPt.setY(centroid.getY());

		double minDistance = minPt.calculateSumOfDistances(points);
		
		System.out.println(centroid.getX() + "centroid.getX()" + centroid.getY());
		// checking points surrounding the potential centroid
		// x,y -> centroid
		// 8 point
		// x-1 , y
		// x+1 , y
		// x, y-1
		// x, y+1
		// x-1, y-1
		// x+1, y+1
		// x-1 ,y+1
		// x+1 , y-1
		int startX = minPt.getX() - 1; //2
		int endX = minPt.getX() + 1; //4
		for (int i = startX; i <=endX; i++) {
			int startY = minPt.getY() - 1; // 1
			int endY = minPt.getY() + 1; // 3
			for (int j = startY; j <= endY; j++) {
				if (i < 1 || j > m) {
					continue;
				}
				System.out.println("X=>" + i + "Y=>" + j);
				Point pt = new Point(i, j);
				double distance = pt.calculateSumOfDistances(points);
				if (distance < minDistance) {
					minDistance = distance;
					minPt.setX(pt.getX());
					minPt.setY(pt.getY());
				}
			}
		}

		return minPt;
	}

	public static void main(String[] args) {

		int[] arr = { 5, 10, 8 };
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(1, 2));
		points.add(new Point(3, 3));
		points.add(new Point(4, 2));

		System.out.println("Grid 5x5 and values ((1, 2), (3, 3), (4, 2))");
		DistanceCentroid d = new DistanceCentroid();
		Point pt = d.shortestDistanceTraveled(arr[0], points);
		System.out.println("Shortest Distance Point = p(" + pt.getX() + ", " + pt.getY() + ")");
		/*
		 * ArrayList<Point> points2 = new ArrayList<Point>(); points2.add(new Point(1,
		 * 1)); points2.add(new Point(3, 5)); points2.add(new Point(6, 2));
		 * points2.add(new Point(7, 7)); points2.add(new Point(8, 4));
		 * 
		 * System.out.
		 * println("\nGrid 10x10 and values ((1, 1), (3, 5), 24, 2), (7, 7), (8,4))");
		 * pt = d.shortestDistanceTraveled(arr[1], points2);
		 * System.out.println("Shortest Distance Point = p(" + pt.getX() + ", " +
		 * pt.getY() + ")");
		 * 
		 * ArrayList<Point> points3 = new ArrayList<Point>(); points3.add(new Point(4,
		 * 3)); points3.add(new Point(5, 5)); points3.add(new Point(2, 7));
		 * 
		 * System.out.println("\nGrid 8x8 and values ((4, 3), (5, 5), (2, 7))"); pt =
		 * d.shortestDistanceTraveled(arr[2], points3);
		 * System.out.println("Shortest Distance Point = p(" + pt.getX() + ", " +
		 * pt.getY() + ")");
		 */
	}
}