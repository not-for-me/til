package kr.notforme.thrift;

import org.apache.thrift.TException;

public class MyHelloSyncService implements HelloService.Iface {
	public String hello(String name) throws TException {
		System.out.println("[" +Thread.currentThread().getStackTrace()[1].getClassName() +"] Start!!!");
		return "Hello, " + name + '!';
	}
}

