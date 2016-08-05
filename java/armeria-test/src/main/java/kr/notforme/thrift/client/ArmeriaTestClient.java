package kr.notforme.thrift.client;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.http.*;
import com.linecorp.armeria.common.http.*;
import kr.notforme.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * ArmeriaTestClient.java version 2016, 08. 06
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class ArmeriaTestClient {
	public static void main(String[] args) {
		ArmeriaTestClient client = new ArmeriaTestClient();
		try {
//			client.runThriftBasedClient();
//			client.runAsyncBasedClient();
			client.runHttpBasedClient();
//			client.runDeprecatedHttpBasedClient();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void runDeprecatedHttpBasedClient() throws ExecutionException, InterruptedException {
		SimpleHttpClient httpClient = Clients.newClient(
				"none+https://api.github.com", SimpleHttpClient.class);

		SimpleHttpRequest req =
				SimpleHttpRequestBuilder.forGet("/users/not-for-me")
						.header("Accept", "application/json")
						.build();

		Future<SimpleHttpResponse> f = httpClient.execute(req);
		SimpleHttpResponse res = f.get();
		System.out.println(new String(res.content()));
	}

	private void runHttpBasedClient() throws ExecutionException, InterruptedException {
		HttpClient httpClient = Clients.newClient(
				"none+https://api.github.com", HttpClient.class);

		HttpHeaders header = new DefaultHttpHeaders()
				.add(HttpHeaderNames.ACCEPT, "application/json")
				.method(HttpMethod.GET)
				.path("/users/not-for-me");

		HttpRequest req = new DefaultHttpRequest(header);

		HttpResponse f = httpClient.execute(req);

		f.subscribe(new Subscriber<HttpObject>() {
			@Override public void onSubscribe(Subscription s) {
				System.out.println("onSubscribe!!");
			}

			@Override public void onNext(HttpObject httpObject) {
				System.out.println("onNext!!");
				System.out.println(httpObject.toString());
			}

			@Override public void onError(Throwable t) {
				System.out.println("onError!!");
			}

			@Override public void onComplete() {
				System.out.println("Finish!!");
			}
		});
	}

	private void runThriftBasedClient() throws TException {
		HelloService.Iface helloService = Clients.newClient(
				"tbinary+http://127.0.0.1:8080/hello",
				HelloService.Iface.class);

		String greeting = helloService.hello("Armerian World");
		assert greeting.equals("Hello, Armerian World!");
		System.out.println(greeting);
	}


	private void runAsyncBasedClient() throws TException {
		HelloService.AsyncIface helloService = Clients.newClient(
				"tbinary+http://127.0.0.1:8080/helloAsync",
				HelloService.AsyncIface.class);

		helloService.hello("Armerian World", new AsyncMethodCallback<String>() {
			@Override
			public void onComplete(String response) {
				System.out.println(response);
				assert response.equals("Hello, Armerian World!");
			}

			@Override
			public void onError(Exception exception) {
				exception.printStackTrace();
			}
		});
	}
}
