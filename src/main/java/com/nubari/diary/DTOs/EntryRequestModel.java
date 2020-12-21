package com.nubari.diary.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EntryRequestModel {
    @Min(value = 3, message = "Entry title must be at least 3 characters long")
    @Max(value = 30, message = "Entry title cannot be over 30 characters")
    String title;
    String body;
}
