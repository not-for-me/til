package kr.notforme.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;

public class MyHelloAsyncService implements HelloService.AsyncIface {
	@Override
	public void hello(String name, AsyncMethodCallback resultHandler) throws TException {
		System.out.println("[" +Thread.currentThread().getStackTrace()[1].getClassName() +"] Start!!!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resultHandler.onComplete("Hello, " + name + '!');
	}
}

