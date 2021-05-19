package app;

import java.util.Stack;

public class NaiveAlgorithm {
	
	public Long[][] multiply(Stack<Long[][]> stack) {
		Long[][] a = stack.pop();
		Long[][] b = stack.pop();
		
		Long[][] x = new Long[a.length][a.length]; 
		
	    for (int row = 0; row < x.length; row++) {
	        for (int col = 0; col < x[row].length; col++) {
	            x[row][col] = multiplyCell(a, b, row, col);
	        }
	    }
		return x;
	}
	
	Long multiplyCell(Long[][] a, Long[][] b, int row, int col) {
	    Long cell = 0L;
	    for (int i = 0; i < b.length; i++) {
	        cell += a[row][i] * b[i][col];
	    }
	    return cell;
	}
	
	Long[][] multiply(Long[][] a, Long[][] b) {
		Long[][] result = new Long[a.length][b[0].length];

	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = multiplyCell(a, b, row, col);
	        }
	    }

	    return result;
	}
	
}