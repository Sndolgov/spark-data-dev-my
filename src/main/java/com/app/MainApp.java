package com.app;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.app.model.Criminal;
import com.app.model.Order;
import com.app.repository.CriminalRepository;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApp.class);
        CriminalRepository criminalRepository = context.getBean(CriminalRepository.class);
        List<Criminal> criminals = criminalRepository.findByNameContains("ov");
        System.out.println(criminals);
        List<Order> orders = criminals.get(0).getOrders();
        System.out.println(orders.get(0));
        System.out.println(orders);
        criminals = criminalRepository.findByNumberBetween(13, 15);
        System.out.println(criminals);
        orders = criminals.get(0).getOrders();
        System.out.println(orders.get(0));
        System.out.println(orders);
    }
}
