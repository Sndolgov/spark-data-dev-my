package com.unsafe_sparkdata;

import java.util.List;

/**
 * @author Evgeny Borisov
 */
public interface SparkLazyCollection extends List{
    long getOwnerId();
    String getPathToSource();
    String getForeignKeyName();
    Class<?> getModelClass();
    void setModelClass(Class<?> modelClass);
    void setForeignKeyName(String foreignKeyName);
    void setPathToSource(String pathToSource);
    void setOwnerId(long modelId);
    boolean initialized();
}