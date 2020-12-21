package com.nubari.diary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Entry {
    @Id
    private String id;
    private String title;
    private String body;
    private LocalDateTime localDateTime;
}
