package com.unsafe_sparkdata.transformation;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

public interface TransformationHandler {
    Pair<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> allFieldNamesOfMethod);
}
