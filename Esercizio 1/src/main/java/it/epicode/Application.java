package it.epicode;

import com.github.javafaker.Faker;
import it.epicode.classes.Customer;
import it.epicode.classes.Order;
import it.epicode.classes.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        Supplier<Integer> integerSupplier = () -> {
            Random rndm = new Random();
            return rndm.nextInt(1, 100);
        };

        Supplier<Customer> userSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            return new Customer(faker.name().firstName(), integerSupplier.get());
        };

        Supplier<Product> productSupplier = () -> {
            Faker faker = new Faker(Locale.ITALY);
            return new Product(faker.book().title(), faker.book().genre(), integerSupplier.get().doubleValue());
        };

        Customer customer1 = new Customer("Stefano", 4);
        Customer customer2 = userSupplier.get();
        Customer customer3 = userSupplier.get();

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

        List<Product> productsList1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productsList1.add(productSupplier.get());
        }

        List<Product> productsList2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productsList2.add(productSupplier.get());
        }

        List<Product> productsList3 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productsList3.add(productSupplier.get());
        }

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order("Completed", LocalDate.parse("2021-05-28"), LocalDate.parse("2021-05-30"), productsList1, customer1);
        Order order2 = new Order("Completed", LocalDate.parse("2021-02-28"), LocalDate.parse("2021-03-31"), productsList2, customer2);
        Order order3 = new Order("Completed", LocalDate.parse("2021-02-28"), LocalDate.parse("2021-03-31"), productsList3, customer3);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        //ESERCIZIO 1
        System.out.println("ESERCIZIO 1");

        Map<Customer, List<Order>> customerOrders = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));

        customerOrders.forEach((customer, orderList) -> {
            System.out.println("Cliente: " + customer.getName());
            System.out.print("Ordine numero #");
            orderList.forEach(order -> System.out.println(Math.abs(order.getId())));
            System.out.println("Prodotti:");
            orderList.forEach(order -> order.getProducts()
                    .forEach(product -> System.out.println("- " + product.getName() + ", " + product.getPrice() + "€")));
        });
        System.out.println();

        //ESERCIZIO 2
        System.out.println("ESERCIZIO 2");

        Map<Customer, Double> customerOrdersTotalPrice =
                orders.stream()
                        .collect(Collectors.groupingBy(Order::getCustomer,
                                Collectors.summingDouble(order -> order.getProducts()
                                        .stream()
                                        .mapToDouble(Product::getPrice)
                                        .sum()
                                )));

        customerOrdersTotalPrice.forEach((customer, totalPrice) -> {
            System.out.println(customer.getName() + " ha speso complessivamente: " + totalPrice + "€");
        });

        System.out.println();

        //ESERCIZIO 3
        System.out.println("ESERCIZIO 3");

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(productSupplier.get());
        }

        List<Product> expensiveProducts = products.stream()
                .sorted(Comparator
                        .comparingDouble(Product::getPrice)
                        .reversed())
                .limit(3)
                .toList();

        System.out.println("Lista Prodotti");
        products.forEach(System.out::println);
        System.out.println();
        System.out.println("I prodotti più costosi sono: ");
        expensiveProducts.forEach(product -> System.out.println(product.getName() + ", " + product.getPrice() + "€"));

        System.out.println();

        //ESERCIZIO 4
        System.out.println("ESERCIZIO 4");

        OptionalDouble averageOrdersPrice = orders.stream()
                .mapToDouble(order -> order.getProducts()
                        .stream()
                        .mapToDouble(Product::getPrice)
                        .sum())
                .average();

        averageOrdersPrice.stream().forEach(price -> System.out.println("La media dei 3 ordini è: " + price + "€"));

        System.out.println();

        //ESERCIZIO 5
        System.out.println("ESERCIZIO 5");

        products.stream().collect(Collectors
                        .groupingBy(Product::getCategory, Collectors.summingDouble(Product::getPrice)))
                .forEach((category, sum) -> System.out.println("Categoria: '" + category + "', Somma dei prodotti: " + sum + "€"));

        System.out.println();
    }
}















