package kr.notforme.thrift.client;

import kr.notforme.thrift.HelloWorld;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * HelloWorldClient.java version 2016, 08. 05
 * <p>
 * Copyright 2016 Ticketmonster Corp. All rights Reserved.
 * Ticketmonster PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
public class HelloWorldClient {
	public static void main(String[] args) {
		try {
			TTransport transport;
			transport = new TSocket("localhost", 8000);
			TProtocol protocol = new TBinaryProtocol(transport);
			HelloWorld.Client client = new HelloWorld.Client(protocol);

			transport.open();
			client.hello("Welcome to Thrift");
			transport.close();

		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
