package com.demo;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ResilientAppController {

	private final BackendServiceCaller backendServiceCaller;
	private final ApiServiceInvoker apiServiceInvoker;
	

	@Autowired
	public ResilientAppController(BackendServiceCaller backendServiceCaller, ApiServiceInvoker apiServiceInvoker) {
		this.backendServiceCaller=backendServiceCaller;
		this.apiServiceInvoker = apiServiceInvoker;
	}

	@GetMapping("bizTrxA")
	public String bizTrxA() throws IOException {
		System.out.println("inside bixTrxA "+Thread.currentThread().getName());
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		System.out.println("Stack trace from bizTrxA ");
		for(StackTraceElement element: stackTrace)
			System.out.println(element);
		//return backendServiceCaller.callAps();
		return apiServiceInvoker.callAps();

	}

	@GetMapping("bizTrxB")
	public CompletableFuture<String> bizTrxB() {
		System.out.println("inside bixTrxB "+Thread.currentThread().getName());
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		System.out.println("Stack trace from bizTrxB ");
		for(StackTraceElement element: stackTrace)
			System.out.println(element);
		return apiServiceInvoker.callEcprAsync();
	}

	@GetMapping("bizTrxC")
	public String bizTrxC() throws IOException {
		return apiServiceInvoker.callEws();
	}

	@GetMapping("bizTrxD")
	public String bizTrxD() throws IOException {
		return apiServiceInvoker.callSims();
	}

	@GetMapping("bizTrxE")
	public String bizTrxE() throws IOException {
		return apiServiceInvoker.callAng();
	}

	@GetMapping("bizTrxF")
	public CompletableFuture<String> bizTrxF() {
		return apiServiceInvoker.callLexisNexisAsync();
	}

}
