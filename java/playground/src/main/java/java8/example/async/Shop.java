package java8.example.async;

import java8.common.CommonUtil;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Shop.java version 2016, 08. 12
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class Shop {
	private String name;

	public Shop(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPrice(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[(new Random()).nextInt(Discount.Code.values().length)];
		return String.format("%s:%.2f:%s", name, price, code);
	}

	public double getPriceString(String product) {
		return calculatePrice(product);
	}

	private double calculatePrice(String product) {
//		CommonUtil.delay(1000);
		CommonUtil.randomDelay();
		return (new Random()).nextDouble() * product.charAt(0) + product.charAt(1);
	}

	public Future<Double> getPriceAsync(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product));
		//		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		//		new Thread(() -> {
		//			try {
		//			double price = calculatePrice(product);
		//			futurePrice.complete(price);
		//			} catch (Exception e) {
		//				futurePrice.completeExceptionally(e);
		//			}
		//		}).start();
		//		return futurePrice;
	}


}
