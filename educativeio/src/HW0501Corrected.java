import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class HW0501Corrected {
	public static void main(String[] args) {
		//Transition Probability
		/*
		 * double[][] p = { 
						timid->  { 0.1(lose), 0.8(draw), 0.1(win)}, 
						bold ->  { 0.55(lose), 0(draw) , 0.45(win)} 
						};
		 * 
		 */
		
		double[][] p = { 
						 { 0.1, 0.8, 0.1 }, 
						 { 0.55, 0, 0.45 } 
						};
		
	
		double[][] valueFunction = {
				{ 0, 0, 0, 0, 0, 0.5 , 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0.5, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0.5, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0.5, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0.5, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0.5, 1, 1, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 0.5, 1, 1, 1, 1, 1 }
				};
	
		
		double[][] policyFunction = {
				{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
				{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
				{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
				{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
				{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
				{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8},
				{8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}
				};
		
		
		for(int games=0;games<5;games++) 
		{
			for(int opponent=0; opponent<(9-2*games);opponent++) 
			{
				double[] choices= {0,0};
				for(int i=0;i<2;i++) 
				{
					for(int j=0;j<3;j++) 
					{
						choices[i] +=p[i][j]*valueFunction[games][opponent+games+j];
					}
				}
				List<Double> list= DoubleStream.of(choices).boxed().collect(Collectors.toCollection(ArrayList::new));
				double maxValue=Collections.max(list);
				valueFunction[games+1][opponent+games+1] =maxValue ;
				policyFunction[games+1][opponent+games+1]= list.indexOf(maxValue);
			}
			
		}
		
		System.out.println("Value function");
		for(int i=0;i<valueFunction.length;i++) {
			System.out.println(Arrays.toString(valueFunction[i]));
		}
		
		System.out.println("Policy function");
		for(int i=0;i<policyFunction.length;i++) {
			System.out.println(Arrays.toString(policyFunction[i]));
		}
	}

}
