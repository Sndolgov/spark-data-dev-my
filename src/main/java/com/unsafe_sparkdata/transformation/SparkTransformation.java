package com.unsafe_sparkdata.transformation;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import com.unsafe_sparkdata.util.OrderedBag;

public interface SparkTransformation {
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, OrderedBag<Object> args);
}
