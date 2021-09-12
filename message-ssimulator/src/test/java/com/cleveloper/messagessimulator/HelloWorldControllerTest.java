package com.cleveloper.messagessimulator;

import com.cleveloper.messagessimulator.controllers.HelloWorldController;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class HelloWorldControllerTest {



    @Test
    public void shouldSayHello() {
        Mono<String> result = helloWorldController.sayHello();
        StepVerifier.create(result)
                .expectNext("Hello world... from webflux")
                .verifyComplete();
    }


    private final HelloWorldController helloWorldController = new HelloWorldController();
}
