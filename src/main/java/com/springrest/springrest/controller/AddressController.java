package com.springrest.springrest.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.springrest.services.AddressService;
import com.springrest.springrest.shared.dto.AddressDto;
import com.springrest.springrest.ui.model.response.AddressRest;
import com.springrest.springrest.ui.model.response.Response;
import com.springrest.springrest.ui.model.response.SuccessMessages;
import com.springrest.springrest.ui.model.response.UserRest;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/users/{userId}/address")
    public ResponseEntity<Response> getAddressByUserId(@PathVariable("userId") String userId) {
        try {

            List<AddressDto> addressDtoList = addressService.getAddressByUserId(userId);

            ModelMapper modelMapper = new ModelMapper();

            List<AddressRest> addressList = new ArrayList<>();

            for (AddressDto addressDto : addressDtoList) {
                AddressRest address = modelMapper.map(addressDto, AddressRest.class);
                addressList.add(address);
            }
            Response response = new Response(addressList, HttpStatus.OK.value(),
                    SuccessMessages.RECORD_FETCHED.getSuccessMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            Response response = new Response(null, HttpStatus.NOT_FOUND.value(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<Response> getAddressById(@PathVariable("addressId") String addressId) {
        try {

            AddressDto addressDto = addressService.getAddressById(addressId);

            ModelMapper modelMapper = new ModelMapper();
            AddressRest address = modelMapper.map(addressDto, AddressRest.class);

            Response response = new Response(address, HttpStatus.OK.value(),
                    SuccessMessages.RECORD_FETCHED.getSuccessMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            Response response = new Response(null, HttpStatus.NOT_FOUND.value(),
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
