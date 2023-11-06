package dev.noelopez.restdemo1.config.filewatcher.csv;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CSVRecord(int lineNumber, @NotNull CSVParser parser, @NotNull String[] values) {
    public String get(@Positive int position) {
        if (position > values.length)
            throw new RuntimeException(String.format("Position %d cannot larger than %d.",position, values.length));

        return values[position];
    }

    public String get(String name) {
        Integer index = parser.getHeaderMap().get(name);

        if (index == null)
            throw new RuntimeException(String.format("No value for %s", name));

        return values[index];
    }

    public int size() {
        return values.length;
    }
}
