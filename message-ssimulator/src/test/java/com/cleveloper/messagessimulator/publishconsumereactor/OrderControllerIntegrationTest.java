package com.cleveloper.messagessimulator.publishconsumereactor;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void listorders() {
        webTestClient.get().uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Order.class).hasSize(25);
    }

    @Test
    public void addAndGetOrder() {
        var order = new Order("test1", BigDecimal.valueOf(1234.56));
        webTestClient.post().uri("/orders").syncBody(order)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Order.class).isEqualTo(order);

        webTestClient.get().uri("/orders/{id}", order.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Order.class).isEqualTo(order);
    }
}
