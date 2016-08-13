package java8.common;

import java.util.Random;

/**
 * CommonUtil.java version 2016, 08. 12
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class CommonUtil {
	private static final Random random = new Random();

	public static void delay(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void randomDelay() {
		int delay = 500 + random.nextInt(2000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
