package com.educative.interviewrefersher.chapter6;

class TrieNode
{
    TrieNode[] children;
    boolean isEndWord; //will be true if the node represents the end of word

    TrieNode(){
        this.isEndWord = false;
        this.children = new TrieNode[26]; //Total # of English Alphabets = 26
    }

    //Function to mark the currentNode as Leaf
    public void markAsLeaf(){
        this.isEndWord = true;
    }

    //Function to unMark the currentNode as Leaf
    public void unMarkAsLeaf(){
        this.isEndWord = false;
    }
}
