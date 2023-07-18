package com.example.artaction.dto.artwork;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtWorkResponseDtoList {
    private List<ArtWorkResponseDto> artWorks;
}
