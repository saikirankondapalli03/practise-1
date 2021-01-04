package com.educative.coderust.chapter1.arrays;

import java.util.LinkedList;
import java.util.List;

class Tuple<X, Y> {
	public X x;
	public Y y;

	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}
}

class StockMaxProfit {
	public static Tuple findBuySellStockPrices(int[] array) {
		if (array == null || array.length < 2) {
			return null;
		}

		int current_buy = array[0];
		int global_sell = array[1];
		int global_profit = global_sell - current_buy;

		int current_profit = Integer.MIN_VALUE; 
// 12, 5, 9, 19 
		for (int i = 1; i < array.length; i++) {
			current_profit = array[i] - current_buy;
 
			if (current_profit > global_profit) {
				global_profit = current_profit;
				global_sell = array[i];
			}

			if (current_buy > array[i]) {
				current_buy = array[i];
			}
		}

		Tuple<Integer, Integer> result = new Tuple<Integer, Integer>(global_sell - global_profit, global_sell);

		return result;
	}

	public static int maxProfit(int prices[]) {
		int minprice = Integer.MAX_VALUE;
		int maxprofit = 0;
		for (int i = 0; i < prices.length; i++) {
			if (prices[i] < minprice)
				minprice = prices[i];
			else if (prices[i] - minprice > maxprofit)
				maxprofit = prices[i] - minprice;
		}
		return maxprofit;
	}

	private static final String[] KEYS = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
    
	public static List<String> letterCombinations(String digits) {
		List<String> ret = new LinkedList<String>();
		combination("", digits, 0, ret);
		return ret;
	}

	private static void combination(String prefix, String digits, int offset, List<String> ret) {
		if (offset >= digits.length()) {
			ret.add(prefix);
			return;
		}
		String letters = KEYS[(digits.charAt(offset) - '0')];
		for (int i = 0; i < letters.length(); i++) {
			combination(prefix + letters.charAt(i), digits, offset + 1, ret);
		}
	}


	public static void main(String[] args) {
		int[] array = { 12, 5, 9, 19 };
		Tuple result = null;
		//System.out.println(letterCombinations("23"));
		result = findBuySellStockPrices(array);
		
		System.out.println(maxProfit(array));
		System.out.println(String.format("Buy Price: %d, Sell Price: %d", result.x, result.y));

		int[] array2 = { 8, 6, 5, 4, 3, 2, 1 };
		result = findBuySellStockPrices(array2);
		System.out.println(String.format("Buy Price: %d, Sell Price: %d", result.x, result.y));

	}
}