package com.gs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagrams {
	 public static List<List<String>> groupAnagrams(String[] strs) {
	        List<List<String>> r = new ArrayList<>();
	        Map<String, List<String>> map = new HashMap<>();
	        for (String s : strs) {
	            char[] c = s.toCharArray();
	            Arrays.sort(c);
	            String t = new String(c);
	            map.putIfAbsent(t, new ArrayList<>());
	            map.get(t).add(s);
	        }
	        for (List<String> v : map.values()) {
	            r.add(v);
	        }
	        return r;
	    }
	 
		public static void main(String[] args) {
			GroupAnagrams.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
		}
}
