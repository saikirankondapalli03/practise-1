package com.educative.interviewrefersher.chapter3;

class CheckSort {
    public static void sortStack(Stack<Integer> stack) {
        //1. Use a second tempStack.
        //2. Pop value from mainStack.
        //3. If the value is greater or equal to the top of tempStack, then push the value in tempStack
        //else pop all values from tempStack and push them in mainStack and in the
        // end push value in tempStack and repeat from step 2.
        //till mainStack is not empty.
        //4. When mainStack will be empty, tempStack will have sorted values in descending order.
        //5. Now transfer values from tempStack to mainStack to make values sorted in ascending order.
        Stack<Integer> tempStack = new Stack<>(stack.getMaxSize());
        while (!stack.isEmpty()) {
            Integer value = stack.pop();
            if (!tempStack.isEmpty() && value >= tempStack.top()) {
                tempStack.push(value);
            } else {
                while (!tempStack.isEmpty() && tempStack.top() > value) {
                    stack.push(tempStack.pop());
                }
                tempStack.push(value);
            }
        }
        while (!tempStack.isEmpty())
            stack.push(tempStack.pop());
    }
    public static void main(String args[]) {

        Stack<Integer> stack = new Stack<Integer>(7);
        stack.push(2);
        stack.push(97);
        stack.push(4);
        stack.push(42);
        stack.push(12);
        stack.push(60);
        stack.push(23);
        sortStack(stack);
        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
}