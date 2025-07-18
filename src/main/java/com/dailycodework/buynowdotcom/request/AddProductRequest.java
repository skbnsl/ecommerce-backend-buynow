package com.dailycodework.buynowdotcom.request;

import com.dailycodework.buynowdotcom.model.Category;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
