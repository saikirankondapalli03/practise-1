package com.educative.coderust.chapter8;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Pair<X, Y> {
	public X first;
	public Y second;

	public Pair(X first, Y second) {
		this.first = first;
		this.second = second;
	}
}

class IntPair extends Pair<Integer, Integer> {
	public IntPair(int first, int second) {
		super(first, second);
	}
}

class Boggle {
	// code assumes that both dimensions of grid are same
	char[][] grid;
	Set<String> dictionary;
	boolean[][] state;

	//find all neighbours of x,y
	ArrayList<IntPair> find_all_nbrs(int x, int y) {
		
	
		ArrayList<IntPair> nbrs = new ArrayList<IntPair>();

		int startX = Math.max(0, x - 1);
		int startY = Math.max(0, y - 1);
		int endX = Math.min(grid.length - 1, x + 1);
		int endY = Math.min(grid.length - 1, y + 1);

		for (int i = startX; i <= endX; ++i) {
			for (int j = startY; j <= endY; ++j) {
				//System.out.println("i=>"+i+"j=>"+j);
				if (state[i][j]) {
					continue;
				}
				nbrs.add(new IntPair(i, j));
			}
		}
		return nbrs;
	}
	
	/*
	 * 
	 * 


	The Choice: Defining Our Decision Space                            
	The Constraints: Directing Our Recursion                           
	Our Goal: When Recursion Bottoms Out
	 */

	void findWordsRec(int i, int j, StringBuilder current, HashSet<String> result_words) {

		if (current.length() > 0 && dictionary.contains(current.toString())) {
			result_words.add(current.toString());
		}

		// we can really speed up our algorithm if
		// we have prefix method available
		// for our dictionary by using code like below
		/*
		 * if (!dictionary.is_prefix(current)) { // if current word is not prefix of any
		 * word in dictionary // we don't need to continue with search return; }
		 */
		// you are in 0,1
		// possible directions(coordinates to go) are 
		// (0,2) (1,0) (1,1) (1,2)
		ArrayList<IntPair> nbrs = find_all_nbrs(i, j);
		for (IntPair pr : nbrs) {
			current.append(grid[pr.first][pr.second]);
			state[pr.first][pr.second] = true;
			//System.out.println("i =>"+i+"j=>"+j+" value is =>"+grid[pr.first][pr.second]);
			findWordsRec(pr.first, pr.second, current, result_words);
			//System.out.println("yovv");
			//System.out.println("i =>"+i+"j=>"+j+" value is =>"+grid[pr.first][pr.second]);
			//System.out.println(current.length());
			//System.out.println(current.charAt(current.length() - 1));
			state[pr.first][pr.second] = false;
			current.setLength(current.length() - 1);
				
		}
	}

	Boggle(char[][] grid, HashSet<String> dictionary) {
		this.grid = grid;
		this.dictionary = dictionary;
		this.state = new boolean[grid.length][grid.length];

		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid.length; ++j) {
				state[i][j] = false;
			}
		}
	}

	public HashSet<String> findAllWords() {
		HashSet<String> result_words = new HashSet<String>();
		StringBuilder currentWord = new StringBuilder();
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid.length; ++j) {
				findWordsRec(i, j, currentWord, result_words);
			}
		}

		return result_words;
	}

	public static void main(String[] args) {
		char[][] grid = new char[][] { { 'c', 'a', 't' }, { 'r', 'r', 'e' }, { 't', 'o', 'n' } };

		String[] dict = { "cat", "cater", "cartoon", "art", "toon", "moon", "eat", "ton" };
		HashSet<String> dictionary = new HashSet<String>();
		for (String s : dict) {
			dictionary.add(s);
		}

		Boggle b = new Boggle(grid, dictionary);
		List<IntPair> list= b.find_all_nbrs(1,1);
		
		HashSet<String> words = b.findAllWords();
		for (String s : words) {
			System.out.println(s);
		}
	}
};