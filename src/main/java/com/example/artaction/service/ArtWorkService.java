package com.example.artaction.service;


import com.example.artaction.contant.CategoryType;
import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.domain.entity.User;
import com.example.artaction.domain.repository.ArtWorkRepository;
import com.example.artaction.domain.repository.UserRepository;
import com.example.artaction.dto.artwork.PostArtWorkRequestDto;
import com.example.artaction.exception.artwork.NotSaveArtWorkException;
import com.example.artaction.exception.user.NotAuthorizedUserException;
import com.example.artaction.exception.user.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ArtWorkService {
    private final ArtWorkRepository artWorkRepository;
    private final UserRepository userRepository;

    @Transactional
    public ArtWork post(Long userId, PostArtWorkRequestDto requestDto) {
        User findUser = userRepository.findById(userId)
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
        } catch (NotSaveArtWorkException e){
            throw new NotSaveArtWorkException("물건 등록에 실패하였습니다");
        }
    }
}
