package kr.notforme.thrift.client;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.http.*;
import com.linecorp.armeria.common.http.AggregatedHttpMessage;
import com.linecorp.armeria.common.http.HttpHeaderNames;
import com.linecorp.armeria.common.http.HttpHeaders;
import com.linecorp.armeria.common.http.HttpMethod;
import kr.notforme.thrift.HelloService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

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
//						client.runDeprecatedHttpBasedClient();
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

		AggregatedHttpMessage getJson = AggregatedHttpMessage.of(
				HttpHeaders.of(HttpMethod.GET, "/users/not-for-me")
						.set(HttpHeaderNames.ACCEPT, "application/json"));

		AggregatedHttpMessage jsonResponse = httpClient.execute(getJson).aggregate().join();
		System.out.println(jsonResponse.content().toStringUtf8());
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
