package com.example.artaction.dto.user;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    private String userName;

    private String email;

    private String password;

    private Integer userType;

}
