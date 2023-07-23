package com.example.artaction.service;


import com.example.artaction.contant.CategoryType;
import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.domain.entity.User;
import com.example.artaction.domain.repository.ArtWorkRepository;
import com.example.artaction.domain.repository.UserRepository;
import com.example.artaction.dto.artwork.ArtWorkResponseDto;
import com.example.artaction.dto.artwork.PostArtWorkRequestDto;
import com.example.artaction.dto.artwork.UpdateArtWorkRequestDto;
import com.example.artaction.exception.artwork.NotFoundArtWorkException;
import com.example.artaction.exception.artwork.NotSaveArtWorkException;
import com.example.artaction.exception.user.NotAuthorizedUserException;
import com.example.artaction.exception.user.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ArtWorkService {
    private final ArtWorkRepository artWorkRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long post(PostArtWorkRequestDto requestDto) {
        User findUser = getUser(requestDto.getUserId());
        if (!findUser.getUserType().isSeller()) {
            throw new NotAuthorizedUserException("판매자 권한이 없습니다");
        }
        ArtWork artWork = requestDto.toEntity(findUser);
        try {
            return artWorkRepository.save(artWork).getId();
        } catch (NotSaveArtWorkException e) {
            throw new NotSaveArtWorkException("상품 등록에 실패 하였습니다");
        }
    }

    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto> findByCategory(Integer categoryValue) {
        return artWorkRepository.findByCategory(CategoryType.fromValue(categoryValue))
                .stream()
                .map(ArtWork::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto> findByUserId(Long userId) {
        User findUser = getUser(userId);
        return artWorkRepository.findByUser(findUser)
                .stream()
                .map(ArtWork::from)
                .toList();
    }

    @Transactional
    public Long update(Long artWorkId, UpdateArtWorkRequestDto requestDto) {
        ArtWork artWork = getArtWork(artWorkId);
        ArtWork updateArtWork = requestDto.toEntity(artWork);
        try {
            return artWorkRepository.save(updateArtWork).getId();
        } catch (NotSaveArtWorkException e) {
            throw new NotSaveArtWorkException("물건 수정에 실패 하였습니다");
        }
    }

    @Transactional
    public void delete(Long artWorkId) {
        ArtWork artWork = getArtWork(artWorkId);
        artWorkRepository.delete(artWork);
    }

    private ArtWork getArtWork(Long ArtWorkId) {
        ArtWork artWork = artWorkRepository.findById(ArtWorkId)
                .orElseThrow(() -> new NotFoundArtWorkException("아이디와 일치하는 상품을 찾을수 없습니다"));
        return artWork;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));
    }
}
