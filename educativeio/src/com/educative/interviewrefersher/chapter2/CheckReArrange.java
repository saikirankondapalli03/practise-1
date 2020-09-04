package com.educative.interviewrefersher.chapter2;

class CheckReArrange
{
  //Re-Arrange Positive and Negative Values of Given Array 
  public static void reArrange(int[] arr) 
  {
    int j = 0; 
    for (int i = 0; i < arr.length; i++) { 
      if (arr[i] < 0) {   // if negative number found
        if (i != j) { 
          int temp = arr[i];
          arr[i] = arr[j]; // swapping with leftmost positive
          arr[j] = temp;
        }
        j++; 
      } 
    } 
  } //end of reArrange()

  public static void main(String args[]) {

    int[] arr = {2, 4, -6, 8, -5, -10};

    System.out.print("Array before re-arranging: ");
    for(int i = 0; i < arr.length; i++)
      System.out.print(arr[i] + " ");
    System.out.println();

    reArrange(arr);

    System.out.print("Array after rearranging: ");
    for(int i = 0; i < arr.length; i++)
      System.out.print(arr[i] + " ");
  }
}