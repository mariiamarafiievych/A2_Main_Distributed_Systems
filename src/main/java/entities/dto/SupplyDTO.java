package entities.dto;

import entities.Supplier;
import entities.Item;

import java.util.List;

public class SupplyDTO {

    private List<Item> items;
    private List<Integer> itemQuantities;
    private Supplier supplier;

    public SupplyDTO() {
    }

    public SupplyDTO(List<Item> items, List<Integer> itemQuantities, Supplier supplier) {
        this.items = items;
        this.itemQuantities = itemQuantities;
        this.supplier = supplier;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Integer> getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(List<Integer> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "SupplyDTO{" +
                "items=" + items +
                ", itemQuantities=" + itemQuantities +
                ", supplier=" + supplier +
                '}';
    }
}

