package com.unsafe_sparkdata.transformation;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.unsafe_sparkdata.util.OrderedBag;

public class SortTransformation implements SparkTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, OrderedBag<Object> args) {
        return dataset.orderBy(fieldNames.get(0), fieldNames.stream().skip(1).toArray(String[]::new));
    }
}
