package com.nubari.diary.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class APIResponseModel {
    private boolean isSuccessful;
    private String message;
}
