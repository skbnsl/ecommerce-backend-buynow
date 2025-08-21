package com.dailycodework.buynowdotcom.service.user;

import com.dailycodework.buynowdotcom.dtos.ImageDto;
import com.dailycodework.buynowdotcom.dtos.ProductDto;
import com.dailycodework.buynowdotcom.dtos.UserDto;
import com.dailycodework.buynowdotcom.model.Image;
import com.dailycodework.buynowdotcom.model.Product;
import com.dailycodework.buynowdotcom.model.User;
import com.dailycodework.buynowdotcom.repository.UserRepository;
import com.dailycodework.buynowdotcom.request.CreateUserRequest;
import com.dailycodework.buynowdotcom.request.UserUpdateRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    //constuctor injection rather than autowired
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(CreateUserRequest request) {
        User savedUser = Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                        user.setFirstName(req.getFirstName());
                        user.setLastName(req.getLastName());
                        user.setEmail(req.getEmail());
                        user.setPassword(passwordEncoder.encode(req.getPassword()));
                        user.setPhone(req.getPhone());
                        return userRepository.save(user);
                }).orElseThrow(()->new EntityExistsException("user "+ request.getEmail() +" already exist!"));
        return convertToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request, Long userId) {
        User user = userRepository.findById(userId).map(existingUser -> {
           existingUser.setFirstName(request.getFirstName());
           existingUser.setFirstName(request.getLastName());
           return userRepository.save(existingUser);
        }).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        return convertToDto(user);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(("user not found!!")));
        return convertToDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, ()->{
            throw new EntityNotFoundException("User Not Found!!");
        });
    }

    public UserDto convertToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return Optional.of(userRepository.findByEmail(email)).orElseThrow(()->
                new EntityNotFoundException("Login is required"));
    }
}
