package com.unsafe_sparkdata.finalizer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import com.unsafe_sparkdata.SparkLazyCollection;
import com.unsafe_sparkdata.annotation.ForeignKey;
import com.unsafe_sparkdata.annotation.Source;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class LazyCollectionInjectorPostFinalizer implements PostFinalizer {

    private final ConfigurableApplicationContext realContext;

    @SneakyThrows
    @Override
    public Object postFinalizer(Object retVal) {
        if (Collection.class.isAssignableFrom(retVal.getClass())){
            List models = (List) retVal;

            for (Object model : models) {
                Field idField = model.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                long ownerId = idField.getLong(model);

                Field[] declaredFields = model.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    if (List.class.isAssignableFrom(field.getType())){
                        SparkLazyCollection lazySparkList = realContext.getBean(SparkLazyCollection.class);
                        String foreignKeyName = field.getAnnotation(ForeignKey.class).value();
                        Class<?> embeddedModelClass = getEmbeddedModel(field);
                        String pathToSource = embeddedModelClass.getAnnotation(Source.class).value();

                        lazySparkList.setOwnerId(ownerId);
                        lazySparkList.setModelClass(embeddedModelClass);
                        lazySparkList.setForeignKeyName(foreignKeyName);
                        lazySparkList.setPathToSource(pathToSource);

                        field.setAccessible(true);
                        field.set(model, lazySparkList);
                    }
                }
            }
        }
        return retVal;
    }

    private Class<?> getEmbeddedModel(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Class<?> embeddedModel = (Class<?>) genericType.getActualTypeArguments()[0];
        return embeddedModel;
    }
}
