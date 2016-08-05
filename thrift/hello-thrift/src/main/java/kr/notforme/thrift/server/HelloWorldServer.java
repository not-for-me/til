package kr.notforme.thrift.server;

import kr.notforme.thrift.HelloWorld;
import kr.notforme.thrift.service.HelloWorldImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloWorldServer {
	public static void main(String[] argss) {
		try {
			TBinaryProtocol.Factory portFactory = new TBinaryProtocol.Factory(true, true);
			TServerSocket serverTransport = new TServerSocket(8000);
			HelloWorld.Processor<HelloWorld.Iface> processor =
					new HelloWorld.Processor<HelloWorld.Iface>(new
							HelloWorldImpl());
			TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(portFactory);
			TServer server = new TThreadPoolServer(args);
			System.out.println("Starting server on port 8000 ...");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}


