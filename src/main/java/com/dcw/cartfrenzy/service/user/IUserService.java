package com.dcw.cartfrenzy.service.user;

import com.dcw.cartfrenzy.dto.UserDto;
import com.dcw.cartfrenzy.model.User;
import com.dcw.cartfrenzy.request.CreateUserRequest;
import com.dcw.cartfrenzy.request.UserUpdateRequest;

public interface IUserService {

	UserDto getUserById(Long userId);
	UserDto createUser(CreateUserRequest request);
	UserDto updateUser(UserUpdateRequest request, Long userId);
	void deleteUser(Long userId);
	UserDto convertUserToDto(User user);
	Long getConsumerId();
	default User findUserProfileByJwt(String jwt) {
		return null;
	};
}
