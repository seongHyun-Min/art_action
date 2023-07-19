package com.example.artaction.controller;

import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.dto.artwork.ArtWorkResponseDto;
import com.example.artaction.dto.artwork.ArtWorkResponseDtoList;
import com.example.artaction.dto.artwork.PostArtWorkRequestDto;
import com.example.artaction.dto.artwork.UpdateArtWorkRequestDto;
import com.example.artaction.service.ArtWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/artworks")
@RequiredArgsConstructor
public class ArtWorkController {

    private final ArtWorkService artWorkService;

    @PostMapping()
    public ResponseEntity<Long> postArtWork(@Valid @RequestBody PostArtWorkRequestDto requestDto) {
        ArtWork postArtWork = artWorkService.post(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(postArtWork.getId());
    }

    @PutMapping("/{artWorkId}")
    public ResponseEntity<Long> updateArtWork(
            @PathVariable Long artWorkId,
            @Valid @RequestBody UpdateArtWorkRequestDto requestDto) {
        ArtWork updateArtWork = artWorkService.update(artWorkId, requestDto);

        return ResponseEntity.ok(updateArtWork.getId());
    }

    @DeleteMapping("/{userId}/{artWorkId}")
    public ResponseEntity<Void> deleteArtWork(
            @PathVariable Long userId,
            @PathVariable Long artWorkId) {
        artWorkService.delete(userId, artWorkId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryType}")
    public ResponseEntity<ArtWorkResponseDtoList> getArtWorkByCategory(@PathVariable Integer categoryType) {
        List<ArtWork> byCategory = artWorkService.findByCategory(categoryType);

        List<ArtWorkResponseDto> responseDtoList = convertResponseDtoList(byCategory);

        ArtWorkResponseDtoList responseDtoListWrapper = new ArtWorkResponseDtoList(responseDtoList);

        return ResponseEntity.ok(responseDtoListWrapper);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<ArtWorkResponseDtoList> getArtWorkByUserId(@PathVariable Long userId) {
        List<ArtWork> byUser = artWorkService.findByUser(userId);

        List<ArtWorkResponseDto> responseDtoList = convertResponseDtoList(byUser);

        ArtWorkResponseDtoList responseDtoListWrapper = new ArtWorkResponseDtoList(responseDtoList);

        return ResponseEntity.ok(responseDtoListWrapper);

    }


    private static List<ArtWorkResponseDto> convertResponseDtoList(List<ArtWork> byUser) {
        return byUser.stream()
                .map(artWork -> ArtWorkResponseDto.builder()
                        .name(artWork.getName())
                        .description(artWork.getDescription())
                        .image(artWork.getImage())
                        .categoryType(artWork.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

}
