package com.unsafe_sparkdata.transformation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unsafe_sparkdata.util.WordsMatcher;

@Component("findBy")
public class FilterHandler implements TransformationHandler {
    @Autowired
    private Map<String, FilterTransformation> filterTransformationMap;

    @Override
    public Pair<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> allFieldNamesOfMethod) {
        List<String> relevantFieldNames = List.of(WordsMatcher.findAndRemoveMatchingPieces(allFieldNamesOfMethod, methodWords));
        String filterName = WordsMatcher.findAndRemoveMatchingPieces(filterTransformationMap.keySet(), methodWords);
        return Pair.of(filterTransformationMap.get(filterName), relevantFieldNames);
    }
}
