package study.example.parallel;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;

/**
 * TestHashMapConcurrently.java version 2017, 01. 04
 * <p>
 * Copyright 2017 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
@Slf4j
public class TestHashMapConcurrently {

	public static final int MAX_MAP_VAL_SIZE = 1000000;

	static class TestDummyKey {
		private Long key;

		public TestDummyKey(Long key) {
			this.key = key;
		}
	}

	static class TestDummyVal {
		private String val;

		public TestDummyVal(String val) {
			this.val = val;
		}
	}

	public static void main(String[] args) {
		new TestHashMapConcurrently().test();
	}

	private void test() {
		Map<TestDummyKey, TestDummyVal> targetmap = new HashMap<>();

		Map<Long, Integer> results = new ConcurrentHashMap<>();

		LongStream.iterate(0L, i -> i + 1).limit(MAX_MAP_VAL_SIZE).boxed()
				.forEach(num -> targetmap.put(new TestDummyKey(num), new TestDummyVal(String.valueOf(num))));

		targetmap.keySet().parallelStream().forEach(i -> {
//			log.debug("Key: {}", i.key);
			TestDummyVal val = targetmap.get(i);
//			log.debug("Val: {}", val.val);
			if (results.containsKey(i.key)) {
				results.put(i.key, results.get(i.key)+1);
			}
			results.put(i.key, 0);
		});

		log.debug("result: {}", results.size());
	}
}
