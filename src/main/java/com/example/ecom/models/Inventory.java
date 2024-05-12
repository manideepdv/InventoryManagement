package com.example.ecom.models;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Inventory extends BaseModel{
    @OneToOne
    private Product product;
    private int quantity;
}
