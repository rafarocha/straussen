package app;

import static app.Util.random;

import java.util.ArrayList;
import java.util.List;

public class Runner {
	
	static final int SLEEP = 0;
	static final int MINIMAL = 5; 
	static final String FILENAME = "file_thin";
	
	public static void main(String[] args) throws Exception {
		Model model = Model.create( FILENAME );
		
		for (int expoent = MINIMAL; expoent <= model.kmax; expoent++) {
			List<Long> timers = new ArrayList<Long>();

			for (int r = 1; r <= model.r; r++) {
				long start = System.currentTimeMillis();
				execute( naive, model, expoent );
				record ( start, timers );	
			}
			
			report( expoent, timers, model.r );
		}		
	}

	private static void record(long start, List<Long> timers) {
		long timeElapsed = timeElapsed( start );
		timers.add( timeElapsed );
	}
	
	private static void report(int expoent, List<Long> timers, int r) {
		
		long records = 0;
		for (Long time : timers) {
			records += time;
		}
		
		String sized = String.format("%02d", expoent);
		String rstr = String.format("%02d", r);
		System.out.println("r " + rstr + " : size " + sized + " : time " + (records/r) );
	}
	
	private static long timeElapsed(long start) {
		long finish = System.currentTimeMillis();
		return finish - start;
	}
	
	private static long[][] mtx(int length) {
		return new long[length][length];	
	}
	
	private static void execute(Algorithm algo, Model model, int expoent) {
		int length = (int) Math.pow(2, expoent);
		long[][] a = mtx(length), b = mtx(length), c = mtx(length);
		
		mount(model, a, b);
		algo.multiply(a, b, c);
	}

	private static void mount(Model model, long[][] a, long[][] b) {
		for (int j = 0; j < a.length; j++) {
			for (int k = 0; k < a[j].length; k++) {
				a[j][k] = random(model.int_min, model.int_max);
				b[j][k] = random(model.int_min, model.int_max);
				
			}
		}
	}
	
	static final NaiveAlgorithm naive = new NaiveAlgorithm();
	static final StraussenAlgorithm straussen = new StraussenAlgorithm();

}