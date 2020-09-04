package com.educative.recursive.refresher.theory.chapter4;

public class CountVowels {

	static boolean isPalRec(String str, int s, int e) {
		// If there is only one character
		if (s == e)
			return true;

		// If first and last
		// characters do not match
		if ((str.charAt(s)) != (str.charAt(e)))
			return false;

		// If there are more than
		// two characters, check if
		// middle substring is also
		// palindrome or not.
		if (s < e + 1)
			return isPalRec(str, s + 1, e - 1);

		return true;
	}
	
	  // Function to check the Vowel 
    static int isVowel(char ch) 
    { 
        ch = Character.toUpperCase(ch); 
       if(ch=='A' || ch=='E' || ch=='I' || 
            ch=='O' || ch=='U')  
           return 1; 
       else return 0; 
    } 
       
    // to count total number of vowel from 0 to n 
    static int countVowels(String str, int n) 
    { 
        if (n == 1) 
            return isVowel(str.charAt(n - 1)); 
       
        return countVowels(str, n-1) + isVowel(str.charAt(n - 1)); 
    } 

	public static int totalVowels(String text, int len, int index) {
		int count = 0;

		if (len == 0) {
			return 0;
		}
		char single = Character.toUpperCase(text.charAt(index));
		if (single == 'A' || single == 'E' || single == 'I' || single == 'O' || single == 'U') {
			count++;
		}
		int returnCount=totalVowels(text, len - 1, index + 1);
		int totalCount= returnCount + count;
		return totalCount; 
	}

	public static int callTotalVowels(String text) {
		return totalVowels(text, text.length(), 0);
	}

	public static void main(String args[]) {
		String string1 = "Hello world";
		String string2 = "STR";
		String string3 = "AEIOUaeiouSs";

		int result1 = callTotalVowels(string1);
		int result2 = callTotalVowels(string2);
		int result3 = callTotalVowels(string3);

		System.out.println("Total number of vowels in " + string1 + " are = " + result1);
		System.out.println("Total number of vowels in " + string2 + " are = " + result2);
		System.out.println("Total number of vowels in " + string3 + " are = " + result3);
	}

}
