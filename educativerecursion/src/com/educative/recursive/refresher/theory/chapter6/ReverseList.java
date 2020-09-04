public class Solution {

  public class Node {
     
	 int data;
	 Node next;
  }
  
  
  public static Node outputHead = null, outputTail = null;
  
  
  public static Node reverse(Node head) {
		reverseList(head);
		return outputHead;
  }
  
  
  public static Node reverseList(Node head) {
	  
		if( head.next == null ) {
			outputHead = head;
			outputTail = head;
			return;
		}
		
		reverseList(head.next);
		
		head.next = null;
		
		outputTail.next = head;
		
		outputTail = outputTail.next;
  }
  
}