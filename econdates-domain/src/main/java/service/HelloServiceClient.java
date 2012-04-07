package service;

import java.lang.reflect.Proxy;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class HelloServiceClient {

	public static void main(String... args) {

		HelloService hello = (HelloService) Proxy.newProxyInstance(
				HelloService.class.getClassLoader(),
				new Class<?>[] { HelloService.class },
				new LoggingServiceMethodInvocationHandler(
						new HelloServiceImpl()));

		hello.greeting("Shiv");

	}

}