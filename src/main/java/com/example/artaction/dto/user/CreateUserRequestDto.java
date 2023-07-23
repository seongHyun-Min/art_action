package com.example.artaction.dto.user;

import com.example.artaction.contant.UserType;
import com.example.artaction.domain.entity.User;
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

    public User toEntity() {
        return User.builder()
                .name(userName)
                .email(email)
                .password(password)
                .userType(UserType.fromValue(userType))
                .build();
    }
}
