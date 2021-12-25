package com.tuana9a.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${lucene.indexes.dir}")
    public String LUCENE_INDEXES_DIR;
}
