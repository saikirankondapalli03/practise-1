package com.educative.patterns.chapter10;

import java.util.*;

class AbbreviatedWord {
	StringBuilder str;
	int start;
	int count;

	public AbbreviatedWord(StringBuilder str, int start, int count) {
		this.str = str;
		this.start = start;
		this.count = count;
	}
}

class GeneralizedAbbreviation {

	public static List<String> generateGeneralizedAbbreviation(String word) {
		int wordLen = word.length();
		List<String> result = new ArrayList<String>();
		Queue<AbbreviatedWord> queue = new LinkedList<>();
		queue.add(new AbbreviatedWord(new StringBuilder(), 0, 0));
		while (!queue.isEmpty()) {
			AbbreviatedWord abWord = queue.poll();
			if (abWord.start == wordLen) {
				if (abWord.count != 0)
					abWord.str.append(abWord.count);
				result.add(abWord.str.toString());
			} else {
				// continue abbreviating by incrementing the current abbreviation count
				queue.add(new AbbreviatedWord(new StringBuilder(abWord.str), abWord.start + 1, abWord.count + 1));

				// restart abbreviating, append the count and the current character to the
				// string
				if (abWord.count != 0)
					abWord.str.append(abWord.count);
				StringBuilder str = new StringBuilder(abWord.str).append(word.charAt(abWord.start));
				queue.add(new AbbreviatedWord(str, abWord.start + 1, 0));
			}
		}

		return result;
	}

	public static List<String> generateGeneralizedAbbreviationRec(String word) {
		List<String> result = new ArrayList<String>();
		generateAbbreviationRecursive(word, new StringBuilder(), 0, 0, result);
		return result;
	}

	private static void generateAbbreviationRecursive(String word, StringBuilder abWord, int start, int count,
			List<String> result) {

		if (start == word.length()) {
			if (count != 0)
				abWord.append(count);
			result.add(abWord.toString());
		} else {
			// continue abbreviating by incrementing the current abbreviation count
			generateAbbreviationRecursive(word, new StringBuilder(abWord), start + 1, count + 1, result);

			// restart abbreviating, append the count and the current character to the
			// string
			if (count != 0)
				abWord.append(count);
			generateAbbreviationRecursive(word, new StringBuilder(abWord).append(word.charAt(start)), start + 1, 0,
					result);
		}
	}

	public static void main(String[] args) {
		List<String> result = GeneralizedAbbreviation.generateGeneralizedAbbreviation("BAT");
		System.out.println("Generalized abbreviation are: " + result);

		result = GeneralizedAbbreviation.generateGeneralizedAbbreviation("code");
		System.out.println("Generalized abbreviation are: " + result);
	}
}
