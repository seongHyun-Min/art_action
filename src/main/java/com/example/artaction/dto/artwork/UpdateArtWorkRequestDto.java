package com.example.artaction.dto.artwork;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArtWorkRequestDto {
    private String name;

    private String description;

    private String image;

    private Integer categoryType;
}
