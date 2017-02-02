package kr.notforme.tobytv.reactor.concurrency;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * ReactiveStreamEx.java version 2016, 12. 21
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
@Slf4j
public class ReactiveStreamConcurrency {
	public static void main(String[] args) {
		/**
		 * Publisher는 데이터를 전달 받을 Subscriber를 등록할 수 있는 인터페이스 subscribe만 가짐
		 */
		Publisher<String> publisher = new Publisher<String>() {
			public void subscribe(final Subscriber<? super String> s) {
				s.onSubscribe(new Subscription() {
					private long tempReqCnt = 0;

					public void request(long n) {
						// Subscriber가 하는 요청에 따라 적절한 값을 보낸다. 간단하게는 그냥 onNext...
						tempReqCnt = n;
						while (tempReqCnt-- > 0) {
							s.onNext("foo");
						}
					}

					public void cancel() {
						s.onComplete();
					}
				});
			}
		};

		publisher.subscribe(new Subscriber<String>() {
			public void onSubscribe(Subscription s) {
				log.info("onSubscribe");

				int tempReqCnt = 10;
				while (tempReqCnt-- > 0) {
					s.request(1);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				s.cancel();
			}

			public void onNext(String s) {
				log.info("onNext: {}", s);
			}

			public void onError(Throwable t) {
				log.info("onError");
			}

			public void onComplete() {
				log.info("onComplete");
			}
		});
	}
}
