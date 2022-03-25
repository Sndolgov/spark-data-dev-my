package com.app.model;

import com.unsafe_sparkdata.annotation.Source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Evgeny Borisov
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/profiles.json")
public class Profile {
    private String name;
    private long number;
}
