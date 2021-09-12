package com.cleveloper.messagessimulator.publishconsumereactor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Order {
    private String id;

    private BigDecimal amount;

    @Override
    public String toString() {
        return String.format("Order [id='%s', amount=%4.2f]", id, amount);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(amount, order.amount);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
