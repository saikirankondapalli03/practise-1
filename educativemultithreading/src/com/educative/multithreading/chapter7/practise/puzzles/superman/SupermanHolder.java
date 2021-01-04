package com.educative.multithreading.chapter7.practise.puzzles.superman;


/*
 * 
 * 
 * Another implementation of the singleton pattern is the holder or Bill Pugh's singleton. The idea is to create a private nested static class that holds the static instance. The nested class Helper isn't loaded when the outer class Superman is loaded. The inner static class Helper is loaded only when the method getInstance() is invoked. This saves us from eagerly initializing the singleton instance.
 */
class SupermanHolder {

    private SupermanHolder() {
    }

    private static class Holder {
        private static final SupermanHolder superman = new SupermanHolder();
    }

    public static SupermanHolder getInstance() {
        return Holder.superman;
    }

    public void fly() {
        System.out.println("I am flyyyyinggggg ...");
    }    
}