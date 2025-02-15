package com.educative.patterns.chapter1;

import java.util.*;

class WordConcatenation {
	public static List<Integer> findWordConcatenation(String str, String[] words) {
		Map<String, Integer> wordFrequencyMap = new HashMap<>();
		for (String word : words)
			wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);

		List<Integer> resultIndices = new ArrayList<Integer>();
		int wordsCount = words.length, wordLength = words[0].length();
		int count=str.length() - wordsCount * wordLength; 
		for (int i = 0; i <= count; i++) {
			Map<String, Integer> wordsSeen = new HashMap<>();
			for (int j = 0; j < wordsCount; j++) {
				int nextWordIndex = i + j * wordLength;
				// get the next word from the string
				String word = str.substring(nextWordIndex, nextWordIndex + wordLength);
				if (!wordFrequencyMap.containsKey(word)) // break if we don't need this word
					break;

				wordsSeen.put(word, wordsSeen.getOrDefault(word, 0) + 1); // add the word to the 'wordsSeen' map

				// no need to process further if the word has higher frequency than required
				if (wordsSeen.get(word) > wordFrequencyMap.getOrDefault(word, 0))
					break;

				if (j + 1 == wordsCount) // store index if we have found all the words
					resultIndices.add(i);
			}
		}

		return resultIndices;
	}

	public static void main(String[] args) {
		List<Integer> result = WordConcatenation.findWordConcatenation("catfoxcatafox", new String[] { "fox", "cata" });
		System.out.println(result);
		result = WordConcatenation.findWordConcatenation("catcatfoxfox", new String[] { "cat", "fox" });
		System.out.println(result);
	}
}
