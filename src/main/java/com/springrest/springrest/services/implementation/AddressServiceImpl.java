package com.springrest.springrest.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrest.springrest.io.entity.AddressEntity;
import com.springrest.springrest.io.entity.UserEntity;
import com.springrest.springrest.repositories.AddressRepository;
import com.springrest.springrest.repositories.UserRepository;
import com.springrest.springrest.services.AddressService;
import com.springrest.springrest.shared.dto.AddressDto;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<AddressDto> getAddressByUserId(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);

        List<AddressEntity> addressEntityList = addressRepository.findAllByUserDetails(userEntity);

        ModelMapper modelMapper = new ModelMapper();
        List<AddressDto> list = new ArrayList<>();

        for (AddressEntity addressEntity : addressEntityList) {
            AddressDto returnValue = modelMapper.map(addressEntity, AddressDto.class);
            list.add(returnValue);
        }

        return list;
    }

    @Override
    public AddressDto getAddressById(String addressId) {

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        ModelMapper modelMapper = new ModelMapper();
        AddressDto returnValue = modelMapper.map(addressEntity, AddressDto.class);

        return returnValue;
    }
}
