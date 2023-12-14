package com.prorigo.service;

import com.prorigo.bean.User;
import com.prorigo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {


  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> updateUser(Long id, User updatedUser) {
    Optional<User> existingUser = userRepository.findById(id);
    if (existingUser.isPresent()) {
      User user = existingUser.get();
      // Update user fields
      user.setName(updatedUser.getName());
      // Update other fields as needed
      userRepository.save(user);
      return Optional.of(user);
    } else {
      return Optional.empty();
    }
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
