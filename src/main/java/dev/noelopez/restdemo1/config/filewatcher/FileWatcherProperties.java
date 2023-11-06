package dev.noelopez.restdemo1.config.filewatcher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.file.watch")
public record FileWatcherProperties(
        @NotBlank String directory,
        boolean daemon,
        @Positive Long pollInterval,
        @Positive Long quietPeriod
) {}
