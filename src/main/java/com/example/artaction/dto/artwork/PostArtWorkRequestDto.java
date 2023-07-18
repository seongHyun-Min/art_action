package com.example.artaction.dto.artwork;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostArtWorkRequestDto {
    @NotNull
    private String name;


    private String description;

    private String image;

    private Integer categoryType;

}
