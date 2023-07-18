package com.example.artaction.dto.user;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {
    @NotNull
    private String userName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Integer userType;

}
