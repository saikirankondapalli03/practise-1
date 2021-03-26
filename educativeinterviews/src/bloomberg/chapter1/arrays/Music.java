package bloomberg.chapter1.arrays;


class Music {
    public static int numPairsDivisibleBy60(int[] time) {
        
        int[] arr = new int[60];
        int rs = 0;
        for (int i = 0; i < time.length; i++) {
            int mod = time[i]%60; // 120%60=0, 1%60=1
            int target = (60-mod)%60; // 120 target = 0; 1 then target=59
            rs = rs + arr[target];
            arr[mod]++;            
        }
        return rs;
    }
    
    public static void main(String[] args) {
    	Music.numPairsDivisibleBy60(new int[] {37,32,60});
    }
}