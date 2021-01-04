package com.educative.multithreading.chapter7.practise.tokenbucket.solution1;

import java.util.HashSet;
import java.util.Set;

class TokenBucketFilterTen {
	private int MAX_TOKENS;
	private long lastRequestTime = System.currentTimeMillis();
 	long possibleTokens = 0;
	
 	public TokenBucketFilterTen(int maxTokens) {
		MAX_TOKENS = maxTokens;
		System.out.println("TokenBucketFilterTen => "+lastRequestTime/1000);
 	}

	synchronized void getToken() throws InterruptedException {
		//System.out.println("came to access token at Time" + System.currentTimeMillis()/1000);
		possibleTokens += (System.currentTimeMillis() - lastRequestTime) / 1000;
		
		System.out.println("possibleTokens ===> " + possibleTokens);
		if (possibleTokens > MAX_TOKENS) {
			System.out.println("max reached");
			possibleTokens = MAX_TOKENS;
		}

		if (possibleTokens == 0) {
			Thread.sleep(1000);
		} else {
			possibleTokens--;
		}
		lastRequestTime = System.currentTimeMillis();

		System.out.println(
				"Granting " + Thread.currentThread().getName() + " token at " + (System.currentTimeMillis() / 1000));
	}

	public static void runTestMaxTokenIsTen() throws InterruptedException {

		Set<Thread> allThreads = new HashSet<Thread>();
		final TokenBucketFilterTen tokenBucketFilter = new TokenBucketFilterTen(20);

		// Sleep for 10 seconds.
		Thread.sleep(6000);

		// Generate 12 threads requesting tokens almost all at once.
		for (int i = 0; i < 12; i++) {

			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						tokenBucketFilter.getToken();
					} catch (InterruptedException ie) {
						System.out.println("We have a problem");
					}
				}
			});
			thread.setName("Thread_" + (i + 1));
			allThreads.add(thread);
		}

		for (Thread t : allThreads) {
			t.start();
		}

		for (Thread t : allThreads) {
			t.join();
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		TokenBucketFilterTen.runTestMaxTokenIsTen();
		
	}

	
}
