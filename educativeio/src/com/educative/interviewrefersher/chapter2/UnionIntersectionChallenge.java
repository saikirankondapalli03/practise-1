package com.educative.interviewrefersher.chapter2;

class UnionIntersectionChallenge {

	   public static <T> SinglyLinkedList<T> union(SinglyLinkedList<T> list1, SinglyLinkedList<T> list2) {

	        //if one of the list is empty then return the other list
	        if (list1.isEmpty())
	            return list2;
	        if (list2.isEmpty())
	            return list1;

	        //take the head of the first list
	        SinglyLinkedList<T>.Node last = list1.getHeadNode();
	        //traverse it to the last element
	        while (last.nextNode != null) {
	            last = last.nextNode;
	        }
	        //attach the last element of list1 to head of list2
	        last.nextNode = list2.getHeadNode();
	        //remove duplicates that might have been added now
	        list1.removeDuplicatesWithHashing(); //complexity of this function is O(n)

	        return list1;
	    }

	    public static <T> boolean contains(SinglyLinkedList<T> list, T data) {
	        SinglyLinkedList<T>.Node current = list.getHeadNode();
	        while (current != null) {
	            if (current.data.equals(data))
	                return true;
	            current = current.nextNode;
	        }
	        return false;
	    }

	    public static <T> SinglyLinkedList<T> intersection(SinglyLinkedList<T> list1, SinglyLinkedList<T> list2) {
	        SinglyLinkedList<T> result = new SinglyLinkedList<T>();
	        if (list1.isEmpty())
	            return result;
	        if (list2.isEmpty())
	            return result;
	        SinglyLinkedList<T>.Node current = list1.getHeadNode();

	        while (current != null) {
	            if (contains(list2, current.data)) {
	                result.insertAtHead(current.data);
	            }
	            current = current.nextNode;
	        }

	        return result;
	    }

	    public static void main( String args[] ) {
	        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<Integer>();
	        for(int i=7; i>3; i--){
	            list1.insertAtHead(i);
	        }
	        System.out.print("1st ");
	        list1.printList();
	        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<Integer>();
	        for(int i=0; i<5; i++){
	            list2.insertAtHead(i);
	        }
	        System.out.print("2nd ");
	        list2.printList();
	        System.out.print("After Intersection ");
	        intersection(list1, list2).printList();
	        System.out.print("After Union ");
	        union(list1, list2).printList();
	    }
	}
