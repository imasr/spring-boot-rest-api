package com.springrest.springrest.services;

import java.util.List;

import com.springrest.springrest.shared.dto.AddressDto;

public interface AddressService {
    List<AddressDto> getAddressByUserId(String userId);

    AddressDto getAddressById(String addressId);
}
