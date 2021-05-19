package app;

import static app.Util.random;

public class StraussenAlgorithm {
	
	public static void main(String[] args) {
		StraussenAlgorithm sa = new StraussenAlgorithm();
		int length = (int) Math.pow(2, 3);
		long[][] a = new long[length][length];
		long[][] b = new long[length][length];
		long[][] c = new long[length][length];
		
		for (int j = 0; j < a.length; j++) {
			for (int k = 0; k < a[j].length; k++) {
				a[j][k] = random(1, 10);
				b[j][k] = random(1, 10);
			}
		}
		
		sa.straussen(a, b, c);
	}
	
	public void straussen(long[][] a, long[][] b, long[][] c, int ... limit) {
		
		int dual = 0, high = 0, half = 0, deal, init = 0;
		int length = a.length;
		
		if ( limit.length != 0 ) {
			int height = limit[1] - limit[0];
			if (height == 15) { 
				base(a, b, c, limit);
				return;
			} else {
				init = limit[0];
				length = (height + 1) / 4;
				dual = init + length - 1;
				high = limit[1];
				half = dual + length;
				deal = high - length;	
			}
		} else {
			init = 1;
			high = length * length;
			
			treatWhenMatrixSmallerOrEqual_4x4(a, b, c, high, init);
			
			dual = high / 4;
			half = high / 2;
			deal = half + dual;	
		}
		
		straussen(a, b, c,     init, dual);
		straussen(a, b, c, (dual+1), half);
		straussen(a, b, c, (half+1), deal);
		straussen(a, b, c,   deal+1, high);
	}

	private void treatWhenMatrixSmallerOrEqual_4x4(long[][] a, long[][] b, long[][] c, int high, int init) {
		if (high == 16) {
			straussen(a, b, c, init, high);
			throwExceptionMatrixMinorOrEqual_4x4();
		} if (high < 16) {
			throwExceptionMatrixMinorOrEqual_4x4();
		}
	}
	
	public void throwExceptionMatrixMinorOrEqual_4x4() {
		throw new RuntimeException("matrix minor than 16, 4x4");
	}
	
	private static int count = 0;

	private void base(long[][] a, long[][] b, long[][] c, int[] limit) {
		System.out.println(++count + " : filled : " + limit[0] + ", " + (limit[1]) );
		Tuple t = new Tuple(a, b, c, limit);
		
		long P = t.m2() + t.m3() - t.m6() -t.m7();
		long Q = t.m4() + t.m6();
		long R = t.m5() + t.m7();
		long S = t.m1() - t.m3() - t.m4() - t.m5();
		
		t.fillPQRS();
	}
	
	class Tuple {
		private long[][] a; 
		private long[][] b; 
		private long[][] c; // result
		private int s; // start
		private int f; // finish

		public Tuple(long[][] a, long[][] b, long[][] c, int[] limit) {
			this.a = a; this.b = b; this.c = c; 
			this.s = limit[0]-1; this.f = limit[1]-1;
		}
		
		public void fillPQRS() {
			long P = m2() + m3() - m6() - m7();
			long Q = m4() + m6();
			long R = m5() + m7();
			long S = m1() - m3() - m4() - m5();
			
			c[s][s] = P; c[s][s+1] = Q; c[s+1][s] = R; c[s+1][s+1] = S;
			
		}
		
		private Long m1() { // (a1 + a3) * (b1 + b2)
			return ( a[s][s] + a[s+1][s] ) * ( b[s][s] + b[s][s+1] ); 
		}
		private Long m2() { // (a1 + a4) * (b3 + b4)
			return ( a[s][s] + a[s+1][s+1] ) * ( b[s+1][s] + b[s+1][s+1] ); 
		}
		private Long m3() { // (a1 - a4) * (b1 + b4)
			return ( a[s][s] - a[s+1][s+1] ) * ( b[s][s] + b[s+1][s+1] ); 
		}
		private Long m4() { // a1 * (b2 - b4)
			return ( a[s][s] * ( b[s][s+1] - b[s+1][s+1]) ); 
		}
		private Long m5() { // (a3 + a4) * b1
			return ( a[s+1][s] + a[s+1][s+1] ) * b[s][s]; 
		}
		private Long m6() { // (a1 + a2) * b4
			return ( a[s][s] + a[s][s+1] ) * b[s+1][s+1]; 
		}
		private Long m7() { // a4 * (b3 - b1)
			return ( a[s+1][s+1] * (b[s+1][s] - b[s][s])); 
		}
		
	}
	
}