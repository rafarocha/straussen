package app;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws Exception {
		Model model = Model.create();
		System.out.println( model );
		
		Map<Integer, Stack<Long[][]>> map = model.mount();
		
		NaiveAlgorithm naive = new NaiveAlgorithm();
		
		for (Entry<Integer, Stack<Long[][]>> entry : map.entrySet()) {
			Long[][] m = naive.multiply( entry.getValue() );
			System.out.println( m );
		}
		
		System.out.println( "sucess" );
		
	}

}