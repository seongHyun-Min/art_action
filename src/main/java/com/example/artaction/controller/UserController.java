package com.example.artaction.controller;


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
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(requestDto));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Long> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequestDto requestDto) {
        return ResponseEntity.ok((userService.update(userId, requestDto)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}




