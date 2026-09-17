
package com.examly.springapp.service;

import com.examly.springapp.entity.User;
import com.examly.springapp.enums.UserRole;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    // Get users by role
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    // Get active users
    public List<User> getActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    // Update user role
    public User updateUserRole(Long id, String role) {
        User user = getUserById(id);
        user.setRole(UserRole.valueOf(role.toUpperCase()));
        return userRepository.save(user);
    }

    // Deactivate user
    public User deactivateUser(Long id) {
        User user = getUserById(id);
        user.setIsActive(false);
        return userRepository.save(user);
    }

    // Activate user
    public User activateUser(Long id) {
        User user = getUserById(id);
        user.setIsActive(true);
        return userRepository.save(user);
    }

    // Delete user
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // Search users by name
    public List<User> searchByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
}

