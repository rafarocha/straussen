package app;

public class NaiveAlgorithm implements Algorithm {
	
	public void multiply(long[][] a, long[][] b, long[][] c) {
		
	    for (int row = 0; row < c.length; row++) {
	        for (int col = 0; col < c[row].length; col++) {
	            c[row][col] = multiplyCell(a, b, row, col);
	        }
	    }
	}
	
	long multiplyCell(long[][] a, long[][] b, int row, int col) {
	    Long cell = 0L;
	    for (int i = 0; i < b.length; i++) {
	        cell += a[row][i] * b[i][col];
	    }
	    return cell;
	}
	
	long[][] multiply(long[][] a, long[][] b) {
		long[][] result = new long[a.length][b[0].length];

	    for (int row = 0; row < result.length; row++) {
	        for (int col = 0; col < result[row].length; col++) {
	            result[row][col] = multiplyCell(a, b, row, col);
	        }
	    }

	    return result;
	}
	
}