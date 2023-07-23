package com.example.artaction.dto.user;

import com.example.artaction.domain.entity.User;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {
    private String userName;

    private String email;

    private String password;

    public User toEntity(User user) {
        return User.builder()
                .id(user.getId())
                .name(this.userName != null ? this.userName : user.getName())
                .email(this.email != null ? this.email : user.getEmail())
                .password(this.password != null ? this.password : user.getPassword())
                .userType(user.getUserType())
                .build();
    }
}
