package com.cleveloper.messagessimulator.publishconsumereactor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Mono<Order> store(@RequestBody Mono<Order> orderMono)
    {
        return orderService.save(orderMono);
    }

    @GetMapping("/{id}")
    public Mono<Order> find(@PathVariable("id") String id){
        return  orderService.findById(id);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Order> list()
    {
        return orderService.orders();
    }
}
