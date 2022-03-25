package com.unsafe_sparkdata.handler;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.unsafe_sparkdata.finalizer.Finalizer;
import com.unsafe_sparkdata.SparkRepository;
import com.unsafe_sparkdata.finalizer.LazyCollectionInjectorPostFinalizer;
import com.unsafe_sparkdata.transformation.SparkTransformation;
import com.unsafe_sparkdata.transformation.TransformationHandler;
import com.unsafe_sparkdata.annotation.Source;
import com.unsafe_sparkdata.annotation.Transient;
import com.unsafe_sparkdata.extractor.DataExtractor;
import com.unsafe_sparkdata.extractor.DataExtractorResolver;
import com.unsafe_sparkdata.util.WordsMatcher;

import lombok.Setter;

@Component
public class SparkInvocationHandlerFactory {

    @Autowired
    private DataExtractorResolver resolver;
    @Autowired
    private Map<String, TransformationHandler> transformationHandlerMap;
    @Autowired
    private Map<String, Finalizer> finalizerMap;

    @Setter
    private ConfigurableApplicationContext realContext;

    public SparkInvocationHandler create(Class<? extends SparkRepository> sparkRepoInterface){
        Class<?> modelClass = getModelClass(sparkRepoInterface);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        Set<String> fieldNames = getModelFieldNames(modelClass);
        DataExtractor dataExtractor = this.resolver.resolver(pathToData);
        Map<Method, List<Pair<SparkTransformation, List<String>>>> transformationChain = new HashMap<>();
        Map<Method, Finalizer> methodFinalizerMap = new HashMap<>();
        Method[] methods = sparkRepoInterface.getMethods();
        for (Method method : methods) {
            TransformationHandler currentHandler = null;
            String methodName = method.getName();
            List<String> methodWords = Arrays.stream(methodName.split("(?=\\p{Upper})")).collect(Collectors.toList());
            List<Pair<SparkTransformation, List<String>>> transformations = new ArrayList<>();
            while (methodWords.size() > 1){
                String handlerName = WordsMatcher.findAndRemoveMatchingPieces(transformationHandlerMap.keySet(), methodWords);
                if (!handlerName.isEmpty()){
                    currentHandler = transformationHandlerMap.get(handlerName);
                }
                transformations.add(currentHandler.getTransformation(methodWords, fieldNames));
            }
            transformationChain.put(method, transformations);
            String finalizerName = "collect";
            if (methodWords.size() == 1){
                finalizerName = Introspector.decapitalize(methodWords.get(0));
            }
            methodFinalizerMap.put(method, finalizerMap.get(finalizerName));
        }

        return SparkInvocationHandler.builder()
                .modelClass(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor)
                .transformationChain(transformationChain)
                .finalizerMap(methodFinalizerMap)
                .postFinalizer(new LazyCollectionInjectorPostFinalizer(realContext))
                .context(realContext)
                .build();
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> sparkDataInterface) {
        return (Class<?>) ((ParameterizedType) sparkDataInterface.getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    private Set<String> getModelFieldNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !(Collection.class.isAssignableFrom(field.getType())))
                .map(Field::getName)
                .collect(Collectors.toSet());
    }
}
