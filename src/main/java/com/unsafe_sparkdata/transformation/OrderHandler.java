package com.unsafe_sparkdata.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import com.unsafe_sparkdata.util.WordsMatcher;

@Component("orderBy")
public class OrderHandler implements TransformationHandler {
    @Override
    public Pair<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> allFieldNamesOfMethod) {
        List<String> sortFields = new ArrayList<>();
        String firstSortField = WordsMatcher.findAndRemoveMatchingPieces(allFieldNamesOfMethod, methodWords);
        sortFields.add(firstSortField);
        while (!methodWords.isEmpty() && methodWords.get(0).equalsIgnoreCase("and")){
            methodWords.remove(0);
            sortFields.add(WordsMatcher.findAndRemoveMatchingPieces(allFieldNamesOfMethod, methodWords));
        }
        return Pair.of(new SortTransformation(), sortFields);
    }
}
