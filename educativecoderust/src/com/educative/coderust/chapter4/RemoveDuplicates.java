package com.educative.coderust.chapter4;

import java.util.LinkedHashSet;
import java.util.Set;

class RemoveDuplicates {
	// this solution uses extra memory
	// to keep all characters present in string.

	static void removeDuplicates(char[] str) {
		Set<Character> hashset = new LinkedHashSet<Character>();

		int writeIndex = 0;
		int readIndex = 0;

		while (readIndex != str.length) {

			if (!hashset.contains(str[readIndex])) {

				hashset.add(str[readIndex]);
				str[writeIndex] = str[readIndex];
				++writeIndex;
			}

			++readIndex;
		}

		str[writeIndex] = '\0';
	}

	// Test Program

	static char[] getArray(String t) {
		char[] s = new char[t.length() + 1];
		int i = 0;
		for (; i < t.length(); ++i) {
			s[i] = t.charAt(i);
		}
		s[i] = '\0';
		return s;
	}

	static void print(char[] s) {
		int i = 0;
		while (s[i] != '\0') {
			System.out.print(s[i]);
			++i;
		}
		System.out.println();
	}

	public static void main(String[] args) {

		char[] input = getArray("dabbac");
		System.out.print("Before: ");
		System.out.println(input);
		RemoveDuplicates.removeDuplicatesWithOn2(input);
		System.out.print("After: ");
 		print(input);
	}
	
	static void removeDuplicatesWithOn2(char[] str) {
	    if (str == null || str.length == 0) {
	      return;
	    }

	    int writeIndex = 0;
	    for (int i = 0; i < str.length; i++) {
 	      boolean found = false;

	      for (int j = 0; j < writeIndex; j++) {
	        if (str[i] == str[j]) {
	          found = true;
	          break;
	        }
	      }

	      if (!found) {
	        str[writeIndex] = str[i];
	        writeIndex++;
	      }
	    }

	    if (writeIndex != str.length) {
	      str[writeIndex] = '\0';
	    }
	  }

	
}