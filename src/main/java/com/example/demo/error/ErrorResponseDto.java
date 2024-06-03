package com.example.demo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class ErrorResponseDto {
    int httpStatus;
    String errorMessage;
    LocalDateTime currentDate;
}
