package qlinx.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qlinx.dto.ApiRequest;
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
    public ResponseEntity<ApiResponse> getAllUsers(@RequestBody ApiRequest request) {
        Map<String, Object> conditions = request.getP_PARAM();
        List<User> users = userService.getAllUsers(conditions);
        ApiResponse response = new ApiResponse(users, "User list fetched successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getById")
    public ResponseEntity<ApiResponse> getUserById(@RequestBody ApiRequest request) {
        Long id = (Long) request.getP_PARAM().get("id");
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
    public ResponseEntity<ApiResponse> createUser(@RequestBody ApiRequest request) {
        User user = new User();
        user.setUsername((String) request.getP_PARAM().get("username"));
        user.setPassword((String) request.getP_PARAM().get("password"));
        user.setEmail((String) request.getP_PARAM().get("email"));
        user.setRole((String) request.getP_PARAM().get("role"));
        user.setStatus((String) request.getP_PARAM().get("status"));

        User createdUser = userService.createUser(user);
        ApiResponse response = new ApiResponse(createdUser, "User created successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody ApiRequest request) {
        Long id = (Long) request.getP_PARAM().get("id");
        if (id == null) {
            ApiResponse response = new ApiResponse(null, "User ID is required", 400);
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setId(id);
        user.setUsername((String) request.getP_PARAM().get("username"));
        user.setPassword((String) request.getP_PARAM().get("password"));
        user.setEmail((String) request.getP_PARAM().get("email"));
        user.setRole((String) request.getP_PARAM().get("role"));
        user.setStatus((String) request.getP_PARAM().get("status"));

        User updatedUser = userService.updateUser(id, user);
        ApiResponse response = new ApiResponse(updatedUser, "User updated successfully", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUser(@RequestBody ApiRequest request) {
        Long id = (Long) request.getP_PARAM().get("id");
        userService.deleteUser(id);
        ApiResponse response = new ApiResponse(null, "User deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}
