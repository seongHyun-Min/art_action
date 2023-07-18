package com.example.artaction.service;


import com.example.artaction.contant.CategoryType;
import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.domain.entity.User;
import com.example.artaction.domain.repository.ArtWorkRepository;
import com.example.artaction.domain.repository.UserRepository;
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
    public ArtWork post(PostArtWorkRequestDto requestDto) {
        User findUser = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));

        if (!findUser.getUserType().isSeller()) {
            throw new NotAuthorizedUserException("판매자 권한이 없습니다");
        }
        ArtWork artWork = ArtWork.builder()
                .user(findUser)
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .image(requestDto.getImage())
                .category(CategoryType.fromValue(requestDto.getCategoryType()))
                .build();

        try {
            return artWorkRepository.save(artWork);
        } catch (NotSaveArtWorkException e) {
            throw new NotSaveArtWorkException("물건 등록에 실패 하였습니다");
        }
    }

    @Transactional(readOnly = true)
    public List<ArtWork> findByCategory(Integer categoryValue) {
        return artWorkRepository.findByCategory(CategoryType.fromValue(categoryValue))
                .orElseThrow(() -> new NotFoundArtWorkException("조회 데이터(예술품)가 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<ArtWork> findByUser(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));

        return artWorkRepository.findByUser(findUser)
                .orElseThrow(() -> new NotFoundArtWorkException("조회 데이터(예술품)가 없습니다."));
    }

    @Transactional
    public ArtWork update(Long artWorkId, UpdateArtWorkRequestDto requestDto) {
        User findUser = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NotFoundUserException("아이디와 일치하는 회원을 찾을 수 없습니다"));

        ArtWork artWork = artWorkRepository.findByIdAndUserId(requestDto.getUserId(), artWorkId)
                .orElseThrow(() -> new NotFoundArtWorkException("상품에 접근 권한이 없거나 아이디와 일치하는 물건을 찾을 수 없습니다"));


        ArtWork UpdateArtWork = ArtWork.builder()
                .id(artWork.getId())
                .user(findUser)
                .name(requestDto.getName() != null ? requestDto.getName() : artWork.getName())
                .description(requestDto.getDescription() != null ? requestDto.getDescription() :
                        artWork.getDescription())
                .image(requestDto.getImage() != null ? requestDto.getImage() : artWork.getImage())
                .category(requestDto.getCategoryType() != null ?
                        CategoryType.fromValue(requestDto.getCategoryType()) : artWork.getCategory())
                .build();

        try {
            return artWorkRepository.save(UpdateArtWork);
        } catch (NotSaveArtWorkException e) {
            throw new NotSaveArtWorkException("물건 수정에 실패 하였습니다");
        }
    }

    @Transactional
    public void delete(Long userId, Long artWorkId) {
        ArtWork artWork = artWorkRepository.findByIdAndUserId(userId, artWorkId)
                .orElseThrow(() -> new NotFoundArtWorkException("상품에 접근 권한이 없거나 아이디와 일치하는 물건을 찾을 수 없습니다"));

        artWorkRepository.delete(artWork);
    }
}
