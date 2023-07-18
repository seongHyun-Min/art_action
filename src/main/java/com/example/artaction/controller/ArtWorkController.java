package com.example.artaction.controller;

import com.example.artaction.dto.artwork.PostArtWorkRequestDto;
import com.example.artaction.service.ArtWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/artworks")
@RequiredArgsConstructor
public class ArtWorkController {

    private final ArtWorkService artWorkService;

    @PostMapping("/{userId}")
    public ResponseEntity<Long> PostArtWork(@Valid @RequestBody PostArtWorkRequestDto,)
}
