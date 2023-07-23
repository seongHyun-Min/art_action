package com.example.artaction.dto.artwork;

import com.example.artaction.contant.CategoryType;
import com.example.artaction.domain.entity.ArtWork;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArtWorkRequestDto {
    @NotNull
    private Long userId;

    private String name;

    private String description;

    private String image;

    private Integer categoryType;

    public ArtWork toEntity(ArtWork artWork) {
        return ArtWork.builder()
                .id(artWork.getId())
                .name(this.getName() != null ? this.getName() : artWork.getName())
                .description(this.getDescription() != null ? this.getDescription() : artWork.getDescription())
                .image(this.getImage() != null ? this.getImage() : artWork.getImage())
                .category(this.getCategoryType() != null ? CategoryType.fromValue(this.getCategoryType()) :
                        artWork.getCategory())
                .user(artWork.getUser())
                .build();
    }
}
