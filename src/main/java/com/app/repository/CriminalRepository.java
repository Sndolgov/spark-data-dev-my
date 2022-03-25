package com.app.repository;

import java.util.List;

import com.app.model.Criminal;
import com.unsafe_sparkdata.SparkRepository;

/**
 * @author Evgeny Borisov
 */
public interface CriminalRepository extends SparkRepository<Criminal> {
    List<Criminal> findByNumberGreaterThanOrderByNumber(int min);
    List<Criminal> findByNumberBetween(int min, int max);
    long findByNameContainsCount(String s);
    List<Criminal> findByNameContains(String s);
}
