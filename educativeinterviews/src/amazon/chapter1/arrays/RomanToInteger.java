package amazon.chapter1.arrays;

import java.util.HashMap;
import java.util.Map;

class RomanToInteger {

	static Map<String, Integer> values = new HashMap<>();

	static {
		values.put("M", 1000);
		values.put("D", 500);
		values.put("C", 100);
		values.put("L", 50);
		values.put("X", 10);
		values.put("V", 5);
		values.put("I", 1);
	}

	
	public static int romanToInteger(String s) {
        int sum = 0;
        for(int i = s.length()-1;i>=0;i--){
            char a= s.charAt(i);
            int ans = check(a);
            if(i>0 &&((a=='V' && s.charAt(i-1)=='I')||(a=='X' && s.charAt(i-1)=='I'))){
                sum = sum+ans-1;
                i--;
            }
            else if(i>0 &&((a=='L' && s.charAt(i-1)=='X')||(a=='C' && s.charAt(i-1)=='X'))){
                sum = sum+ans-10;
                i--;
            }
            else if(i>0 &&((a=='D' && s.charAt(i-1)=='C')||(a=='M' && s.charAt(i-1)=='C'))){
                sum = sum+ans-100;
                i--;
            }
            else {
                sum = sum + ans;
            }
        }
        return sum;
    }
    public static int check(char a) {
        switch (a){
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
        }
        return 0;
    }
    
    
	public static int romanToInt(String s) {

		int sum = 0;
		int i = 0;
		while (i < s.length()) {
			String currentSymbol = s.substring(i, i + 1);
			int currentValue = values.get(currentSymbol);
			int nextValue = 0;
			if (i + 1 < s.length()) {
				String nextSymbol = s.substring(i + 1, i + 2);
				nextValue = values.get(nextSymbol);
			}

			if (currentValue < nextValue) {
				sum += (nextValue - currentValue);
				i += 2;
			} else {
				sum += currentValue;
				i += 1;
			}

		}
		return sum;
	}
	
	public static void main(String[]args) {
		System.out.println(RomanToInteger.romanToInteger("XIV"));
	}
}