package com.unsafe_sparkdata.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderedBag<T> {
    private final List<T> list;

    public OrderedBag(T[] arr){
        list = Arrays.stream(arr).collect(Collectors.toList());
    }

    public T takeAndRemove(){
        return list.remove(0);
    }
    public int size(){
        return list.size();
    }
}
