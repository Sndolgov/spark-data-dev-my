package com.unsafe_sparkdata.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import com.unsafe_sparkdata.SparkLazyList;
import com.unsafe_sparkdata.extractor.DataExtractor;
import com.unsafe_sparkdata.extractor.DataExtractorResolver;

public class FirstLevelCacheService {

    @Autowired
    private DataExtractorResolver extractorResolver;

    private final Map<Class<?>, Dataset<Row>> model2Dataset = new HashMap<>();

    public List<?> readDataFor(SparkLazyList lazyList, ConfigurableApplicationContext context) {
        Class<?> modelClass = lazyList.getModelClass();
        if (!model2Dataset.containsKey(modelClass)) {
            DataExtractor resolver = extractorResolver.resolver(lazyList.getPathToSource());
            Dataset<Row> dataset = resolver.load(lazyList.getPathToSource(), context);
            dataset.persist();
            model2Dataset.put(modelClass, dataset);
        }
        Encoder<?> encoder = Encoders.bean(modelClass);
        return model2Dataset.get(modelClass).filter(functions.col(lazyList.getForeignKeyName()).equalTo(lazyList.getOwnerId()))
                .as(encoder).collectAsList();

    }
}
