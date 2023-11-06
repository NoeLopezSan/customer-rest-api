package dev.noelopez.restdemo1.config.filewatcher.processor;

import org.springframework.stereotype.Component;

import java.nio.file.Path;

public interface FileProcessor {
    public void process(Path file);
}
