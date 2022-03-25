package com.unsafe_sparkdata.transformation;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

import com.unsafe_sparkdata.util.OrderedBag;

@Component("greaterThan")
public class GreaterThanFilterTransformation implements FilterTransformation {
    @Override
    public Dataset<Row> transform(Dataset<Row> dataset, List<String> fieldNames, OrderedBag<Object> args) {
        return dataset.filter(functions.col(fieldNames.get(0)).$greater$eq(args.takeAndRemove()));
    }
}
