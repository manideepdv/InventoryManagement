package com.example.ecom.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel{
    private String name;
    private String description;
    private double price;
}
