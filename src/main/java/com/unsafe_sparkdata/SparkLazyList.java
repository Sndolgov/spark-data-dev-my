package com.unsafe_sparkdata;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

/**
 * @author Evgeny Borisov
 */

@Getter
@Setter
public class SparkLazyList implements SparkLazyCollection {
    @Delegate
    private List content;
    private long ownerId;
    private Class<?> modelClass;
    private String foreignKeyName;
    private String pathToSource;


    @Override
    public boolean initialized() {
        return content != null && !content.isEmpty();
    }

    @Override
    public String toString() {
        return initialized() ? content.toString() : null;
    }
}
