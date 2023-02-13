package ru.croc.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ru.croc.model.Product;

import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final static String DATA_PATH = ".\\data\\products.csv";

    private static List<Product> repository = null;


    public static void createProductService() {
        if (repository == null) {
            repository = new ArrayList<>();
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            try(CSVReader reader = new CSVReaderBuilder(
                    new FileReader(DATA_PATH))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build()){
                List<String[]> r = reader.readAll();
                r.forEach(el -> repository.add( new Product(
                        el[0],
                        el[1],
                        el[2],
                        Double.parseDouble(el[3]),
                        BigInteger.valueOf(Integer.parseInt(el[4]))
                )));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Product getProductById(String id) {
        return repository.stream()
                .filter(el -> el.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static List<Product> getAll() {
        return repository;
    }
}
