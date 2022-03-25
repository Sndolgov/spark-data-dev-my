package com.app.repository;

import java.util.List;

import com.unsafe_sparkdata.SparkRepository;
import com.app.model.Profile;

/**
 * @author Evgeny Borisov
 */
public interface ProfileRepository extends SparkRepository<Profile> {
    List<Profile> findByAgeBetween(int min, int max);
    List<Profile> findByAgeGreaterThan(int age);
}
