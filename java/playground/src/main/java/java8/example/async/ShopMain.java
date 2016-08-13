package java8.example.async;

import java8.common.CommonUtil;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ShopMain.java version 2016, 08. 12
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class ShopMain {
	private static List<Shop> shops = IntStream.range(1, 6)
			.boxed().map(i -> new Shop("Shop " + String.valueOf(i)))
			.collect(Collectors.toList());

	private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});

	public static void main(String[] args) {
		(new ShopMain()).testStreamConcurrency();
		//		(new ShopMain()).testBasicCompletableFuture();
		//		(new ShopMain()).testConcurrency();
//		(new ShopMain()).testImprovedConcurrency();
	}

	private void testStreamConcurrency() {
		long start = System.nanoTime();
		// IDE type error ?
//		CompletableFuture[] futures = findPriceStream("myPhone27S")
//				.map(f -> f.thenAccept(System.out::println))
//				.toArray(size -> new CompletableFuture[size]);


		CompletableFuture[] futures = findPriceStream("myPhone27S")
				.map(f -> f.thenAccept(
						s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
				.toArray(size -> new CompletableFuture[size]);

//		CompletableFuture.allOf(futures).join();
		CompletableFuture.anyOf(futures).join();
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}

	private void testImprovedConcurrency() {
		long start = System.nanoTime();
		System.out.println(findImprovedPrices("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}

	private void testConcurrency() {
		long start = System.nanoTime();
		System.out.println(findPrices("myPhone27S"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("Done in " + duration + " msecs");
	}

	private void testBasicCompletableFuture() {
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
		long invocationTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Invocation returned after " + invocationTime + " msecs");

		doOtherThing();

		try {
			double price = futurePrice.get();
			System.out.printf("Price is %.2f%n", price);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");
	}

	private void doOtherThing() {
		CommonUtil.delay(1500);
	}

	public List<String> findPrices(String product) {
		// Sequential...
		//		return shops.stream()
		//				.map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
		//				.collect(Collectors.toList());

		// Parallel...
		//				return shops.parallelStream()
		//						.map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
		//						.collect(Collectors.toList());

		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture
						.supplyAsync(() -> {
							System.out.println("[" + Thread.currentThread().getName() + "] Shop: " + shop.getName());
							return String.format("%s price is $%2f", shop.getName(), shop.getPrice(product));
						}, executor))
				.collect(Collectors.toList());

		return priceFutures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
	}

	private List<String> findImprovedPrices(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
				.map(future -> future.thenApply(Quote::parse))
				.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
//				.map(future -> future
//						.thenComposeAsync(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
				.collect(Collectors.toList());

		return priceFutures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
	}


	private Stream<CompletableFuture<String>> findPriceStream(String product) {
		return shops.stream()
				.map(shop ->CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
				.map(future -> future.thenApply(Quote::parse))
				.map(future ->future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
	}
}
