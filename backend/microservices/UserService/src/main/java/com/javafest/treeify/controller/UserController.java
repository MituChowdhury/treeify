package com.javafest.treeify.controller;

import com.javafest.treeify.model.User;
import com.javafest.treeify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException {
        User user = userService.registerUser(email, password, profilePicture);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile-picture/{userId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String userId) {
        Resource profilePicture = userService.getProfilePicture(userId);
        return ResponseEntity.ok().body(profilePicture);
    }

    // Additional endpoints can be added here as needed.
}
