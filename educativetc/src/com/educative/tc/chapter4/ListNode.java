package com.educative.tc.chapter4;

public class ListNode<T> {
    T data;
    ListNode<T> next;
    ListNode<T> prev;

    ListNode(T data) {
        this(null, data, null);
    }

    ListNode(ListNode<T> prev, T data, ListNode<T> next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}