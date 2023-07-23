package com.example.artaction.dto.artwork;

import com.example.artaction.contant.CategoryType;
import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.domain.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostArtWorkRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private String name;

    private String description;

    private String image;

    private Integer categoryType;

    public ArtWork toEntity(User user) {
        return ArtWork.builder()
                .name(name)
                .description(description)
                .image(image)
                .category(CategoryType.fromValue(categoryType))
                .user(user)
                .build();
    }
}
