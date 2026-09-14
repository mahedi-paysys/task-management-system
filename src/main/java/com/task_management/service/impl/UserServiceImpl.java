package com.task_management.service.impl;

import com.task_management.entity.User;
import com.task_management.exception.ResourceNotFoundException;
import com.task_management.repository.UserRepository;
import com.task_management.request.UserRequestDTO;
import com.task_management.response.UserResponseDTO;
import com.task_management.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
    }


    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {

        // Convert request DTO into Entity
        User user  = User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .build();


        // save entity into DB
        User savedUser = userRepository.save(user);

        // Convert Entity into Response DTO
        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));

        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build()
                ).toList();
    }


    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());

        User updatedUser = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .build();
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));

        userRepository.delete(user);
    }


}
