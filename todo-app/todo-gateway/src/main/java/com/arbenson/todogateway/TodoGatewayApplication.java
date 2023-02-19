package com.arbenson.todogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.bind.annotation.RequestMapping;
//import reactor.core.publisher.Mono;

@SpringBootApplication
public class TodoGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoGatewayApplication.class, args);
	}

//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder){
//
//		return builder.routes()
//				.route(p-> p
//						.path("/api/auth/**")
//						.filters(f->f.addRequestHeader("Hello","World"))
//						.uri("http://localhost:8085/"))
////						.uri("http://httpbin.org:80"))
//				.route(p-> p
//						.path("/api/v1/**")
//						.uri("http://localhost:8086/")
//				)
//				.route(p-> p.host("*.circuitbreaker.com")
//						.filters(f->f.circuitBreaker(config -> config
//								.setName("mycmd")
//								.setFallbackUri("forward:/fallback")))
//						.uri("http://httpbin.org:80"))
//
//				.build();
//	}


//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://httpbin.org:80"))
//				.route(p -> p
//						.host("*.circuitbreaker.com")
//						.filters(f -> f.circuitBreaker(config -> config
//								.setName("mycmd")
//								.setFallbackUri("forward:/fallback")))
//						.uri("http://httpbin.org:80"))
//				.build();
//	}



//	@Bean
//	public ReactiveResilience4JCircuitBreakerFactory factory()
//	{
//		return new ReactiveResilience4JCircuitBreakerFactory();
//	}
//
//	@RequestMapping("/fallback")
//	public Mono<String> fallback(){
//		return Mono.just("fallback");
//	}

}

