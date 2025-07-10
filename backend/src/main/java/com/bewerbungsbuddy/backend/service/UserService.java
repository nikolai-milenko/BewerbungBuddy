package com.bewerbungsbuddy.backend.service;

import com.bewerbungsbuddy.backend.dto.UserRequestDto;
import com.bewerbungsbuddy.backend.dto.UserResponseDto;
import com.bewerbungsbuddy.backend.entity.SubscriptionType;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.mapper.UserMapper;
import com.bewerbungsbuddy.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User getById(long id) {
        return userRepository.getOne(id);
    }

    public UserResponseDto register(UserRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email is already in use");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setSubscriptionPlan(SubscriptionType.valueOf(dto.subscriptionPlan())); // String → Enum
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());

        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    public UserResponseDto login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return userMapper.toResponseDto(user);
    }
}
