package com.educative.coderust.chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PythagoreanTriplets {
	static boolean isPythagoreanTriplets(int a, int b, int c) {
		int sqra = a * a;
		int sqrb = b * b;
		int sqrc = c * c;

		if (sqra + sqrb == sqrc || sqra + sqrc == sqrb || sqrb + sqrc == sqra) {
			return true;
		}

		return false;
	}

	/**
	 * @param arr
	 * @return
	 */
	static List<int[]> findPythagoreanTriplets(int[] arr) {
		int n = arr.length;
		List<int[]> triplets = new ArrayList<int[]>();
		for (int i = 0; i < n - 2; ++i) {
			if (arr[i] == 0)
				continue;

			for (int j = i + 1; j < n - 1; ++j) {
				if (arr[j] == 0)
					continue;

				for (int k = j + 1; k < n; ++k) {
					System.out.println("i=>"+i+"j=>"+j+"k=>"+k);
					if (isPythagoreanTriplets(arr[i], arr[j], arr[k])) {
						int[] triplet = { arr[i], arr[j], arr[k] };
						triplets.add(triplet);
					}
				}
			}
		}

		return triplets;
	}

	public static void main(String[] argv) {
		int[] l1 = { 4, 5, 6, 8, 10,25 };

		List<int[]> t1 = findPythagoreanTripletsApproach(l1);

		System.out.println("Original: " + Arrays.toString(l1));
		String result = "";

		for (int[] a : t1) {
			Arrays.sort(a);
			result += "[";
			for (int x : a) {
				result += Integer.toString(x) + ",";
			}
			result = result.replaceAll(",$", "");
			result += "]";
		}
		System.out.println("Pythagorean triplets: " + result);
	}
	
	
	static List<int[]> findPythagoreanTripletsApproach(int[] arr) {
	    int n = arr.length;
	    List<int[]> triplets = new ArrayList<int[]>();
	    Arrays.sort(arr);

	    for (int i = 0; i < n; ++i) {
	      if (arr[i] == 0) continue;

	      int c2 = arr[i] * arr[i];

	      int j = 0;
	      int k = n - 1;

	      while (j < k) {
	        if (j == i || arr[j] == 0) {
	          j += 1;
	          continue;
	        }

	        if (k == i || arr[k] == 0) {
	            k -= 1;
	              continue;
	        }
	        
	        int a2 = arr[j] * arr[j];
	        int b2 = arr[k] * arr[k];

	        if (a2 + b2 == c2) {
	          triplets.add(
	            new int[]{arr[j], arr[k], arr[i]});
	          break;
	        }
	        else if (a2 + b2 + (-c2) > 0) {
	          k -= 1;
	        }
	        else {
	          j += 1;
	        }
	      }
	    }
	    return triplets;
	  }
}