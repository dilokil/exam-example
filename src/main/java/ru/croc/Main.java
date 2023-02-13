package ru.croc;

import ru.croc.metric.ProductsCountAvgWeight;
import ru.croc.model.Order;
import ru.croc.model.Product;
import ru.croc.service.OrderService;
import ru.croc.service.ProductService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    private static final String DEFAULT_PRODUCTS_DATA_PATH = ".\\data\\products.csv";
    private static final String DEFAULT_ORDERS_DATA_PATH = ".\\data\\orders.csv";
    private static final String DEFAULT_RESULT_DATA_PATH = ".\\data\\result.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static ProductService productService;
    private static OrderService orderService;
    private static LocalDate start;
    private static LocalDate end;
    private static String pathProducts;
    private static String pathOrders;
    private static String pathResult;

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 2) {
            System.out.println("Enter the start and end date of the analysis in the arguments. Format: yyyy-MM-dd");
            return;
        }
        try {
            start = LocalDate.parse(args[0], FORMATTER);
            end = LocalDate.parse(args[1], FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Wrong format! Correct format: yyyy-MM-dd");
            return;
        }
        pathProducts = args.length >= 3 ? args[2] : DEFAULT_PRODUCTS_DATA_PATH;
        pathOrders = args.length >= 4 ? args[3] : DEFAULT_ORDERS_DATA_PATH;
        pathResult = args.length == 5 ? args[4] : DEFAULT_RESULT_DATA_PATH;

        try {
            productService = new ProductService();
            productService.createProductService(pathProducts);

            orderService = new OrderService(productService);
            orderService.createOrderService(pathOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<ProductsCountAvgWeight> result = new ArrayList<>();
        for (var date = start; !date.isAfter(end); date = date.plusDays(1)) {
            List<Order> ordersInDate = orderService.getOrdersByDate(date);

            long productsCount = ordersInDate.stream()
                    .map(Order::getProducts)
                    .mapToLong(Collection::size)
                    .sum();
            double productsAvgWeight = ordersInDate.stream()
                    .map(Order::getProducts)
                    .flatMap(Collection::stream)
                    .mapToDouble(Product::getWeight)
                    .average().orElse(0.0);
            result.add(new ProductsCountAvgWeight(productsCount, productsAvgWeight, date));
        }

        File csvFile = new File(pathResult);
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            result.stream()
                    .map(ProductsCountAvgWeight::toString)
                    .forEach(pw::println);
        }
    }
}