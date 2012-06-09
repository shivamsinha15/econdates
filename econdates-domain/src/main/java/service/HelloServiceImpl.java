package service;

import org.springframework.stereotype.Service;

@Service("helloService")
public class HelloServiceImpl implements HelloService {

	public void greeting(String name) {
		System.out.println("Greetings: " + name);
	}

}
