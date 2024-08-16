package com.javafest.treeify.service;

import com.javafest.treeify.model.User;
import com.javafest.treeify.repository.UserRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GridFSService gridFsService;

    public User registerUser(String email, String password, MultipartFile profilePicture) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            // Upload profile picture and set the ObjectId
            ObjectId profilePictureId = gridFsService.uploadFile(profilePicture);
            user.setProfilePictureId(profilePictureId.toHexString());
            user.setPoints(50); // Initialize points

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload profile picture", e);
        }

        // Add default plant if needed
        return userRepository.save(user);
    }

    public Resource getProfilePicture(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        try {
            // Convert ObjectId from user to actual ObjectId for download
            ObjectId profilePictureId = new ObjectId(user.getProfilePictureId());
            byte[] profilePictureData = gridFsService.downloadFile(profilePictureId);

            return new ByteArrayResource(profilePictureData);
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve profile picture", e);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
