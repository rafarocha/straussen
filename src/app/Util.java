package app;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
	
	public static long random(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max+ 1);
	}

	public static int getExpoent(int x) {
		if (x == 0) throw new RuntimeException();
		int q = 1;
		while(x > 2) {
			x /= 2;
			q++;
		}
		return q;
	}
	
}