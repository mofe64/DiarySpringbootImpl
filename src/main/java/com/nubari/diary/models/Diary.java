package com.nubari.diary.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Diary {
    @Id
    private String id;
    private String diaryName;
    private int diaryLimit;
    @DBRef
    private ArrayList<Entry> entries = new ArrayList<>();
    private int numberOfEntriesSoFar;

    public void addEntry(Entry entry) {
        entries.add(entry);
        numberOfEntriesSoFar++;
    }
}
