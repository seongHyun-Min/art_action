package com.example.artaction.controller;


import com.example.artaction.domain.entity.User;
import com.example.artaction.dto.user.CreateUserRequestDto;
import com.example.artaction.dto.user.UpdateUserRequestDto;
import com.example.artaction.dto.user.UserResponseDto;
import com.example.artaction.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        User saveUser = userService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser.getId());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Long> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequestDto requestDto) {
        User updateUser = userService.update(userId, requestDto);

        return ResponseEntity.ok((updateUser.getId()));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        User findUser = userService.findById(userId);

        UserResponseDto responseUser = UserResponseDto.builder()
                .id(findUser.getId())
                .name(findUser.getName())
                .email(findUser.getEmail())
                .userType(findUser.getUserType())
                .build();

        return ResponseEntity.ok(responseUser);
    }
}



