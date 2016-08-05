package java8.example.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * ParallelStreamTest.java version 2016, 08. 05
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
@Slf4j
public class ParallelStreamTest {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ParallelStreamTest testRunner = new ParallelStreamTest();

		testRunner.testDefaultParallelStream();
		testRunner.testFixedForkJoinPool();
	}

	private void testDefaultParallelStream() {
		long startTime = System.nanoTime();

		IntStream.range(0, 10).parallel().forEach(printNumbersConsumer());

		double estimatedTime = (System.nanoTime() - startTime)/ 1000000000.0;
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " Elapsed Time: " +  estimatedTime  + "s");
	}

	private void testFixedForkJoinPool() throws ExecutionException, InterruptedException {
		long startTime = System.nanoTime();

		ForkJoinPool myPool = new ForkJoinPool(10);
		myPool.submit(() -> {
			IntStream.range(0, 10).parallel().forEach(printNumbersConsumer());
		}).get();

		double estimatedTime = (System.nanoTime() - startTime)/ 1000000000.0;
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " Elapsed Time: " +  estimatedTime  + "s");
	}

	private IntConsumer printNumbersConsumer() {
		return i -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.debug("idx: {}", i);
		};
	}
}
