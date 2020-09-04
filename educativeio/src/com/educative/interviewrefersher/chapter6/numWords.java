package com.educative.interviewrefersher.chapter6;

import java.util.Arrays;

class numWords {
	public static int totalWords(TrieNode root) {
		int result = 0;

		// Leaf denotes end of a word
		if (root.isEndWord)
			result++;

		for (int i = 0; i < 26; i++) {
			if (root.children[i] != null) {
				int result1=totalWords(root.children[i]);
				result = result + result1;
			}
		}

		return result;
	}

	public static void main(String args[]) {
		// Input keys (use only 'a' through 'z' and lower case)
		String keys[] = { "a", "the", "there", "answer", "any", "by", "bye", "their", "abc" };

		Trie t = new Trie();

		System.out.println("Keys: " + Arrays.toString(keys));

		// Construct trie

		for (int i = 0; i < keys.length; i++)
			t.insert(keys[i]);

		System.out.println(totalWords(t.getRoot()));

	}
}