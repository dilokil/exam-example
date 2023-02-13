package ru.croc.metric;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductsCountAvgWeight {
    private long productsCount;
    private double avgWeight;
    private LocalDate saleDay;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public ProductsCountAvgWeight(long productsCount, double avgWeight, LocalDate saleDay) {
        this.productsCount = productsCount;
        this.avgWeight = avgWeight;
        this.saleDay = saleDay;
    }

    public String toString() {
        return saleDay.format(FORMATTER).concat(";")
                .concat(String.valueOf(productsCount)).concat(";")
                .concat(String.format("%.2f", avgWeight));
    }
}
