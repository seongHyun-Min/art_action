package com.example.artaction.service;


import com.example.artaction.contant.UserType;
import com.example.artaction.domain.entity.User;
import com.example.artaction.dto.user.UserRequestDto;
import com.example.artaction.exception.user.NotFoundUserException;
import com.example.artaction.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(UserRequestDto requestDto) {
        User user = User.builder()
                .name(requestDto.getUserName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .userType(UserType.fromValue(requestDto.getUserType()))
                .build();

        try {
            return userRepository.save(user);
        } catch (NotFoundUserException e) {
            throw new NotFoundUserException("유저 생성이 실패 하였습니다");
        }
    }

    @Transactional
    public User update(Long userId, UserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));

        User updatedUser = User.builder()
                .id(user.getId())
                .email(requestDto.getEmail() != null ? requestDto.getEmail() : user.getEmail())
                .name(requestDto.getUserName() != null ? requestDto.getUserName() : user.getName())
                .password(requestDto.getPassword() != null ? requestDto.getPassword() : user.getPassword())
                .build();

        try {
            return userRepository.save(updatedUser);
        } catch (NotFoundUserException e) {
            throw new NotFoundUserException("유저 업데이트가 실패 하였습니다");
        }
    }

    @Transactional
    public void delete(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);

        if (findUser.isPresent()) {
            userRepository.delete(findUser.get());
        } else {
            throw new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다");
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));
    }


}
