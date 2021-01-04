package com.educative.coderust.chapter1.arrays;

import java.util.Scanner;

public class wordReverse {
	
	public static String wordsReverse(String str1) {
		String reversedString = "";
		 String words[] = str1.split(" ");
		 for (int i = 0; i < words.length; i++) 
	        {
	            String word = words[i];
	            String reverseWord = "";
	            for (int j = word.length() - 1; j >= 0; j--) {
	                reverseWord = reverseWord + word.charAt(j);
	            }
	            reversedString = reversedString + reverseWord + " ";
	        }
		return reversedString;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter String :");
		String str1 = sc.nextLine();
		String reversedString = wordsReverse(str1); 
		System.out.println(reversedString);

	}

}
