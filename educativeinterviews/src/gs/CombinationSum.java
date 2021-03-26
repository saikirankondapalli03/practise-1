package gs;

import java.util.ArrayList;
import java.util.List;

public class CombinationSum {
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> finalList = new ArrayList<List<Integer>>();
        combinationSumHelper(finalList, null, candidates, target, 0);
        return finalList;
    }
    
    private void combinationSumHelper(List<List<Integer>> finalList, List<Integer> currentList, int[] candidates, int target, int pos) {
        if (currentList == null) currentList = new ArrayList<Integer>();
        if (target == 0) {
            finalList.add(currentList);
            return;
        }
        for (int i = pos; i < candidates.length; i++) {
            if (candidates[i] <= target) {
                ArrayList<Integer> originalList = new ArrayList<Integer>(currentList);
                currentList.add(candidates[i]);
                combinationSumHelper(finalList, currentList, candidates, target - candidates[i], i);
                currentList = originalList;
            }
        }
        return;
    }
}
