package ru.practicum_services.qa_scooter.responses.order;

import java.util.List;

public class Orders {
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
