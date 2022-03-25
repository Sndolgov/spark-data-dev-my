package com.app.model;

import com.unsafe_sparkdata.annotation.Source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Source("data/orders.csv")
public class Order {
    private String name;
    private String desc;
    private int price;
    private long criminalId;
}
