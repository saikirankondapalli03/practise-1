package com.educative.interviewrefersher.chapter2;

class CheckDuplicates {
    public static <T> void removeDuplicates(SinglyLinkedList<T> list) 
    {
        SinglyLinkedList<T>.Node current = list.headNode; // will be used for outer loop
        SinglyLinkedList<T>.Node compare = null;     // will be used for inner loop
        int i=0;
        while (current != null ) {
            compare = current;
            while (compare.nextNode != null) {
                if (current.data.equals(compare.nextNode.data)) { //check if duplicate
                    compare.nextNode = compare.nextNode.nextNode;
                } else {	
                    compare = compare.nextNode;
                    }
                i++;                
            }
            current = current.nextNode;
        }
        System.out.println(i);
    }
    
    public static void main( String args[] ) {
   
        SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
        for(int i = 0; i < 9; i++)
            list.insertAtEnd(1);
        list.insertAtEnd(0);
        list.insertAtEnd(1);
        System.out.print("Before ");
        list.printList();
        removeDuplicates(list);
        System.out.print("After ");
        list.printList();
    
    }
}