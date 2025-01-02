package com.dcw.cartfrenzy.service.user;

import com.dcw.cartfrenzy.dto.UserDto;
import com.dcw.cartfrenzy.exception.AlreadyExistsException;
import com.dcw.cartfrenzy.exception.ResourceNotFoundException;
import com.dcw.cartfrenzy.model.Role;
import com.dcw.cartfrenzy.model.User;
import com.dcw.cartfrenzy.repository.RoleRepository;
import com.dcw.cartfrenzy.repository.UserRepository;
import com.dcw.cartfrenzy.request.CreateUserRequest;
import com.dcw.cartfrenzy.request.UserUpdateRequest;
import com.dcw.cartfrenzy.util.OwnershipUtil;
import com.dcw.cartfrenzy.util.UserConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }
//	private final JwtUtils jwtUtils;
//
//	@Override
//	public User findUserProfileByJwt(String jwt) {
//
//		String email= jwtUtils.getUsernameFromToken(jwt);
//
//		return userRepository.findByEmail(email)
//				.orElseThrow(()->new ResourceNotFoundException("user not exist with email "+email));
//
//	}

    @Override
    public UserDto getUserById(Long userId) {

        return userRepository.findById(userId).map(this::convertUserToDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(encoder.encode(req.getPassword()));

                    List<Role> availableRoles = roleRepository.findAllByNameIn(new ArrayList<>(req.getRoles()));
                    user.setRoles(availableRoles.size() == req.getRoles().size() ? new HashSet<>(availableRoles) : Set.of(new Role("ROLE_USER")));

                    return convertUserToDto(userRepository.save(user));
                }).orElseThrow(() -> new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!"));

    }

    @Override
    public UserDto updateUser(UserUpdateRequest request, Long userId) {

        return userRepository.findById(userId)
                .filter(user -> user.getId().equals(getConsumerId()))
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());

                    return convertUserToDto(userRepository.save(existingUser));
                }).orElseThrow(() -> new ResourceNotFoundException("user not found!"));

    }

    @Override
    public void deleteUser(Long userId) {

        userRepository.findById(userId)
                .filter(user -> user.getId().equals(getConsumerId()))
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new ResourceNotFoundException("user not found");
                });

    }

    @Override
    public UserDto convertUserToDto(User user) {

        return UserConverter.convertToDto(user);
    }

    @Override
    public Long getConsumerId() {

        return OwnershipUtil.getConsumerId();
    }

}
