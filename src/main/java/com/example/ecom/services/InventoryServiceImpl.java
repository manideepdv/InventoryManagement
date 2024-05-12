package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.exceptions.UnAuthorizedAccessException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                UserRepository userRepository,
                                ProductRepository productRepository){

        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Inventory createOrUpdateInventory(int userId, int productId, int quantity)
            throws ProductNotFoundException, UserNotFoundException, UnAuthorizedAccessException{

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }

        if(!userOptional.get().getUserType().equals(UserType.ADMIN)){
            throw new UnAuthorizedAccessException("Can not access!!");
        }

        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found!");
        }


        Inventory inventory;

        Optional<Inventory> inventoryOptional = inventoryRepository.findByProduct_Id(productId);
        if(inventoryOptional.isEmpty()){
            inventory = new Inventory();
            inventory.setProduct(productOptional.get());
            inventory.setQuantity(quantity);
            return inventoryRepository.save(inventory);
        }


        inventory = inventoryOptional.get();
        inventory.setQuantity(inventory.getQuantity()+quantity);//adding quantity in previous inventory quantity
        inventoryRepository.save(inventory);
        return inventory;

    }


    @Override
    public void deleteInventory(int userId, int productId) throws UserNotFoundException, UnAuthorizedAccessException{

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found!");
        }

        if(!userOptional.get().getUserType().equals(UserType.ADMIN)){
            throw new UnAuthorizedAccessException("Can not access!!");
        }

        Optional<Inventory> inventoryOptional = inventoryRepository.findByProduct_Id( productId);
        if(inventoryOptional.isEmpty()){
            return;
        }

        inventoryRepository.delete(inventoryOptional.get());
    }
}
