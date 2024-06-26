package com.demo;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;

@ControllerAdvice
public class ResilienceExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ResilienceExceptionHandler.class);

	// bulkhead related exception
	@ExceptionHandler({ BulkheadFullException.class })
	@ResponseStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	public void handleBulkheadFullException(BulkheadFullException ex) {
		logger.error("BulkheadFullException Recieved (resilience4J Bulkhead). HTTP Response Status code set to: "
				+ HttpStatus.BANDWIDTH_LIMIT_EXCEEDED+" ,thread id : "+Thread.currentThread().getId()+" ,thread name : "+Thread.currentThread().getName(),ex);
	}

	// timelimiter related exception
	@ExceptionHandler({ TimeoutException.class })
	@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
	public void handleTimeoutException(TimeoutException ex) {
		ex.printStackTrace();
		logger.error("TimeoutException Recieved (resilience4J TimeLimiter). HTTP Response Status code set to: "
				+ HttpStatus.REQUEST_TIMEOUT+" ,thread id : "+Thread.currentThread().getId()+" ,thread name : "+Thread.currentThread().getName(),ex);
	}

	// ratelimiter related exception
	@ExceptionHandler({ RequestNotPermitted.class })
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public void handleRequestNotPermitted(RequestNotPermitted ex) {
		logger.error(
				"RequestNotPermitted exception Recieved (resilience4J RateLimiter) . HTTP Response Status code set to: "
						+ HttpStatus.TOO_MANY_REQUESTS+" ,thread id : "+Thread.currentThread().getId()+" ,thread name : "+Thread.currentThread().getName(),ex);
	}

	// circuit breaker related exception
	@ExceptionHandler({ CallNotPermittedException.class })
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public void handleCallNotPermittedException(CallNotPermittedException ex) {
		logger.error(
				"CallNotPermittedException Recieved (resilience4J CircuitBreaker) . HTTP Response Status code set to: "
						+ HttpStatus.SERVICE_UNAVAILABLE+" ,thread id : "+Thread.currentThread().getId()+" ,thread name : "+Thread.currentThread().getName(),ex);
	}

}
