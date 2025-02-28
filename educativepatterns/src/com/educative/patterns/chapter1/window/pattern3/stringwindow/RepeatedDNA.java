package com.educative.patterns.chapter1.window.pattern3.stringwindow;

import java.util.*;

//https://www.educative.io/module/page/Z4JLg2tDQPVv6QjgO/10370001/4976190424350720/6411992975015936

class RepeatedDNA {
	
    public static Set<String> findRepeatedSequences(String dna, int k) {
      
        int stringLength = dna.length();

        if (stringLength < k) {
            return new HashSet<String>();
        }

        Map<Character, Integer> mapping = new HashMap<>();
        mapping.put('A', 1);
        mapping.put('C', 2);
        mapping.put('G', 3);
        mapping.put('T', 4);

        int baseValue = 4;

        List<Integer> numbers = new ArrayList<>(Arrays.asList(new Integer[stringLength]));
        Arrays.fill(numbers.toArray(), 0);
        for (int i = 0; i < stringLength; i++) {
            numbers.set(i, mapping.get(dna.charAt(i)));
        }

        int hashValue = 0;

        Set<Integer> hashSet = new HashSet<>();
        Set<String> output = new HashSet<>();

        for (int i = 0; i < stringLength - k + 1; i++) {

            if (i == 0) {
                for (int j = 0; j < k; j++) {
                    hashValue += numbers.get(j) * (int) Math.pow(baseValue, k - j - 1);
                }
            } else {
                int previousHashValue = hashValue;
                hashValue = ((previousHashValue - numbers.get(i - 1) * (int) Math.pow(baseValue, k - 1)) * baseValue) + numbers.get(i + k - 1);
            }

            if (hashSet.contains(hashValue)) {
                output.add(dna.substring(i, i + k));
            }

            hashSet.add(hashValue);
        }
        return output;
      }

    // Driver code
    public static void main(String[] args) {
        List<String> inputsString = Arrays.asList("ACGT", "AGACCTAGAC", "AAAAACCCCCAAAAACCCCCC",
                "GGGGGGGGGGGGGGGGGGGGGGGGG", "TTTTTCCCCCCCTTTTTTCCCCCCCTTTTTTT", "TTTTTGGGTTTTCCA",
                "AAAAAACCCCCCCAAAAAAAACCCCCCCTG", "ATATATATATATATAT");
        List<Integer> inputsK = Arrays.asList(3, 3, 8, 12, 10, 14, 10, 6);

        for (int i = 0; i < inputsK.size(); i++) {
            System.out.println((i + 1) + ".\tInput sequence: " + inputsString.get(i) + 
                                         "\n\tk: " + inputsK.get(i) + 
                                          "\n\n\tRepeated sequences: " + Print.printSetString(findRepeatedSequences(inputsString.get(i), inputsK.get(i))));
            System.out.println(Print.repeat("-", 100));
        }
    }
}