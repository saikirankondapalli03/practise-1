package com.educative.coderust.chapter8.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 * 
 * @author Koffman and Wolfgang
 **/
public class Maze implements GridColors {

    /** The maze */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * Attempts to find a path through point (x, y).
     * 
     * @pre Possible path cells are in BACKGROUND color; barrier cells are in
     *      ABNORMAL color.
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the
     *       TEMPORARY color.
     * @param x
     *            The x-coordinate of current point
     * @param y
     *            The y-coordinate of current point
     * @return If a path through (x, y) is found, true; otherwise, false
     */
    public boolean findMazePath(int x, int y) {
         // COMPLETE HERE FOR PROBLEM 1
    	/**
         * 
         * Writing base conditions 
         * a) check if the x and y are lying within boundary of the matrix. if it is not return false
         * b) check if block is white color which means it is a blocker. Don't go . return false
         * c) check if you are trying to revisit the node that you've already visited ? , dont visit. return false
         * d) check if you are already at the destination. then it means you reached. return true happily. 
         * 
         */
    	//a) check if the x and y are lying within boundary of the matrix. if it is not return false
    	if (x < 0 || y < 0) {
			return false;
		}
		if(x > maze.getNCols() - 1 || y > maze.getNRows() - 1) {
    		return false;
    	}
		//b)if this is white color which means it is a blocker. Don't go . return false
    	else if (maze.getColor(x, y) == BACKGROUND) 
        {
            return false;
        }
        //c) if you are trying to revisit the node that you've already visited ? , dont visit. return false 
    	else if(maze.getColor(x, y) == TEMPORARY) 
        {
            return false;
        }
        //d) if you are already at the destination. then it means you reached. return true happily. 
    	else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) 
        {
            maze.recolor(x, y, PATH);
            return true;
        }
        
        //FINDING MAZE PATH
        // if you encounter red, then it means you can go through it. 
        // when you visit red , it mark it as "visited" with color Temporary, so that you don't end up visiting again
    	else if(maze.getColor(x, y) == NON_BACKGROUND)
        {
			maze.recolor(x, y, TEMPORARY);
			//try going to the Top . which means decrement x by 1
			boolean goTop= findMazePath(x-1,y);
			//try going to the down . which means increment x by 1
			boolean goDown= findMazePath(x+1,y);
			//try going to the  left. which means decrement y by 1
			boolean goLeft=  findMazePath(x,y-1);
			//try going to the right . which means increment y by 1 
			boolean goRight = findMazePath(x,y+1);
			//if you have path in all the directions then , mark that as path (green) and return true 
			if (goRight || goDown || goLeft || goTop) 
			{
				maze.recolor(x, y, PATH);
				return true;
			}
		}
        
    	//if the execution has reached 
    	return false;
        	
    }

   public void findMazePathStackBased(int x, int y, ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace) {
	   /**
        * 
        * Writing base conditions 
        * a) check if the x and y are lying within boundary of the matrix. if it is not return false
        * b) check if block is white color which means it is a blocker. Don't go . return false
        * c) check if you are trying to revisit the node that you've already visited ? , dont visit. return false
        * d) check if you are already at the destination. then it means you reached. return true happily. 
        * 
        */
	    //a) check if the x and y are lying within boundary of the matrix. if it is not return false
	    if (x < 0 || y < 0) {
			return;
		}
		//a) check if the x and y are lying within boundary of the matrix. if it is not return false
    	if(x > maze.getNCols() - 1 || y > maze.getNRows() - 1) {
    		return;
    	}
		//b) check if block is white color which means it is a blocker. Don't go . return false
    	else if ( maze.getColor(x, y) == BACKGROUND || maze.getColor(x, y) == TEMPORARY) {
    		return;
    	} 
		//d) check if you are already at the destination. then it means you reached. return true happily. 
    	else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1) {
        	trace.push(new PairInt(x,y));
			ArrayList<PairInt> visited = new ArrayList<PairInt>();
			visited.addAll(trace);
			result.add(visited);
			trace.pop();
			maze.recolor(x,y, NON_BACKGROUND);
			return;
        }
        maze.recolor(x, y, TEMPORARY);
        trace.push(new PairInt(x, y));
        //try going to the down . which means increment x by 1
		findMazePathStackBased(x+1,y, result, trace);
		//try going to the up . which means increment x by 1
		findMazePathStackBased(x-1,y, result, trace);
		//try going to the right . which means increment y by 1 
		findMazePathStackBased(x,y+1, result, trace);
		//try going to the  left. which means decrement y by 1
		findMazePathStackBased(x,y-1, result, trace);
		maze.recolor(x, y, NON_BACKGROUND);
		trace.pop();
		return;
    }

    public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y) 
    {
        ArrayList<ArrayList<PairInt>> result = new ArrayList<>();
        Stack<PairInt> trace = new Stack<>();
        findMazePathStackBased(0, 0, result, trace);
        return result;
    }

    // ADD METHOD FOR PROBLEM 3 HERE
    public ArrayList<PairInt> findMazePathMin(int x, int y) {
		ArrayList<ArrayList<PairInt>> allPaths = findAllMazePaths(0, 0);
		if (allPaths.size() == 0) {
			return new ArrayList<PairInt>();
		} else {
			Collections.sort(allPaths,new Comparator<ArrayList<PairInt>>() {
				@Override
				public int compare(ArrayList<PairInt> o1, ArrayList<PairInt> o2) {
					// TODO Auto-generated method stub
					if(o1.size() < o2.size()) {
						return -1;
					}
					else if (o1.size() > o2.size()) {
					    return 1; 
					}
					else {
						return 0;
					}
				}
			});
			return allPaths.get(0);
		}
    }
    	
    /* <exercise chapter="5" section="6" type="programming" number="2"> */
    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }
    /* </exercise> */

    /* <exercise chapter="5" section="6" type="programming" number="3"> */
    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
    /* </exercise> */
}
/* </listing> */