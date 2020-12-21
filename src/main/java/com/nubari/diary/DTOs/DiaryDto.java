package com.nubari.diary.DTOs;

import com.nubari.diary.models.Diary;
import com.nubari.diary.models.Entry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DiaryDto {
    private String id;
    private String diaryName;
    private int diaryLimit;
    private ArrayList<Entry> entries = new ArrayList<>();
    private int numberOfEntriesSoFar;

    public Diary unpackDto() {
        Diary diary = new Diary();
        diary.setDiaryName(diaryName);
        diary.setDiaryLimit(diaryLimit);
        diary.setEntries(entries);
        diary.setNumberOfEntriesSoFar(numberOfEntriesSoFar);
        return diary;
    }

    public DiaryDto packDto(Diary diary) {
        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setDiaryName(diary.getDiaryName());
        diaryDto.setDiaryLimit(diary.getDiaryLimit());
        diaryDto.setEntries(diary.getEntries());
        diaryDto.setId(diary.getId());
        diaryDto.setNumberOfEntriesSoFar(diary.getNumberOfEntriesSoFar());
        return diaryDto;


    }

}
