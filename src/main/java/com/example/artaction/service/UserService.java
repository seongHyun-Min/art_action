package com.example.artaction.service;


import com.example.artaction.domain.entity.User;
import com.example.artaction.dto.user.CreateUserRequestDto;
import com.example.artaction.dto.user.UpdateUserRequestDto;
import com.example.artaction.dto.user.UserResponseDto;
import com.example.artaction.exception.user.NotFoundUserException;
import com.example.artaction.domain.repository.UserRepository;
import com.example.artaction.exception.user.NotSaveUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(CreateUserRequestDto requestDto) {
        User user = requestDto.toEntity();
        try {
            return userRepository.save(user).getId();
        } catch (NotSaveUserException e) {
            throw new NotSaveUserException("유저 생성이 실패 하였습니다");
        }
    }

    @Transactional
    public Long update(Long userId, UpdateUserRequestDto requestDto) {
        User user = getUser(userId);
        User updateUser = requestDto.toEntity(user);
        try {
            return userRepository.save(updateUser).getId();
        } catch (NotFoundUserException e) {
            throw new NotSaveUserException("유저 업데이트가 실패 하였습니다");
        }
    }

    @Transactional
    public void delete(Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long userId) {
        return getUser(userId).from();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));
    }
}
