package com.example.artaction.dto.artwork;

import com.example.artaction.contant.CategoryType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtWorkResponseDto {
    private String name;

    private String description;

    private String image;

    private CategoryType categoryType;

}
