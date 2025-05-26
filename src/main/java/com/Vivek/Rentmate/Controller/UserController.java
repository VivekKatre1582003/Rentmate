package com.Vivek.Rentmate.Controller;

import com.Vivek.Rentmate.Model.User;
import com.Vivek.Rentmate.Repository.UserRepository;
import com.Vivek.Rentmate.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setAvatarUrl(userDto.getAvatarUrl());
        user.setLocation(userDto.getLocation());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCollege(userDto.getCollege());
        user.setDepartment(userDto.getDepartment());
        user.setYearOfStudy(userDto.getYearOfStudy());
        user.setHostelRoom(userDto.getHostelRoom());
        user.setBio(userDto.getBio());

        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
        Optional<User> user = userRepository.findByPhoneNumber(phone);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        return userRepository.findById(id).map(user -> {
            user.setFullName(userDto.getFullName());
            user.setAvatarUrl(userDto.getAvatarUrl());
            user.setLocation(userDto.getLocation());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setCollege(userDto.getCollege());
            user.setDepartment(userDto.getDepartment());
            user.setYearOfStudy(userDto.getYearOfStudy());
            user.setHostelRoom(userDto.getHostelRoom());
            user.setBio(userDto.getBio());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }
}
