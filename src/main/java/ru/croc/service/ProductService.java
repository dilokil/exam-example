package ru.croc.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.croc.model.Product;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Сервис продуктов.
 */
public class ProductService {
    private Map<String, Product> repository = null;
    public void createProductService(final String dataPath) throws IOException, CsvException {
        if (repository == null) {
            repository = new HashMap<>();
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(
                    new FileReader(dataPath))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build();
            List<String[]> r = reader.readAll();
            r.forEach(el -> repository.put(el[0], new Product(
                    el[0],
                    el[1],
                    el[2],
                    Double.parseDouble(el[3]),
                    BigInteger.valueOf(Integer.parseInt(el[4]))
            )));
        }
    }
    public Product getProductById(String id) {
        return Optional.ofNullable(repository.get(id)).orElseThrow();
    }
    public List<Product> getAll() {
        return new ArrayList<>(repository.values());
    }
}
