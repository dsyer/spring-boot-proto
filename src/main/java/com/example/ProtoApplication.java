package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.CustomerProtos.Customer;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ProtoApplication {

	private Customer customer;

	@GetMapping("/")
	Customer home() {
		return this.customer == null
				? Customer.newBuilder().setId(0).setFirstName("Josh").setLastName("Long")
						.build()
				: this.customer;
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.ACCEPTED)
	Mono<Void> post(@RequestBody Mono<Customer> input) {
		return input.doOnSuccess(value -> this.customer = value).then();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProtoApplication.class, args);
	}
}
