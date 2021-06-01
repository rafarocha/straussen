package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Model {
	int kmax;
	int r;
	
	int int_min;
	int int_max;
	
	public Model(String kmax, String r, String interval) {
		kmax( kmax ).r( r ).interval( interval );
	}
	
	public static Model create(InputStream stream) {
		try {
			BufferedReader reader = new BufferedReader( 
					new InputStreamReader( stream ) );
			
			String kmax;
			kmax = reader.readLine();
			String r = reader.readLine();
			String interval = reader.readLine();
			reader.close();
			
			Model model = new Model(kmax, r, interval); 
			System.out.println( model );
			
			return model; 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public Model kmax(String kmax) {
		this.kmax = Integer.valueOf(kmax); return this;
	}
	
	public Model r(String r) {
		this.r = Integer.valueOf(r); return this;
	}
	
	public Model interval(String interval) {
		int[] array 
			= Arrays
				.stream( interval.split(" "))
				.mapToInt(Integer::parseInt).toArray();
		this.int_min = array[0];
		this.int_max = array[1];
		return this;
	}
	
	@Override public String toString() {
		return "k_max " + this.kmax 
				+ " : r " + this.r 
				+ " : interval " + this.int_min + " " + this.int_max;
	}
	
}