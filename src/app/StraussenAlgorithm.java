package app;

import static app.StraussenAlgorithm.Way.lclc;
import static app.StraussenAlgorithm.Way.rclc;
import static app.StraussenAlgorithm.Way.sclc;
import static app.StraussenAlgorithm.Way.tclc;
import static app.StraussenAlgorithm.Way.uclc;

public class StraussenAlgorithm implements Algorithm {
	
	public void multiply(long[][] a, long[][] b, long[][] c) {
		straussen(a, b, c);
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
	
	static int mode = 0;
	static int step = 0;
	static int index = 0;
	static int x = -2;
	static int y = 2; 

	private void base(long[][] a, long[][] b, long[][] c, int[] limit) {
		index++;
//		System.out.print(index + " : " + limit[0] + ", " + (limit[1]) );

		long start = System.currentTimeMillis();
		Tuple tuple = new Tuple(a, b, c, limit);

		for (int i = 0; i < 4; i++) {
			tuple.position( i, c.length );
			tuple.fill();
		}
		
		tuple.change(a.length);

		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;		
//		System.out.println(" : " + timeElapsed );
	}
	
	enum Way {
		lclc, cclc, rclc, sclc, 
			mclc, hclc, tclc, uclc;
		public char action(int offset) {
			return this.toString().charAt(offset);
		}
	}
	
	enum Route {
		Q(lclc, rclc, sclc, rclc),
			W(tclc, rclc, uclc, rclc);
		
		private Way[] modes;
		
		Route(Way ... modes) {
			this.modes = modes;
		}
		
		public Way way() {
			return this.modes[ step ];	
		}
	}
	
	class Tuple {
		private long[][] a, b, c; // a x b = c 
		private int s, f;   // start, finish
		private Route route;
		
		public Tuple(long[][] a, long[][] b, long[][] c, int[] limit) {
			this.a = a; this.b = b; this.c = c; 
			this.s = limit[0]; this.f = limit[1];
			this.route = (mode == 0) ? Route.Q : Route.W;
		}
		
		public boolean isBetween(int lower, int upper) {
			  return lower <= index && index <= upper;
		}
		
		public void change(int length) {
			step = ( step == 3 ) ? 0 : step+1; 
			
			if (index <= 1) return;
			if ( !isMultipleOf4(index) ) return;

			mode = (mode == 1) ? 0 : 1;
		}
		
		public boolean isMultipleOf4(int n) {
	        if (n == 1)
	           return false;
	      
	        int XOR = 0;
	        for (int i = 1; i <= n; i++)
	            XOR = XOR ^ i;
	      
	        return (XOR == n);
	    }
		
		public void position(int offset, int length) {
			Way mode = this.route.way();
			switch ( mode.action(offset) ) {
				case 'l': {
					x += 2;
					if ( index-1 == length/2 ) // rotate
						y -= y;
					else 
						y -= (index > 0 ) ? 2 : y; // nullable
					break;
				}
				case 'c': {
					y += 2;
					break;
				}
				case 'r': { 
					x -= 2;	
					y += 2;
					break;
				}
				case 's': {
					x += 2;
					y -= y; // nullable
					break;
				}
				case 't': {
					int quarter = (length - (length/4));
					if (index == quarter) {
						x = x - y;
					} else {
						x -= x; // nullable
					}
					y += 2;						
					break;
				}
				case 'u': {
					x += 2;
					y = (y/2) +1;
					break;
				}
				default:
			}
			
			// for debug
//			int indexx = index;
//			int stepp = step;
//			String route = this.route.name();
//			String modee = mode.name();
//			int offsett = offset;
//			char pos = mode.toString().charAt(offset);
//			int ox = x;
//			int oy = y;
//			
//			if (index == 12) {
//				System.out.print("");				
//			}
//			
//			System.out.print("");
		}
		
		public void fill() {
			c  [x][y]   = p();
			c  [x][y+1] = q(); 
			c[x+1][y]   = r(); 
			c[x+1][y+1] = s();
		}
		
		private Long p() {
			return m2() + m3() - m6() - m7();
		}
		private Long q() {
			return m4() + m6();
		}
		private Long r() {
			return m5() + m7();
		}
		private Long s() {
			return m1() - m3() - m4() - m5();
		}
		private Long m1() { // (a1 + a3) * (b1 + b2)
			return ( a[x][y] + a[x+1][y] ) * ( b[x][y] + b[x][y+1] ); 
		}
		private Long m2() { // (a1 + a4) * (b3 + b4)
			return ( a[x][y] + a[x+1][y+1] ) * ( b[x+1][y] + b[x+1][y+1] ); 
		}
		private Long m3() { // (a1 - a4) * (b1 + b4)
			return ( a[x][y] - a[x+1][y+1] ) * ( b[x][y] + b[x+1][y+1] ); 
		}
		private Long m4() { // a1 * (b2 - b4)
			return ( a[x][y] * ( b[x][y+1] - b[x+1][y+1]) ); 
		}
		private Long m5() { // (a3 + a4) * b1
			return ( a[x+1][y] + a[x+1][y+1] ) * b[x][y];
		}
		private Long m6() { // (a1 + a2) * b4
			return ( a[x][y] + a[x][y+1] ) * b[x+1][y+1]; 
		}
		private Long m7() { // a4 * (b3 - b1)
			return ( a[x+1][y+1] * (b[x+1][y] - b[x][y])); 
		}
	}
	
}