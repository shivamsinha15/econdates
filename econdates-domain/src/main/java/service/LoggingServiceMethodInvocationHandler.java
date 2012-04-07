package service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingServiceMethodInvocationHandler implements InvocationHandler {

	private Object HelloServiceImpl;

	public LoggingServiceMethodInvocationHandler(Object HelloServiceImpl) {
		this.HelloServiceImpl = HelloServiceImpl;
	}

	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {
		System.out.println("Invoking Method: " + method.getName() + " With Arugment: " +args[0].toString());
		method.invoke(HelloServiceImpl, args);
		System.out.println("Succefully Invoked: " + method.getName());
		return null;
	}
}
