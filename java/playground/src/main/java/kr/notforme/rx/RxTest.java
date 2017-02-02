package kr.notforme.rx;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;

import java.util.Arrays;

/**
 * RxTest.java version 2017, 02. 01
 * <p>
 * Copyright 2017 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
@Slf4j
public class RxTest {
	public static void main(String[] args) {
		Observable.from(Arrays.asList(1,2,3,4,5,6))
				.flatMap(i -> Observable.from(i))
				.doOnNext(i -> log.debug("num: {}", i))
				.subscribe();

		log.debug("main");
	}
}
