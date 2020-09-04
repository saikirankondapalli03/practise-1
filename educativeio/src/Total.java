import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

class Total{
	public static int count;
	static {
		System.out.println("In Block 1");
		count=10;
	}
	private int[]data;
	{
		System.out.println("In Block 2");
		data =new int[count];

	for(int i=0;i<count;i++) {
		data[i]=1;
	}
	}
	public static void main(String[]args) {
		
	}

}
