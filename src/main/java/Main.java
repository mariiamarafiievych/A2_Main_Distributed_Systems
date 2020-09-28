import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.*;
import entities.dto.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class Main {
    private static final String URL = "http://localhost:8081";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);

    public static void main(String[] args) {

        headers.setContentType(MediaType.APPLICATION_JSON);

        Supplier supplier1 = new Supplier( "Sasha", "Zaykina");
        List<Item> addedItems = createItemList(supplier1);
        List<Integer> quantities = Arrays.asList(1,2,3,4,5);

        addItemsOnSite(supplier1, addedItems, quantities);

        ResponseEntity<ItemsDTO> response2 = restTemplate
                .exchange(URL + "/items/getAll", HttpMethod.GET, headersEntity, ItemsDTO.class);
        printItems(Objects.requireNonNull(response2.getBody()).getItems());

        response2 = restTemplate
                .exchange(URL + "/items/getAll", HttpMethod.GET, headersEntity, ItemsDTO.class);
        List<Item> itemstoOrder = Objects.requireNonNull(response2.getBody()).getItems();
        printItems(itemstoOrder);

        Customer customer1 = new Customer( "Mariia", "Pinchuk");
        Customer customer2 = new Customer( "Kseniia", "Zayets");

        System.out.println("Trying to create new order");
        List<Item> CustomerShopCart1 = new ArrayList<>(itemstoOrder.subList(0, 2));
        makeOrder(customer1, supplier1, CustomerShopCart1);

        System.out.println("Trying to create new order");
        List<Item> CustomerShopCart2 = new ArrayList<>(itemstoOrder.subList(2, 5));

        makeOrder(customer2, supplier1, CustomerShopCart2);

        response2 = restTemplate
                .exchange(URL + "/items/getAll", HttpMethod.GET, headersEntity, ItemsDTO.class);
        printItems(Objects.requireNonNull(response2.getBody()).getItems());

        ResponseEntity<CustomerDTO> response4 = restTemplate
                .exchange(URL + "/customers/getAll", HttpMethod.GET, headersEntity, CustomerDTO.class);
        System.out.println("\nCustomers: ");
        for (Customer c : Objects.requireNonNull(response4.getBody()).getCustomers()) {
            System.out.println(c);
        }
        System.out.println("\n");

        ResponseEntity<OrdersDTO> response5 = restTemplate
                .exchange(URL + "/orders/getAll", HttpMethod.GET, headersEntity, OrdersDTO.class);
        System.out.println("\nOrders: ");
        for (Order o : Objects.requireNonNull(response5.getBody()).getOrders()) {
            System.out.println(o);
        }
        System.out.println("\n");
    }

    private static void makeOrder(Customer customer, Supplier supplier, List<Item> bucketForCustomer) {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setCustomer(customer);
        createOrderDTO.setSupplier(supplier);
        createOrderDTO.setItems(bucketForCustomer);
        HttpEntity<CreateOrderDTO> createOrder = new HttpEntity<>(createOrderDTO);
        ResponseEntity<Void> response4 = restTemplate
                .exchange(URL + "/orders/create", HttpMethod.POST,
                        createOrder, Void.class);
    }

    public static List<Item> createItemList(Supplier supplier1) {

        Item item1 = new Item("shampoo", 200);
        Item item2 = new Item("scrab", 100);
        Item item3 = new Item("soap", 50);
        Item item4 = new Item("creme", 300);
        Item item5 = new Item("mask", 400);

        return Arrays.asList(item1, item2, item3, item4, item5);
    }


    private static void addItemsOnSite(Supplier supplier1, List<Item> addedItems, List<Integer> quantities) {
        SupplyDTO supplyDTO = new SupplyDTO();
        supplyDTO.setSupplier(supplier1);
        supplyDTO.setItems(addedItems);
        System.out.println(supplier1);
        supplyDTO.setItemQuantities(quantities);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String supplyJsonStr = gson.toJson(supplyDTO);

        HttpEntity<String> suplyJson = new HttpEntity<>(supplyJsonStr, headers);
        ResponseEntity<Void> response1 = restTemplate
                .exchange(URL + "/suply/suplyItems", HttpMethod.POST, suplyJson, Void.class);

        System.out.println("Supplier " + supplier1.getLastName() + " has added " + addedItems);
    }

    private static void printItems(List<Item> items) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("\nItems: ");
        for (Item th : items) {
            stringBuilder.append("\n").append(th);
        }
        stringBuilder.append("\n");

        System.out.println(stringBuilder.toString());
    }

}
