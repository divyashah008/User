package com.prorigo.service;

import com.prorigo.bean.User;
import com.prorigo.exception.EmptyInputException;
import com.prorigo.exception.UserNotFoundException;
import com.prorigo.model.request.UserRequest;
import com.prorigo.model.response.UserResponse;
import com.prorigo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<UserResponse> getAllUsers(int page, int size,String sortBy) {
    Pageable pageable = PageRequest.of(page, size,Sort.by(sortBy));
    Page<User> userPage = userRepository.findAll(pageable);
    return userPage.map(user -> new UserResponse(user.getId(), user.getName()));
  }

  public UserResponse getUserById(Long id) {
    User user = userRepository.findById(id)
                              .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    return new UserResponse(user.getId(), user.getName());
  }

  public UserResponse createUser(UserRequest userRequest) {
    if (userRequest.getName() == null || userRequest.getName().trim().isEmpty()) {
      throw new EmptyInputException("User name cannot be empty");
    }
    User user = new User();
    user.setName(userRequest.getName());
    // Set other fields as needed
    User createdUser = userRepository.save(user);
    return new UserResponse(createdUser.getId(), createdUser.getName());
  }

  public Optional<UserResponse> updateUser(Long id, UserRequest userRequest) {
    Optional<User> existingUser = userRepository.findById(id);
    return existingUser.map(user -> {
      if (userRequest.getName() != null && !userRequest.getName().trim().isEmpty()) {
        // Update user fields
        user.setName(userRequest.getName());
        // Update other fields as needed
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getName());
      } else {
        throw new EmptyInputException("User name cannot be empty");
      }
    });
  }

  public void deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new UserNotFoundException("User not found with ID: " + id);
    }
  }
}
