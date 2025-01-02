package com.dcw.cartfrenzy.util;

import org.modelmapper.ModelMapper;

import com.dcw.cartfrenzy.dto.UserDto;
import com.dcw.cartfrenzy.model.User;

public class UserConverter {

	private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

	public static User convertToEntity(UserDto userDto) {
		 return modelMapper.map(userDto, User.class);		
	}

}
