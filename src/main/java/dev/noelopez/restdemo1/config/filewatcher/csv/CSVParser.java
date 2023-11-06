package dev.noelopez.restdemo1.config.filewatcher.csv;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CSVParser {
    public static final String DELIMITER = ",";
    private Map<String, Integer> headerMap = new HashMap<>();
    private List<CSVRecord> records = new ArrayList<>();
    public static CSVParser parse(Path path) {
        Objects.requireNonNull(path, "File cannot be null");
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new CSVParser(lines);
    }

    public static CSVParser parse(File file) {
        return CSVParser.parse(file.toPath());
    }

    private CSVParser(List<String> lines) {
        if (lines.size() == 0)
            return;

        headerMap = createHeader(lines.get(0));
        records = createRecords(lines);
    }

    private Map<String, Integer> createHeader(String firstLine) {
        var headers = firstLine.split(DELIMITER);

        return IntStream.range(0, headers.length)
                .boxed()
                .collect(Collectors.toMap(i -> headers[i].trim(), Integer::intValue));
    }
    private List<CSVRecord> createRecords(List<String> lines) {
        return IntStream.range(1, lines.size())
                .mapToObj(index -> new CSVRecord(index,this, lines.get(index).split(DELIMITER)))
                .toList();
    }

    public Map<String, Integer> getHeaderMap() {
        return headerMap;
    }

    public List<CSVRecord> getRecords() {
        return records;
    }
}
