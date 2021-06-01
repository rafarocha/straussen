package app;

import static app.Util.random;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Model {
	int kmax;
	int r;
	
	int int_min;
	int int_max;
	
	public Model(String kmax, String r, String interval) {
		kmax( kmax ).r( r ).interval( interval );
	}
	
	public static Model create(String filename) {
		try {
			String file = "src/app/".concat( filename );
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String kmax;
			kmax = reader.readLine();
			String r = reader.readLine();
			String interval = reader.readLine();
			reader.close();
			return new Model(kmax, r, interval);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public Map<Integer, Stack<Long[][]>> mount() {
		Map<Integer, Stack<Long[][]>>  map = new HashMap<Integer, Stack<Long[][]>>();
		Stack<Long[][]> stack = new Stack<Long[][]>();

		for (int i = 2; i < 2; i++) { //kmax
//			for (int r = 0; r < 2; r++) { //this.r
				if ( !map.containsKey(i) ) {
					stack = new Stack<Long[][]>();
					map.put(i, stack);
				} else {
					stack = map.get(i);
				}
				stack.add( matriz(i) );
//			}
			System.out.println( "finish =" + i );
		}
		return map;
	}
	
	public Long[][] matriz(int i) {
		int order = (int) Math.pow(2, i);		
	
		Long[][] matriz = new Long[order][order];
		for (int j = 0; j < matriz.length; j++) {
			for (int k = 0; k < matriz[j].length; k++) {
				matriz[j][k] = random(this.int_min, this.int_max);
			}
		}

		return matriz;
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
		return "k_max=" + this.kmax 
				+ ", r=" + this.r 
				+ ", [" + this.int_min + ", " + this.int_max + "]";
	}
	
}