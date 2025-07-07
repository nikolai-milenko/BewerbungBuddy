package com.bewerbungsbuddy.backend.user;

import com.bewerbungsbuddy.backend.dto.UserRequestDto;
import com.bewerbungsbuddy.backend.dto.UserResponseDto;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.mapper.UserMapper;
import com.bewerbungsbuddy.backend.repository.UserRepository;
import com.bewerbungsbuddy.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldSaveUserAndReturnDto() {
        UserRequestDto dto = new UserRequestDto("test@example.com", "password", "Test User", "FREE");
        User mappedUser = new User();
        mappedUser.setEmail(dto.email());

        User savedUser = new User();
        savedUser.setEmail(dto.email());

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(mappedUser);
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPass");
        when(userRepository.save(mappedUser)).thenReturn(savedUser);
        when(userMapper.toResponseDto(savedUser)).thenReturn(new UserResponseDto());

        UserResponseDto result = userService.register(dto);

        assertNotNull(result);
        verify(userRepository).save(mappedUser);
    }
}
