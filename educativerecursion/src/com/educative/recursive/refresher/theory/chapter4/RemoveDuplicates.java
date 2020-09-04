package com.educative.recursive.refresher.theory.chapter4;

class RemoveDuplicates {

    private static String remDuplicates(String text) {
        if (text.length() == 1) {
            return text;
        }
        String result="";
        if (text.substring(0,1).equals(text.substring(1,2))) {
        	result=remDuplicates(text.substring(1));
        }
        else {
        	String str1=text.substring(0,1);
        	String str2=remDuplicates(text.substring(1));
        	result= str1+str2;
        }
        return result;
    }

    public static void main( String args[] ) {
        String input1 = "hhhhelloo";
        //String input2 = "Thiss iiss aa teesstt";
        
        System.out.println( "Original string: " + input1);

        String output = remDuplicates(input1);

        System.out.println("String after: " + output);
    }   
}