package java8.common;

/**
 * CommonUtil.java version 2016, 08. 12
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class CommonUtil {
	public static void delay(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
