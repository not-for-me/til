package study.example.check;

/**
 * IsInteger.java version 2016, 12. 29
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class IsInteger {
	public boolean isInterger(double num) {
		if (num > Integer.MAX_VALUE) {
			return false;
		}

		return Math.floor(num) == Math.ceil(num);
	}
}
