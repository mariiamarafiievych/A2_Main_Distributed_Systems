package entities;

import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private List<Item> orderedItem;
    private Supplier supplier;
    private Customer customer;

    public Order() {
    }

    public Order(List<Item> orderedItem, Supplier supplier, Customer customer) {
        this.orderedItem = orderedItem;
        this.supplier = supplier;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Item> getOrderedItem() {
        return orderedItem;
    }

    public void setOrderedItem(List<Item> orderedItem) {
        this.orderedItem = orderedItem;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedItem=" + orderedItem +
                ", supplier=" + supplier +
                ", customer=" + customer +
                '}';
    }
}

