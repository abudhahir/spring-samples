package com.cleveloper.messagessimulator.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController {
    @GetMapping("/greet")
    public Mono<String> sayHello(){
        return Mono.just("Hello world... from webflux");
    }
}
