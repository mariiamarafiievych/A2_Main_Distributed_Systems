import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.*;
import entities.dto.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
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

        ResponseEntity<Item[]> response2 = restTemplate
                .exchange(URL + "/items", HttpMethod.GET, headersEntity, Item[].class);
        List<Item> things = Arrays.asList(Objects.requireNonNull(response2.getBody()));
        System.out.println(things);

        response2 = restTemplate
                .exchange(URL + "/items", HttpMethod.GET, headersEntity, Item[].class);
        List<Item> thingsForSale = Arrays.asList(Objects.requireNonNull(response2.getBody()));
        System.out.println(thingsForSale);

        Customer customer1 = new Customer( "Mariia", "Pinchuk");
        Customer customer2 = new Customer( "Kseniia", "Zayets");

        System.out.println("Trying to create new order");
        List<Item> bucketForCustomer1 = new ArrayList<>(thingsForSale.subList(0, 2));
        makeOrder(customer1, supplier1, bucketForCustomer1);

        System.out.println("Trying to create new order");
        List<Item> bucketForCustomer2 = new ArrayList<>(thingsForSale.subList(2, 5));
        makeOrder(customer2, supplier1, bucketForCustomer2);

        response2 = restTemplate
                .exchange(URL + "/items", HttpMethod.GET, headersEntity, Item[].class);
        System.out.println(Arrays.asList(Objects.requireNonNull(response2.getBody())));

        ResponseEntity<Customer[]> response4 = restTemplate
                .exchange(URL + "/customers", HttpMethod.GET, headersEntity, Customer[].class);
        System.out.println( "\nCustomers: ");
        List<Customer> customers = Arrays.asList(Objects.requireNonNull(response4.getBody()));
        System.out.println(customers);
        System.out.println("\n");

        ResponseEntity<Order[]> response5 = restTemplate
                .exchange(URL + "/orders", HttpMethod.GET, headersEntity, Order[].class);
        System.out.println("\nOrders: ");
        List<Order> orders = Arrays.asList(Objects.requireNonNull(response5.getBody()));
        System.out.println(orders);
        System.out.println("\n");
    }

    private static void makeOrder(Customer customer, Supplier supplier, List<Item> bucketForCustomer) {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setCustomer(customer);
        createOrderDTO.setSupplier(supplier);
        createOrderDTO.setItems(bucketForCustomer);
        HttpEntity<CreateOrderDTO> createOrder = new HttpEntity<>(createOrderDTO);
        ResponseEntity<Void> response4 = restTemplate
                .exchange(URL + "/orders", HttpMethod.POST,
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


    private static void addItemsOnSite(Supplier supplier1, List<Item> addedThings, List<Integer> quantities) {
        SupplyDTO supplyDTO = new SupplyDTO();
        supplyDTO.setSupplier(supplier1);
        supplyDTO.setItems(addedThings);
        System.out.println(supplier1);
        supplyDTO.setItemQuantities(quantities);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String serveJsonStr = gson.toJson(supplyDTO);

        HttpEntity<String> serveJson = new HttpEntity<>(serveJsonStr, headers);
        ResponseEntity<Void> response1 = restTemplate
                .exchange(URL + "/suply", HttpMethod.POST, serveJson, Void.class);

        System.out.println("Supplier " + supplier1.getLastName() + " has added " + addedThings);
    }
}