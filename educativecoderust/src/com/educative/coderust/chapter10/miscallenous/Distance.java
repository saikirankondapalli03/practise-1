package com.educative.coderust.chapter10.miscallenous;

import java.util.ArrayList;

class Point {
	private int x;
	private int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	int getX() {
		return x;
	}

	void setX(int x) {
		this.x = x;
	}

	int getY() {
		return y;
	}

	void setY(int y) {
		this.y = y;
	}

	double calculateDistance(Point p) {
		double distance;
		distance = Math.sqrt((p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y));
		return distance;
	}

	double calculateSumOfDistances(ArrayList<Point> points) {
		double distanceSum;
		distanceSum = 0;
		for (int i = 0; i < points.size(); i++) {
			distanceSum += this.calculateDistance(points.get(i));
		}
		return distanceSum;
	}
};

class Distance {
	public static Point shortestDistanceTraveled(int m, ArrayList<Point> points) {
		Point pt = new Point(1, 1);
		double minDistance = pt.calculateSumOfDistances(points);
		Point minPt = new Point(pt.getX(), pt.getY());

		double distance;
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= m; j++) {
				pt.setX(i);
				pt.setY(j);
				distance = pt.calculateSumOfDistances(points);

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
		Distance d = new Distance();
		Point pt = d.shortestDistanceTraveled(arr[0], points);
		System.out.println("Shortest Distance Point = p(" + pt.getX() + ", " + pt.getY() + ")");

		ArrayList<Point> points2 = new ArrayList<Point>();
		points2.add(new Point(1, 1));
		points2.add(new Point(3, 5));
		points2.add(new Point(6, 2));
		points2.add(new Point(7, 7));
		points2.add(new Point(8, 4));

		System.out.println("\nGrid 10x10 and values ((1, 1), (3, 5), 24, 2), (7, 7), (8,4))");
		pt = d.shortestDistanceTraveled(arr[1], points2);
		System.out.println("Shortest Distance Point = p(" + pt.getX() + ", " + pt.getY() + ")");

		ArrayList<Point> points3 = new ArrayList<Point>();
		points3.add(new Point(4, 3));
		points3.add(new Point(5, 5));
		points3.add(new Point(2, 7));

		System.out.println("\nGrid 8x8 and values ((4, 3), (5, 5), (2, 7))");
		pt = d.shortestDistanceTraveled(arr[2], points3);
		System.out.println("Shortest Distance Point = p(" + pt.getX() + ", " + pt.getY() + ")");
	}
}