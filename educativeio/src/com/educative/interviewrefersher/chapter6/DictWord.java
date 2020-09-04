package com.educative.interviewrefersher.chapter6;

import java.util.Arrays;

class DictWord {
	  
	  public static boolean isFormationPossible(String[] dict,String word) {
	    if(word.length() < 2 || dict.length < 2) {
	      return false;
	    }

	    //Create Trie and insert dictionary elements in it
	    Trie trie = new Trie();

	    for(int i = 0; i < dict.length; i++) {
	      trie.insert(dict[i]);
	    }

	    for(int i = 0; i < word.length(); i++) {
	      //Slice the word into two strings in each iteration
	      String first = word.substring(0, i);
	      String second = word.substring(i, word.length());

	      //If both substrings are present in the trie, the condition is fulfilled
	      if(trie.search(first) && trie.search(second)) {
	        return true;
	      }
	    }

	    return false;
	  }

	  public static void main(String args[]){
	    // Input dict (use only 'a' through 'z' and lower case)
	    String dict[] = {"the", "hello", "there", "answer","any", "dragon", "world", "their",  "inc"};

	    Trie t = new Trie();

	    System.out.println("Keys: "+ Arrays.toString(dict));

	    if(isFormationPossible(dict, "helloworld"))
	      System.out.println("true");
	    else
	      System.out.println("false");

	  }


	}
