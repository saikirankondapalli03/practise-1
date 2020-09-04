package com.educative.tc.chapter3;

public class Permutation {
	 public static void main( String args[] ) {
	        char[] array = new char[] { 'a', 'b', 'c' ,'c' };
	        char[] perm = new char[array.length];
	        boolean[] used = new boolean[256];
	        permute(array, perm, 0, used);
	    }
	  
	    /**
	     * Note that we are using a boolean array to remember what
	     * character we have already used and another array perm 
	     * which contains the actual permutation. We can actually
	     * generate permutations without these additional arrays but
	     * for now the desire is to make the code easier to understand.
	     */  
	    static void permute(char[] array, char[] perm, int i, boolean[] used) {
	        
	        // base case
	        if (i == perm.length) {
	            System.out.println(perm);
	            return;
	        }

	        for (int j = 0; j < array.length; j++) {

	            if (!used[array[j] - 'a']) {
	                perm[i] = array[j];
	                used[array[j] - 'a'] = true;
	                permute(array, perm, i + 1, used);
	                used[array[j] - 'a'] = false;
	            }
	        }
	    }
}
