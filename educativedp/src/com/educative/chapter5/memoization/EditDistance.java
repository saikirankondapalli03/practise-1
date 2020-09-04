package com.educative.chapter5.memoization;
class EditDistance {

	  public int findMinOperations(String s1, String s2) {
	    Integer[][] dp = new Integer[s1.length()+1][s2.length()+1];
	    return findMinOperationsRecursive(dp, s1, s2, 0, 0);
	  }

	  private int findMinOperationsRecursive(Integer[][] dp, String s1, String s2, int i1, int i2) {
	    
	    if(dp[i1][i2] == null) {
	      // if we have reached the end of s1, then we have to insert all the remaining characters of s2
	      if(i1 == s1.length())
	        dp[i1][i2] = s2.length() - i2;

	      // if we have reached the end of s2, then we have to delete all the remaining characters of s1
	      else if(i2 == s2.length())
	        dp[i1][i2] = s1.length() - i1;

	      // If the strings have a matching character, we can recursively match for the remaining lengths
	      else if(s1.charAt(i1) == s2.charAt(i2))
	        dp[i1][i2] = findMinOperationsRecursive(dp, s1, s2, i1+1, i2+1);
	      else {
	        int c1 = findMinOperationsRecursive(dp, s1, s2, i1+1, i2); //delete
	        int c2 = findMinOperationsRecursive(dp, s1, s2, i1, i2+1); //insert
	        int c3 = findMinOperationsRecursive(dp, s1, s2, i1+1, i2+1); //replace
	        dp[i1][i2] = 1 + Math.min(c1, Math.min(c2, c3));
	      }
	    }
	    
	    return dp[i1][i2];
	  }

	  public static void main(String[] args) {
	    EditDistance editDisatnce = new EditDistance();
	    System.out.println(editDisatnce.findMinOperations("bat", "but"));
	    System.out.println(editDisatnce.findMinOperations("abdca", "cbda"));
	    System.out.println(editDisatnce.findMinOperations("passpot", "ppsspqrt"));
	  }
	}