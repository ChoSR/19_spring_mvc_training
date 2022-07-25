package com.spring.training;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect	
public class LoggingAdvice {

	private static Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);

	/*
	@Before("execution(* com.spring.training.boardAdvance.service.*.*(..))")
	public void beforeService(JoinPoint jp) {

		logger.info("---------------- Service Before -------------------");
		logger.info("name : " + jp.getSignature().getName());
		logger.info("args : " + Arrays.toString(jp.getArgs()));

	}
	
	@Before("execution(* com.spring.training.boardAdvance.dao.*.*(..))")
	public void beforeDao(JoinPoint jp) {

		logger.info("---------------- Dao Before -------------------");
		logger.info("name : " + jp.getSignature().getName());
		logger.info("args : " + Arrays.toString(jp.getArgs()));

	}
	@After("execution(* com.spring.training.boardAdvance.service.*.*(..))")
	public void afterService(JoinPoint jp) {

		logger.info("---------------- Service After -------------------");
		logger.info("name : " + jp.getSignature().getName());
		logger.info("args : " + Arrays.toString(jp.getArgs()));

	}
	*/
	@After("execution(* com.spring.training.boardAdvance.dao.*.*(..))")
	public void afterDao(JoinPoint jp) {

		logger.info("---------------- Dao After -------------------");
		logger.info("name : " + jp.getSignature().getName());
		logger.info("args : " + Arrays.toString(jp.getArgs()));

	}
	

}
