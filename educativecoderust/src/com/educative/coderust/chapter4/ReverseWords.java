package com.educative.coderust.chapter4;

import java.util.ArrayDeque;
import java.util.Deque;

class ReverseWords {
	// Null terminating strings are not used in java
	public static void strRev(char[] str, int start, int end) {
		if (str == null || str.length < 2) {
			return;
		}

		while (start < end) {
			char temp = str[start];
			str[start] = str[end];
			str[end] = temp;
			start++;
			end--;
		}
	}

	public static void reverseWords(char[] sentence) {
		// Here sentence is a null-terminated string ending with char '\0'.
		if (sentence == null || sentence.length == 0) {
			return;
		}

		// To reverse all words in the string, we will first reverse
		// the string. Now all the words are in the desired location, but
		// in reverse order: "Hello World" -> "dlroW olleH".
		int len = sentence.length;
		strRev(sentence, 0, len - 2);

		// Now, let's iterate the sentence and reverse each word in place.
		// "dlroW olleH" -> "World Hello"
		int start = 0;
		int end;

		while (true) {
			// find the start index of a word while skipping spaces.
			while (sentence[start] == ' ') {
				++start;
			}

			if (start >= sentence.length - 1) {
				break;
			}

			// find the end index of the word.
			end = start + 1;
			while (end < sentence.length - 1 && sentence[end] != ' ') {
				++end;
			}

			// let's reverse the word in-place.
			strRev(sentence, start, end - 1);
			start = end;
		}
	}

	static char[] getArray(String t) {
		char[] s = new char[t.length() + 1];
		for (int i = 0; i < t.length(); ++i) {
			s[i] = t.charAt(i);
		}
		return s;
	}

	
	 public static String reverseWords(String s) {
		    int left = 0, right = s.length() - 1;
		    // remove leading spaces
		    while (left <= right && s.charAt(left) == ' ') ++left;

		    // remove trailing spaces
		    while (left <= right && s.charAt(right) == ' ') --right;

		    Deque<String> d = new ArrayDeque();
		    StringBuilder word = new StringBuilder();
		    // push word by word in front of deque
		    while (left <= right) {
		      char c = s.charAt(left);

		      if ((word.length() != 0) && (c == ' ')) {
		        d.offerFirst(word.toString());
		        word.setLength(0);
		      } else if (c != ' ') {
		        word.append(c);
		      }
		      ++left;
		    }
		    d.offerFirst(word.toString());

		    return String.join(" ", d);
		  }
	public static void main(String[] args) {
		String input = "Hello world sao asa";
		reverseWords(input);
		char[] s = getArray(input);
		System.out.println(s);
		reverseWords(s);
		System.out.println(s);
	}
}