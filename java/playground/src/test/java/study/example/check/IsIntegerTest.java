package study.example.check;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * IsIntegerTest.java version 2016, 12. 29
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class IsIntegerTest {
	private IsInteger tester = new IsInteger();

	@Test
	public void isInterger() throws Exception {
		assertFalse(tester.isInterger(Double.MAX_VALUE));
		assertFalse(tester.isInterger(Integer.MAX_VALUE + 1));
		assertFalse(tester.isInterger(1.0));
		assertFalse(tester.isInterger(1.2));
		assertFalse(tester.isInterger(1.5));
		assertFalse(tester.isInterger(1.8));
		assertFalse(tester.isInterger(2.0));
	}

}