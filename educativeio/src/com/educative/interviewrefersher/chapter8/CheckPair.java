package com.educative.interviewrefersher.chapter8;

import java.util.HashMap;

//Hash Set  =>  HashSet<Integer> hSet = new HashSet<>();
//HashMap   =>  HashMap<Integer,String> hMap = new HashMap<>();  
//HashTable =>  Hashtable<Integer,String> hTable = new Hashtable<>();  
//Hash Set Functions => {add(), remove(), contains()}
//Hash Map and Table Functions => {put(key,value), get(key), remove(key), containsKey(key), containsValue(value)}

class CheckPair {

	public static String findPair(int[] arr) {

		String result = "";

		// Create HashMap with Key being sum and value being a pair i.e key = 3 , value
		// = {1,2}
		// Traverse all possible pairs in given arr and store sums in map
		// If sum already exist then print out the two pairs.
		HashMap<Integer, int[]> hMap = new HashMap<>();

		for (int i = 0; i < arr.length; ++i) {
			for (int j = i + 1; j < arr.length; ++j) {
				int sum = arr[i] + arr[j]; // calculate sum

				if (!hMap.containsKey(sum)) {
					// If sum is not present in Map then insert it alongwith pair
					hMap.put(sum, new int[] { arr[i], arr[j] });
				} else {
					// Sum already present in Map
					int[] prev_pair = hMap.get(sum);

					// Since array elements are distinct, we don't
					// need to check if any element is common among pairs
					result += "{" + prev_pair[0] + "," + prev_pair[1] + "}{" + arr[i] + "," + arr[j] + "}";

					return result;
				}
			}
		} // end of for
		return result;
	}

	public static void main(String args[]) {

		int[] arr = { 3, 4, 7, 1, 12, 9,10,6 };
		System.out.println(findPair(arr));

	}
}