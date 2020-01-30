package com.rbkmoney.cm.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "claim-management")
public class CommitterConfig {

    private final List<Committer> committers;

    @Data
    public static class Committer {
        private String id;
        private URI uri;
        private int timeout;
    }

}
