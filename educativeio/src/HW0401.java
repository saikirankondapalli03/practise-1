import java.util.Arrays;
import java.util.stream.LongStream;

public class HW0401 {
	int ocCostC0 = 1;
	int ocCostC1 = 1;
	int revenuePerPeriod = 5;
	int replacementCost = 10;
	double discountFactor = 0.8;
	double mu = 0.2;
	int lambda = 1;
	int totalStates = 20;
	double expTerm = Math.exp(-1 * lambda);

	public double getOperatingCost(int x) {
		return ocCostC0 + (ocCostC1*x);
	}

	public double getSalvageValue(int x) {
		return (discountFactor*replacementCost* (Math.exp(-1 * mu * x)));
	}

	public double getDeteriorationProbability(int j) {
		long factorial = LongStream.rangeClosed(1, j).reduce(1, (long a, long b) -> a * b);
		return (Math.pow(lambda, j)) * expTerm / factorial;
	}

	public double[] getDeteriroationForAllStates() {
		double[] detProbStates = new double[totalStates];
		for (int i = 0; i < totalStates; i++) {
			detProbStates[i] = getDeteriorationProbability(i);
		}
		return detProbStates;
	}

	public static void main(String[] args) {
		HW0401 e= new HW0401();
		double[] valueIteration= e.getValueFunction();
		System.out.println("Value Iteration ===> ");
		System.out.println(Arrays.toString(valueIteration));
		
		double[] policyIteration= e.getPolicyFunction();
		System.out.println("Policy Iteration ===> ");
		System.out.println(Arrays.toString(policyIteration));
	}

	
	public double[] getValueFunction() {
		double[] detProb = getDeteriroationForAllStates();
		double[] v = new double[totalStates];
		long epochs = 10000l;
		double[] temp = new double[totalStates];
		for (int i = 0; i < epochs; i++) 
		{
			temp = v;
			for (int j = 0; j < totalStates; j++) 
			{
				double term0 = revenuePerPeriod - replacementCost + getSalvageValue(j) - ocCostC0;
				double term1 = revenuePerPeriod - getOperatingCost(j);
				for (int k = 0; k < totalStates; k++) {
					term0 += detProb[k] * (Math.pow(0.9, k + 1)) * temp[k];
					if (j + k < totalStates) {
						term1 += detProb[k] * (Math.pow(0.9, k + 1)) * temp[k + j];
					}
				}
				double maxValue = Math.max(term0, term1);
				v[j] =maxValue;
			}
		}
		return v;
	}
	
	public double[] getPolicyFunction() 
	{
		double[] detProb = getDeteriroationForAllStates();
		double[] v = new double[totalStates];
		double[] policy = new double[totalStates];
		long epochs = 1000l;
		double[] temp = new double[totalStates];
		long main = 100;
		for (int p = 0; p < main; p++) {
			//policy evaluation
			for (int i = 0; i < epochs; i++) {
				temp = v;
				for (int j = 0; j < totalStates; j++) {
					double term0 = revenuePerPeriod - replacementCost + getSalvageValue(j) - ocCostC0;
					double term1 = revenuePerPeriod - getOperatingCost(j);
					for (int k = 0; k < totalStates; k++) {
						term0 += detProb[k] * (Math.pow(0.9, k + 1)) * temp[k];
						if (j + k < totalStates) {
							term1 += detProb[k] * (Math.pow(0.9, k + 1)) * temp[k + j];
						}
					}
					double maxValue = Math.max(term0, term1);
					v[j] = maxValue;
				}
			}
			
			//policy improvement
			for (int state = 0; state < totalStates; state++) {
				double term0 = revenuePerPeriod - replacementCost + getSalvageValue(state) - ocCostC0;
				double term1 = revenuePerPeriod - getOperatingCost(state);
				for (int j = 0; j < totalStates; j++) {
					term0 += detProb[j] * (Math.pow(0.9, j + 1)) * v[j];
					if (state + j < totalStates) {
						term1 += detProb[j] * (Math.pow(0.9, j + 1)) * temp[state + j];
					}
				}
				if (term0 > term1) {
					policy[state] = 0;
				} else {
					policy[state] = 1;
				}
			}

		}
		return policy;
	}
}
