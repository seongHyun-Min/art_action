package com.example.artaction.dto.bid;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBidRequestDto {
    @NotNull
    private Long ActionId;
    @NotNull
    private Long userId;
    @NotBlank
    private long price;
}
