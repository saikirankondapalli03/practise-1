package com.patterns.maxminheapcombo;

import java.util.PriorityQueue;

public class MaximizeCapitalSimple {
    
    public static int maximizeCapital(int[] capital, int[] profits, int maxProjects, int initialMoney) {
        int currentMoney = initialMoney;
        
        // Projects I can't afford yet (sorted by capital needed - cheapest first)
        PriorityQueue<Integer> cantAfford = new PriorityQueue<>((a, b) -> capital[a] - capital[b]);
        
        // Projects I can afford now (sorted by profit - most profitable first)  
        PriorityQueue<Integer> canAfford = new PriorityQueue<>((a, b) -> profits[b] - profits[a]);
        
        // Initially, I can't afford anything - put all projects in cantAfford
        for (int i = 0; i < capital.length; i++) {
            cantAfford.add(i);
        }
        
        // Do up to maxProjects
        for (int projectsDone = 0; projectsDone < maxProjects; projectsDone++) {
            
            // Step 1: Move projects I can now afford
            while (!cantAfford.isEmpty() && capital[cantAfford.peek()] <= currentMoney) {
                canAfford.add(cantAfford.poll());
            }
            
            // Step 2: If no projects available, stop
            if (canAfford.isEmpty()) {
                break;
            }
            
            // Step 3: Pick most profitable project
            int bestProject = canAfford.poll();
            currentMoney += profits[bestProject];
        }
        
        return currentMoney;
    }
    
    public static void main(String[] args) {
        // Test with simple example
        int[] capital = {2, 4, 6};
        int[] profits = {3, 8, 10};
        int maxProjects = 2;
        int initialMoney = 5;
        
        int result = maximizeCapital(capital, profits, maxProjects, initialMoney);
        System.out.println("Final money: " + result);
    }
}