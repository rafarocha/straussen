package app;

import static app.Util.random;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Runner {
	
	public static void main(String[] args) throws Exception {
		
		// select algorithm and filename
		Model model = getModel(args); // change inside method SAMPLE1-3
		Algorithm algorithm = getAlgorithm(args);

		System.out.println( "\nstarting ... \n\n" );
		
		// execute
		for (int expoent = Defaults.MINIMAL; expoent <= model.kmax; expoent++) {
			List<Long> timers = new ArrayList<Long>();

			for (int r = 1; r <= model.r; r++) {
				long start = System.currentTimeMillis();
				execute( algorithm, model, expoent );
				record ( start, timers );	
			}
			
			report( expoent, timers, model.r );
		}		
	}
	
	private static boolean hasSecondValidArgument(String[] args) {
		return ( args != null && args.length > 0 && args[1] != null && !args[1].equals("") );
	}

	private static Model getModel(String[] args) {
		return ( hasSecondValidArgument(args) ) 
			? getModelAsPath( args[1] )
			: Model.create( getFileAsStream(Defaults.SAMPLE1) );
	}
	
	private static Model getModelAsPath(String filename) {
		return Model.create( getFileAsPath(filename) );
	}

	private static Algorithm getAlgorithm(String[] args) {
		return (args != null && args.length > 0)
			? ( (args[0].equals("naive")) ? Defaults.NAIVE : Defaults.STRAUSSEN )
			: Defaults.STRAUSSEN;
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
		System.out.println("kmax 2^" + sized + " : time " + (records/r) );
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
	
	private static InputStream getFileAsStream(String filename) {
		System.out.println( "inside JAR file: " + filename );
		return Defaults.STRAUSSEN.getClass().getResourceAsStream( filename );
	}

	private static InputStream getFileAsPath(String filename) {
		try {
			File file = Paths.get( filename ).toFile();
			System.out.println( "file: " + file.getAbsolutePath() );
			
			return new FileInputStream( file );
		} catch (FileNotFoundException e) {
			throw new RuntimeException("file not found: " + filename);
		}
	}
	
	interface Defaults {
		int SLEEP = 0;
		int MINIMAL = 5; 
		
		String SAMPLE1 = "sample1";
		String SAMPLE2 = "sample2";
		String SAMPLE3 = "sample3";
		
		Algorithm STRAUSSEN = new StraussenAlgorithm();
		Algorithm NAIVE = new NaiveAlgorithm();
	}
	
}