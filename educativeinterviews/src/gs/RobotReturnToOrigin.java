package gs;

import java.util.HashMap;
import java.util.Map;

public class RobotReturnToOrigin {

	public boolean judgeCircle(String moves) {
	    
	    Map<Character, Integer> hm = new HashMap<>();
	    hm.put('L', -1);
	    hm.put('R', 1);
	    hm.put('D', -5);
	    hm.put('U', 5);
	    
	    int sum = 0;
	    for(int i=0; i<moves.length(); i++){
	        sum += hm.get(moves.charAt(i));
	    }
	    
	    if(sum == 0)
	        return true;

	return false;
	}
	
}
