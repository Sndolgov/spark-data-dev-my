package com.unsafe_sparkdata.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

import com.unsafe_sparkdata.finalizer.Finalizer;
import com.unsafe_sparkdata.finalizer.PostFinalizer;
import com.unsafe_sparkdata.transformation.SparkTransformation;
import com.unsafe_sparkdata.extractor.DataExtractor;
import com.unsafe_sparkdata.util.OrderedBag;

import lombok.Builder;

@Builder
public class SparkInvocationHandler implements InvocationHandler {
    private Class<?> modelClass;
    private String pathToData;
    private DataExtractor dataExtractor;
    private Map<Method, List<Pair<SparkTransformation, List<String>>>> transformationChain;
    private Map<Method, Finalizer> finalizerMap;
    private ConfigurableApplicationContext context;
    private PostFinalizer postFinalizer;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Dataset<Row> dataset = dataExtractor.load(pathToData, context);
        List<Pair<SparkTransformation, List<String>>> sparkTransformationsWithArgs = transformationChain.get(method);
        for (Pair<SparkTransformation, List<String>> transformationWithArgs : sparkTransformationsWithArgs) {
            dataset = transformationWithArgs.getLeft().transform(dataset, transformationWithArgs.getRight(), new OrderedBag<>(args));
        }
        Object retVal = finalizerMap.get(method).doAction(dataset, modelClass);
        return postFinalizer.postFinalizer(retVal);
    }
}
