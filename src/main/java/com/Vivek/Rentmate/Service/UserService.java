package com.Vivek.Rentmate.Service;

import com.Vivek.Rentmate.Model.User;
import com.Vivek.Rentmate.Repository.UserRepository;
import com.Vivek.Rentmate.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDto userDto) {
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
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User updateUser(UUID id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullName(userDto.getFullName());
            user.setAvatarUrl(userDto.getAvatarUrl());
            user.setLocation(userDto.getLocation());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setCollege(userDto.getCollege());
            user.setDepartment(userDto.getDepartment());
            user.setYearOfStudy(userDto.getYearOfStudy());
            user.setHostelRoom(userDto.getHostelRoom());
            user.setBio(userDto.getBio());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
