package com.nubari.diary.services;

import com.nubari.diary.DTOs.DiaryDto;
import com.nubari.diary.DTOs.EntryRequestModel;
import com.nubari.diary.DTOs.EntryResponseModel;
import com.nubari.diary.exceptions.DiaryLimitReachedException;
import com.nubari.diary.exceptions.DiaryNotFoundException;
import com.nubari.diary.exceptions.UserNotFoundException;
import com.nubari.diary.models.Diary;
import com.nubari.diary.models.Entry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiaryService {
    public void createDiary(DiaryDto diaryDto, String userId) throws UserNotFoundException;

    public EntryResponseModel addEntry(EntryRequestModel entryRequestModel, String dairyId) throws DiaryLimitReachedException, DiaryNotFoundException;

    public List<EntryResponseModel> getAllEntries(String dairyId) throws DiaryNotFoundException;

    public DiaryDto getDiary(String diaryId) throws DiaryNotFoundException;

    public List<DiaryDto> getAllDiaries(String userId) throws UserNotFoundException;
}
