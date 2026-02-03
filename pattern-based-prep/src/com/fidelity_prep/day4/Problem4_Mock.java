package fidelity_prep.day4;

import java.util.*;

/**
 * MOCK INTERVIEW PROBLEM 4 (Backend-focused)
 * Time: 45 minutes
 * 
 * Problem: Design Twitter (simplified)
 * LeetCode: 355. Design Twitter
 * Difficulty: Medium
 * 
 * Implement:
 * - postTweet(userId, tweetId)
 * - getNewsFeed(userId) - returns 10 most recent tweets from user and followees
 * - follow(followerId, followeeId)
 * - unfollow(followerId, followeeId)
 * 
 * ============================================
 * YOUR APPROACH (10 min - write here)
 * ============================================
 * 
 * Pattern Recognition:
 * 
 * 
 * Data Structures:
 * 
 * 
 * Logical Steps:
 * 1.
 * 2.
 * 3.
 * 
 * ============================================
 * CODE (25 min)
 * ============================================
 */

class Twitter {
    // YOUR CODE HERE
    
    public Twitter() {
        
    }
    
    public void postTweet(int userId, int tweetId) {
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        return new ArrayList<>();
    }
    
    public void follow(int followerId, int followeeId) {
        
    }
    
    public void unfollow(int followerId, int followeeId) {
        
    }
}

public class Problem4_Mock {
    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5);
        System.out.println(twitter.getNewsFeed(1)); // [5]
        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        System.out.println(twitter.getNewsFeed(1)); // [6, 5]
        twitter.unfollow(1, 2);
        System.out.println(twitter.getNewsFeed(1)); // [5]
    }
    
    // ============================================
    // SOLUTION HINT
    // ============================================
    /*
    - Use HashMap<Integer, List<Tweet>> for user tweets
    - Use HashMap<Integer, Set<Integer>> for follow relationships
    - Use PriorityQueue (max-heap) for merging feeds
    - Sort by timestamp (use counter or timestamp)
    
    Time: O(n log n) for getNewsFeed where n is total tweets
    Space: O(n) for all tweets and relationships
    */
}
