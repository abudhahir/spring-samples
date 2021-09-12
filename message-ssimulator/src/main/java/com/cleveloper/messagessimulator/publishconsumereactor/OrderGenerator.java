package com.cleveloper.messagessimulator.service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class OrderGenerator {
    public Order generate() {
        var amount = ThreadLocalRandom.current().nextDouble();
        return new Order(UUID.randomUUID().toString(), BigDecimal.valueOf(amount));
    }
}
