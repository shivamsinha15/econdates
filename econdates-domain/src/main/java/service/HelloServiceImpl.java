package service;

import org.springframework.stereotype.Service;

import com.econdates.application.LogPlease;

@Service("helloService")
public class HelloServiceImpl implements HelloService {

	
	public void greeting(String name) {
		System.out.println("Greetings: " + name);
	}

}
