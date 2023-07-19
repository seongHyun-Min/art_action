package com.example.artaction.dto.auction;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAuctionRequestDto {
    @NotNull
    private Long artWorkId;

    @NotNull
    private long startingPrice;
    @NotEmpty(message = "시작날짜를 입력해주세요.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "시작날짜 형식은 yyyy-MM-dd HH:mm이어야 합니다.")
    private String startDateTime;

    @NotEmpty(message = "종료날짜를 입력해주세요.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "종료날짜 형식은 yyyy-MM-dd HH:mm이어야 합니다.")
    private String endDateTime;

    // Getters and Setters for LocalDateTime
    public LocalDateTime getStartTime() {
        return LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public LocalDateTime getEndTime() {
        return LocalDateTime.parse(endDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}

