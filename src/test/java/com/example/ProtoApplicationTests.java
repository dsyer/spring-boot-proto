package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

import demo.CustomerProtos.Customer;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ProtoApplicationTests {

	@Autowired
	private WebTestClient client;

	@Test
	public void get() {
		client.get().uri("/").exchange().expectBody(Customer.class)
				.value(customer -> assertThat(customer.getFirstName()).isNotNull());
	}

	@Test
	public void post() {
		Customer customer = Customer.newBuilder().setId(1).setFirstName("Juergen")
				.setLastName("Hoeller").build();
		client.post().uri("/").body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isAccepted();
		client.get().uri("/").exchange().expectBody(Customer.class)
				.value(value -> assertThat(value).isEqualTo(customer));
	}

}
