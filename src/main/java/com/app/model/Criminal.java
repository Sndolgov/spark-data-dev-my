package com.app.model;

import java.util.List;

import com.unsafe_sparkdata.annotation.ForeignKey;
import com.unsafe_sparkdata.annotation.Source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Source("data/criminals.csv")
public class Criminal {
    private long id;
    private String name;
    private int number;

    @ForeignKey("criminalId")
    private List<Order> orders;
}
