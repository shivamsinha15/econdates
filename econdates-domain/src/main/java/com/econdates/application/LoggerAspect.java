package com.econdates.application;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggerAspect {

	@Around("@annotation(com.econdates.application.LogPlease)")                      	
	public Object LogGreetMethodExecution(ProceedingJoinPoint pjp) throws Throwable{
		Object[] args = pjp.getArgs();
		
		System.out.println("Invoking Method: " + pjp.getSignature().getName() + " With Arugment: " + args[0]);
		Object returnObject = pjp.proceed();
		System.out.println("Succefully Invoked: " + pjp.getSignature().getName());
		System.out.println(pjp.getStaticPart().toString());
		return returnObject;
	
	}

}
	