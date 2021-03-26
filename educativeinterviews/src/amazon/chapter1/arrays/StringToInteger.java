package amazon.chapter1.arrays;

public class StringToInteger {
	
	 public static int myAtoi(String str) {
	        if(str.length() == 0)
	            return 0;
	        int i = 0;
	        int sign = 1;
	        int n = str.length();
	        while(i < n && str.charAt(i) == ' ')
	            i++; //skip blank spaces at the beginnig
	        if(i >= n) return 0;
	        if(str.charAt(i) == '+' || str.charAt(i) == '-'){
	        	//checking for =ve or -ve no.
	            sign = str.charAt(i) == '+' ? 1 : -1;
	            i++;
	        }
	        long res = 0;
	        while(i < n && Character.isDigit(str.charAt(i))){
	            res = res * 10 + str.charAt(i) - '0'; // -'0' to convert a char to digit
	            if(res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) //if exceeds min ot max val
	                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE; //dpending upon sign return val
	            i++;
	        }
	        return (int) (res*sign);
	    }
	 
	 public static void main(String[]args) {
		 System.out.println(StringToInteger.myAtoi("4193 with words"));
		 System.out.println('0'-'9');
	 }
}
