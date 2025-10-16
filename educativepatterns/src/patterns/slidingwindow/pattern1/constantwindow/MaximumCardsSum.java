package patterns.slidingwindow.pattern1.constantwindow;

//https://takeuforward.org/plus/dsa/sliding-window-and-2-pointer/constant-window/maximum-points-you-can-obtain-from-cards-

/*
 * 
 * 
 * Given N cards arranged in a row, each card has an associated score denoted by the cardScore array.
 *  Choose exactly k cards. In each step, a card can be chosen either from the beginning or the end of the row.
 * The score is the sum of the scores of the chosen cards.Return the maximum score that can be obtained.
 */
//Completed: GOOD
public class MaximumCardsSum {

	public static int maxScore(int[] cardScore, int k) {
		int lSum = 0, rSum = 0, maxSum = 0;
		// Calculate the initial sum of the first k cards
		for (int i = 0; i < k; i++) {
			lSum += cardScore[i];
			/*
			 * Initialize maxSum with the sum of the first k cards
			 */
			maxSum = lSum;
		}
		// Initialize rightIndex to iterate array from last
		int rightIndex = cardScore.length - 1;
		for (int i = k - 1; i >= 0; i--) {
			// Remove the score of the ith card from left sum
			lSum -= cardScore[i];
			/*
			 * Add the score of the card from the right to the right sum
			 */
			rSum += cardScore[rightIndex];
			// Move to the next card from the right
			rightIndex--;
			// Update maxSum with the maximum sum found so far
			maxSum = Math.max(maxSum, lSum + rSum);
		}
		// Return the maximum score found
		return maxSum;
	}

	public static void main(String[] args) {
		System.out.println(MaximumCardsSum.maxScore(new int[] {1, 2, 3, 4, 5, 6, 1}, 3));
		System.out.println(MaximumCardsSum.maxScore(new int[] { 5, 4, 1, 8, 7, 1, 3 }, 3));
		System.out.println(MaximumCardsSum.maxScore(new int[] { 9, 10, 1, 2, 3, 5 }, 5));
	}

}
