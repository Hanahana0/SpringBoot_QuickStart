package qlinx.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qlinx.entity.User;
import qlinx.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(Map<String, Object> conditions) {
        if (conditions == null || conditions.isEmpty()) {
            return userRepository.findAll();
        }

        String name = (String)conditions.get("name");
        String startDateStr = (String)conditions.get("startDate");
        String endDateStr = (String)conditions.get("endDate");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (name != null && startDateStr != null && endDateStr != null) {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
            return userRepository.findByUsernameContainingAndCreatedAtBetween(name, startDate, endDate);
        } else if (name != null) {
            return userRepository.findByUsernameContaining(name);
        } else {
            return userRepository.findAll();
        }
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setRole(user.getRole());
            updatedUser.setStatus(user.getStatus());
            return userRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
