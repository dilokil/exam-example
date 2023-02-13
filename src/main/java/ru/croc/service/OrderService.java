package ru.croc.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ru.croc.model.Order;

import java.io.FileReader;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final static String DATA_PATH = ".\\data\\orders.csv";

    private static List<Order> repository = null;

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private OrderService() {

    }

    public static void createOrderService() {
        if (repository == null) {
            repository = new ArrayList<>();
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            try(CSVReader reader = new CSVReaderBuilder(
                    new FileReader(DATA_PATH))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build()){
                List<String[]> r = reader.readAll();
                r.forEach(el -> repository.add( new Order(
                        el[0],
                        el[1],
                        LocalDateTime.parse(el[2], FORMATTER),
                        parseProductsId(el[3])
                )));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static List<String> parseProductsId(String productsId){
        return Arrays.stream(productsId.replaceAll("\\[|\\]", "").split(",")).map(String::trim)
                .collect(Collectors.toList());
    }

    public static Order getOrderById(String id) {
        return repository.stream()
                .filter(el -> el.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static List<Order> getAll() {
        return repository;
    }

    public static List<Order> getOrdersByDate(LocalDate date) {
        return repository.stream()
                .filter(el -> date.equals(el.getCreatedDate().toLocalDate()))
                .collect(Collectors.toList());
    }
}
