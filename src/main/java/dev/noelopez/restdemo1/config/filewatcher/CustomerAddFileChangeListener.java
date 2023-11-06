package dev.noelopez.restdemo1.config.filewatcher;

import dev.noelopez.restdemo1.config.filewatcher.csv.CustomerCSVFileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;

import java.util.Set;

public class CustomerAddFileChangeListener implements FileChangeListener {
    private static Logger logger = LoggerFactory.getLogger(CustomerAddFileChangeListener.class);
    private CustomerCSVFileProcessor fileProcessor;
    public CustomerAddFileChangeListener(CustomerCSVFileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        for(ChangedFiles files : changeSet)
            for(ChangedFile file: files.getFiles())
                if (file.getType().equals(ChangedFile.Type.ADD))
                    fileProcessor.process(file.getFile().toPath());
    }
}
