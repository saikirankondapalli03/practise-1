package bloomberg.chapter1.arrays;

class CandyCrush {
	public static int[][] candyCrush(int[][] b) {
		int R = b.length, C = b[0].length;
		boolean todo = false;
		for (int r = 0; r < R; ++r) 
		{
			for (int c = 0; c + 2 < C; ++c)
			{
				System.out.println("ra,"+r+"ca,"+c);
				int v = Math.abs(b[r][c]);
				if (v != 0 && v == Math.abs(b[r][c + 1]) && v == Math.abs(b[r][c + 2])) {
					System.out.println("setting r,c r,c+1 r,c+2 "+r+","+c);
					System.out.println("setting value to "+ -v);
					b[r][c] = b[r][c + 1] = b[r][c + 2] = -v;
					todo = true;
				}
			}
		}
		for (int r = 0; r + 2 < R; ++r) 
		{
			for (int c = 0; c < C; ++c) 
			{
				System.out.println("rb,"+r+"cb,"+c);

				int v = Math.abs(b[r][c]);
				if (v != 0 && v == Math.abs(b[r + 1][c]) && v == Math.abs(b[r + 2][c])) {
					System.out.println("setting r,c r+1,c r+2,c "+r+","+c);
					System.out.println("setting value to "+ -v);
				
					b[r][c] = b[r + 1][c] = b[r + 2][c] = -v;
					todo = true;
				}
			}
		}

		for (int c = 0; c < C; ++c) 
		{
			int wr = R - 1;
			for (int r = R - 1; r >= 0; --r)
				if (b[r][c] > 0) {
					b[wr][c] = b[r][c];
					wr--;
				}
			while (wr >= 0) {
				b[wr][c] = 0;
				wr--;
			}
		}

		System.out.println("before going to");
		return todo ? candyCrush(b) : b;
	}
	
	public static void main(String[] args) {
		
		int[][] a= {{110,5,112,113,114},{210,211,5,213,214},{310,311,3,313,314},{410,411,412,5,414},{5,1,512,3,3},{610,4,1,613,614},{710,1,2,713,714},{810,1,2,1,1},{1,1,2,2,2},{4,1,4,4,1014}};
		int[][] result=CandyCrush.candyCrush(a);
		System.out.println(result);
		
	}
	
	

	@Override
	public String toString() {
		return "CandyCrush [toString()=" + super.toString() + "]";
	}
	
	
}