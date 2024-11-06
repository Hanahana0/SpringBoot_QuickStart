package qlinx.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qlinx.entity.User;
import qlinx.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/getById")
    public ResponseEntity<User> getUserById(@RequestBody Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userService.updateUser(user.getId(), user));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
