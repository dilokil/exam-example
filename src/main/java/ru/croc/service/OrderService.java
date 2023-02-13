package ru.croc.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.croc.model.Order;
import ru.croc.model.Product;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис заказов.
 */
public class OrderService {
    private final static String DATA_PATH = ".\\data\\orders.csv";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final ProductService productService;
    private Map<String, Order> repository = null;

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    public void createOrderService(String dataPath) throws IOException, CsvException {
        if (repository == null) {
            repository = new HashMap<>();
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(
                    new FileReader(dataPath))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build();
            List<String[]> r = reader.readAll();
            r.forEach(el -> repository.put(el[0], new Order(
                    el[0],
                    el[1],
                    LocalDateTime.parse(el[2], FORMATTER),
                    parseProductsId(el[3])
            )));
        }
    }

    private List<Product> parseProductsId(String productsId) {
        return Arrays.stream(productsId.replaceAll("\\[|\\]", "").split(","))
                .map(String::trim)
                .map(productService::getProductById)
                .collect(Collectors.toList());
    }

    public Order getOrderById(String id) {
        return Optional.ofNullable(repository.get(id)).orElseThrow();
    }

    public List<Order> getAll() {
        return new ArrayList<>(repository.values());
    }

    public List<Order> getOrdersByDate(LocalDate date) {
        return getAll().stream()
                .filter(el -> date.equals(el.getCreatedDate().toLocalDate()))
                .collect(Collectors.toList());
    }
}
