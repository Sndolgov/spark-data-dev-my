package com.unsafe_sparkdata.extractor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataExtractorResolver {

    @Autowired
    private Map<String, DataExtractor> dataExtractorMap;

    public DataExtractor resolver(String pathToData){
        String fileExtension = pathToData.split("\\.")[1];
        return dataExtractorMap.get(fileExtension);
    }
}
