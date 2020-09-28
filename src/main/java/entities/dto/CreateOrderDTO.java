package entities.dto;

import entities.Customer;
import entities.Supplier;
import entities.Item;

import java.util.List;

public class CreateOrderDTO {

    private Supplier supplier;
    private Customer customer;
    private List<Item> items;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

