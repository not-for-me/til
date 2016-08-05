package kr.notforme.thrift.bootstrap;

import com.linecorp.armeria.common.SerializationFormat;
import com.linecorp.armeria.common.SessionProtocol;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.thrift.THttpService;
import kr.notforme.thrift.HelloService;
import kr.notforme.thrift.MyHelloAsyncService;
import kr.notforme.thrift.MyHelloSyncService;

/**
 * RpcServer.java version 2016, 08. 05
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class RpcServer {
	public static void main(String[] args) {
		new RpcServer().run();
	}

	private void run() {
		HelloService.Iface helloHandler = new MyHelloSyncService();
		HelloService.AsyncIface asyncHelloHandler = new MyHelloAsyncService();

		ServerBuilder sb = new ServerBuilder();
		sb.port(8080, SessionProtocol.HTTP);
		sb.serviceAt(
				"/hello",
				THttpService.of(helloHandler, SerializationFormat.THRIFT_BINARY)
						.decorate(LoggingService::new))
								.serviceAt(
										"/helloAsync",
										THttpService.of(asyncHelloHandler, SerializationFormat.THRIFT_BINARY)
												.decorate(LoggingService::new))
				.serviceAt("/text", THttpService.of(helloHandler, SerializationFormat.THRIFT_TEXT))
				//				.serviceUnder("/docs/", new DocService())
				.build();

		Server server = sb.build();
		server.start();
	}
}
