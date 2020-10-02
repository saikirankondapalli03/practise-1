package com.educative.tc.chapter4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

public class PriorityExpiryCache {

	int maxSize;
	int currSize;

	PriorityQueue<ListNode<Item>> pqByExpiryTime = new PriorityQueue<>(
			(a, b) -> a.data.expireAfter - b.data.expireAfter);
	PriorityQueue<ListNode<Item>> pqByPreference = new PriorityQueue<>((a, b) -> a.data.preference - b.data.preference);
	HashMap<Integer, DoublyLinkedList<Item>> preferrenceToListMap = new HashMap<>();
	HashMap<String, ListNode<Item>> keyToItemNodeMap = new HashMap<>();

	public PriorityExpiryCache(int maxSize) {
		this.maxSize = maxSize;
		this.currSize = 0;
		LinkedList<Item> l = new LinkedList<>();
	}

	public Set<String> getKeys() {
		return keyToItemNodeMap.keySet();
	}

	/**
	 * 1. Remove all expired items first
	 * 2. If none are expired, evict the ones with
	 * lowest preference 
	 * 3. If there's a tie on items with least preference, evict
	 * the ones which are least recently used.
	 */
	public void evictItem(int currentTime) {

		if (currSize == 0)
			return;

		currSize--;

		// Check expired items first
		if (pqByExpiryTime.peek().data.expireAfter < currentTime) {

			ListNode<Item> node = pqByExpiryTime.poll();
			Item item = node.data;

			DoublyLinkedList<Item> dList = preferrenceToListMap.get(item.preference);
			dList.removeNode(node);

			// Remove from hashmap too
			if (dList.size() == 0) {
				preferrenceToListMap.remove(item.preference);
			}

			// Remove from hashmap
			keyToItemNodeMap.remove(item.key);

			// Remove from preference queue too
			pqByPreference.remove(item.preference);

			return;
		}

		// Next check if preference items are to be removed
		int preference = pqByPreference.poll().data.preference;

		DoublyLinkedList<Item> dList = preferrenceToListMap.get(preference);

		// Remove the end
		ListNode<Item> leastRecentlyUsedWithLeastPreference = dList.removeLast();
		keyToItemNodeMap.remove(leastRecentlyUsedWithLeastPreference.data.key);

		// Remove from the expiry queue
		pqByExpiryTime.remove(leastRecentlyUsedWithLeastPreference);

		if (dList.size() == 0) {
			// Remove the dList too
			preferrenceToListMap.remove(dList);
		}
	}

	/**
	 * Get the value of the key if the key exists in the cache and isn't expired.
	 */
	public Item getItem(String key) {

		if (keyToItemNodeMap.containsKey(key)) {
			ListNode<Item> node = keyToItemNodeMap.get(key);
			Item itemToReturn = node.data;

			DoublyLinkedList<Item> dList = preferrenceToListMap.get(itemToReturn.preference);

			dList.removeNode(node);
			dList.addFront(itemToReturn);

			return itemToReturn;
		}

		return null;
	}

	/**
	 * update or insert the value of the key with a preference value and expire
	 * time. Set should never allow more items than maxItems to be in the cache.
	 * When evicting we need to evict the lowest preference item(s) which are least
	 * recently used.
	 */
	public void setItem(Item item, int currentTime) {

		if (currSize == maxSize) {
			evictItem(currentTime);
		}

		// Get the linkedlist for the preference queue
		DoublyLinkedList<Item> dlist = null;
		if (preferrenceToListMap.containsKey(item.preference)) {
			dlist = preferrenceToListMap.get(item.preference);
		} else {
			dlist = new DoublyLinkedList<>();
			preferrenceToListMap.put(item.preference, dlist);
		}

		ListNode<Item> node = dlist.addFront(item);
		keyToItemNodeMap.put(item.key, node);

		// Update the expiry time pqueue
		pqByExpiryTime.add(node);

		// Update the preference pqueue
		pqByPreference.add(node);

		currSize++;
	}
	
	
	public static void main( String args[] ) {
        PriorityExpiryCache priorityExpiryCache = new PriorityExpiryCache(5);
        priorityExpiryCache.setItem(new Item("A", "val1", 5, 2), 0);
        priorityExpiryCache.setItem(new Item("B", "val2", 15, 3), 0);
        priorityExpiryCache.setItem(new Item("C", "val3", 5, 10), 0);
        priorityExpiryCache.setItem(new Item("D", "val4", 10, 9), 0);
        priorityExpiryCache.setItem(new Item("E", "val5", 5, 150), 0);

        priorityExpiryCache.getItem("C");

        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem(5);
        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem(5);
        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem(5);
        System.out.println(priorityExpiryCache.getKeys());

        priorityExpiryCache.evictItem(5);
        System.out.println(priorityExpiryCache.getKeys());
    }
}