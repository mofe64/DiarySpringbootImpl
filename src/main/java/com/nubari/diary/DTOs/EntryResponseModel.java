package com.nubari.diary.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EntryResponseModel {
    @Min(value = 3, message = "Entry title must be at least 3 characters long")
    @Max(value = 30, message = "Entry title cannot be over 30 characters")
    private String title;
    private String body;
    private LocalDateTime localDateTime;
    private String id;

}
