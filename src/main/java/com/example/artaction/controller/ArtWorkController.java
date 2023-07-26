package com.example.artaction.controller;

import com.example.artaction.dto.artwork.ArtWorkResponseDto;
import com.example.artaction.dto.artwork.PostArtWorkRequestDto;
import com.example.artaction.dto.artwork.UpdateArtWorkRequestDto;
import com.example.artaction.service.ArtWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/artworks")
@RequiredArgsConstructor
public class ArtWorkController {

    private final ArtWorkService artWorkService;

    @PostMapping()
    public ResponseEntity<Long> postArtWork(@Valid @RequestBody PostArtWorkRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artWorkService.post(requestDto));
    }

    @PatchMapping("/{artWorkId}")
    public ResponseEntity<Long> updateArtWork(
            @PathVariable Long artWorkId,
            @Valid @RequestBody UpdateArtWorkRequestDto requestDto) {
        return ResponseEntity.ok(artWorkService.update(artWorkId, requestDto));
    }

    @DeleteMapping("/{artWorkId}")
    public ResponseEntity<Void> deleteArtWork(
            @PathVariable Long artWorkId) {
        artWorkService.delete(artWorkId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryType}")
    public ResponseEntity<List<ArtWorkResponseDto>> getArtWorkByCategory(@PathVariable Integer categoryType) {
        List<ArtWorkResponseDto> byCategory = artWorkService.findByCategory(categoryType);
        return ResponseEntity.ok(byCategory);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ArtWorkResponseDto>> getArtWorkByUserId(@PathVariable Long userId) {
        List<ArtWorkResponseDto> byUserId = artWorkService.findByUserId(userId);
        return ResponseEntity.ok(byUserId);
    }
}
