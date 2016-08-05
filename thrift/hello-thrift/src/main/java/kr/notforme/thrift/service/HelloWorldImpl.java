package kr.notforme.thrift.service;

import kr.notforme.thrift.HelloWorld;
import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorld.Iface {
	public void hello(String name) throws TException {
		long time = System.currentTimeMillis();
		System.out.println("Current time : " + time);
		System.out.println("Hello " + name);
	}
}
