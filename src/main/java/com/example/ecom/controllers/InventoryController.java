package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.exceptions.UnAuthorizedAccessException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Inventory;
import com.example.ecom.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InventoryController {


    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public CreateOrUpdateResponseDto createOrUpdateInventory(CreateOrUpdateRequestDto requestDto){
        CreateOrUpdateResponseDto responseDto = new CreateOrUpdateResponseDto();
        Inventory inventory = null;
        try {
            inventory = inventoryService.createOrUpdateInventory(requestDto.getUserId(), requestDto.getProductId(), requestDto.getQuantity());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        } catch (UnAuthorizedAccessException e) {
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        responseDto.setInventory(inventory);
        return responseDto;
    }

    public DeleteInventoryResponseDto deleteInventory(DeleteInventoryRequestDto requestDto){
        DeleteInventoryResponseDto responseDto = new DeleteInventoryResponseDto();
        try {
            inventoryService.deleteInventory(requestDto.getUserId(), requestDto.getProductId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        } catch (UnAuthorizedAccessException e) {
            System.out.println(e.getMessage());
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }


}
