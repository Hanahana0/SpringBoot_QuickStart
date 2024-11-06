package qlinx.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import qlinx.entity.User;
import qlinx.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
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
