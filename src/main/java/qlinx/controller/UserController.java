package qlinx.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qlinx.dto.ApiResponse;
import qlinx.entity.User;
import qlinx.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllUsers(@RequestBody(required = false) Map<String, String> conditions) {
        List<User> users = userService.getAllUsers(conditions);
        ApiResponse response = new ApiResponse(users, "User list fetched successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getById")
    public ResponseEntity<ApiResponse> getUserById(@RequestBody Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            ApiResponse response = new ApiResponse(user.get(), "User fetched successfully", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse response = new ApiResponse(null, "User not found", 404);
            return ResponseEntity.status(404).body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        ApiResponse response = new ApiResponse(createdUser, "User created successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            ApiResponse response = new ApiResponse(null, "User ID is required", 400);
            return ResponseEntity.badRequest().body(response);
        }
        User updatedUser = userService.updateUser(user.getId(), user);
        ApiResponse response = new ApiResponse(updatedUser, "User updated successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestBody Long id) {
        userService.deleteUser(id);
        ApiResponse response = new ApiResponse(null, "User deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
