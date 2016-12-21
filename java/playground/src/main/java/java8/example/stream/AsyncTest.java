package java8.example.stream;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * AsyncTest.java version 2016, 12. 11
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class AsyncTest {
	private static String getThreadName() {
		return Thread.currentThread().getName();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("[" + getThreadName() + "] Hello World");

		// Thread....
		Observable
				.interval(1000, TimeUnit.MILLISECONDS)
				.observeOn(Schedulers.from(Executors.newSingleThreadExecutor(r -> {
					Thread t = new Thread(r);
					t.setDaemon(true);
					t.setName("Sub Thread");
					return t;
				})))
				.doOnNext(_n -> System.out.println("[" + getThreadName() + "]: " + _n))
				.subscribe();

		Thread.sleep(5000);
		System.out.println("[" + getThreadName() + "] Program End");

	}
}


