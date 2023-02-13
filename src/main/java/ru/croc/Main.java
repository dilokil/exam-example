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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        OrderService.createOrderService();
        ProductService.createProductService();

        LocalDate start = LocalDate.of(2022,6,1);
        LocalDate end = LocalDate.of(2022,7,31);

        List<ProductsCountAvgWeight> result = new ArrayList<>();
        for (var date = start; !date.isAfter(end); date = date.plusDays(1)) {
            List<Order> ordersInDate = OrderService.getOrdersByDate(date);

                long productsCount = ordersInDate.stream()
                        .map(Order::getProducts)
                        .mapToLong(Collection::size)
                        .sum();
                double productsAvgWeight = ordersInDate.stream()
                        .map(Order::getProducts)
                        .flatMap(Collection::stream)
                        .map(ProductService::getProductById)
                        .mapToDouble(Product::getWeight)
                        .average().orElse(0.0);
                result.add(new ProductsCountAvgWeight(productsCount, productsAvgWeight, date));

        }

        File csvFile = new File(".\\data\\result.csv");
        try (PrintWriter pw = new PrintWriter(csvFile)) {
            result.stream()
                    .map(ProductsCountAvgWeight::toString)
                    .forEach(pw::println);
        }
    }
}