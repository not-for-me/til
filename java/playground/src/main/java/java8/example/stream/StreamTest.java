package java8.example.stream;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * StreamTest.java version 2016, 08. 17
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class StreamTest {
	public static void main(String[] args) {
		//		(new StreamTest()).testIsStreamRunLikePipeline();
		(new StreamTest()).testStreamWithRxJava();
	}

	private void testIsStreamRunLikePipeline() {
		List<Integer> list = IntStream.range(0, 10)
				.map(i -> {
					System.out.println("First Map: " + i);
					return i;
				})
				.map(i -> {
					System.out.println("Second Map: " + i);
					return i;
				})
				.filter(i -> i > 5)
				.boxed()
				.collect(Collectors.toList());
	}

	private void testStreamWithRxJava() {
		Observable.range(0, 10)
				.observeOn(Schedulers.from(Executors.newSingleThreadExecutor(r -> {
					Thread t = new Thread(r);
					t.setDaemon(true);
					t.setName("First Thread");
					return t;
				})))
				.map(i -> {
					System.out.println("[" + Thread.currentThread().getName() + "] First Map: " + i);
					return i;
				})
				.observeOn(Schedulers.from(Executors.newSingleThreadExecutor(r -> {
					Thread t = new Thread(r);
					t.setDaemon(true);
					t.setName("Second Thread");
					return t;
				})))
				.map(i -> {
					System.out.println("[" + Thread.currentThread().getName() + "] Second Map: " + i);
					return i;
				})
				.filter(i -> i > 5)
				.subscribe(i -> {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("[" + Thread.currentThread().getName() + "] Final : " + i);
				});

		int i = 0;
		while (i < 10) {
			System.out.println("[" + Thread.currentThread().getName() + "] main~~~ : " + i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
}
