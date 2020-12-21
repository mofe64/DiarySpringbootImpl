package com.nubari.diary.services;

import com.nubari.diary.DTOs.DiaryDto;
import com.nubari.diary.DTOs.EntryRequestModel;
import com.nubari.diary.DTOs.EntryResponseModel;
import com.nubari.diary.exceptions.DiaryLimitReachedException;
import com.nubari.diary.exceptions.DiaryNotFoundException;
import com.nubari.diary.exceptions.UserNotFoundException;
import com.nubari.diary.models.Diary;
import com.nubari.diary.models.Entry;
import com.nubari.diary.models.User;
import com.nubari.diary.repositories.DiaryRepo;
import com.nubari.diary.repositories.EntryRepository;
import com.nubari.diary.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    DiaryRepo diaryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    EntryRepository entryRepository;

    @Override
    public void createDiary(DiaryDto diaryDto, String userId) throws UserNotFoundException {
        Diary diary = diaryDto.unpackDto();
        createDiary(diary, userId);
    }

    private void createDiary(Diary diary, String userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            Diary savedDiary = diaryRepo.save(diary);
            User user = userOptional.get();
            user.addDiary(savedDiary);
            userRepo.save(user);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public EntryResponseModel addEntry(EntryRequestModel entryRequestModel, String diaryId) throws DiaryLimitReachedException, DiaryNotFoundException {
        Entry entry = new Entry();
        entry.setTitle(entryRequestModel.getTitle());
        entry.setBody(entryRequestModel.getBody());
        entry.setLocalDateTime(LocalDateTime.now());
        Entry savedEntry = addEntry(entry, diaryId);
        EntryResponseModel entryResponseModel = new EntryResponseModel();
        entryResponseModel.setId(savedEntry.getId());
        entryResponseModel.setTitle(savedEntry.getTitle());
        entryResponseModel.setBody(savedEntry.getBody());
        entryResponseModel.setLocalDateTime(savedEntry.getLocalDateTime());
        return entryResponseModel;
    }

    private Entry addEntry(Entry entry, String diaryId) throws DiaryLimitReachedException, DiaryNotFoundException {
        Optional<Diary> diaryOptional = diaryRepo.findById(diaryId);
        if (diaryOptional.isPresent()) {
            Diary diary = diaryOptional.get();
            if (diary.getNumberOfEntriesSoFar() < diary.getDiaryLimit()) {
                Entry savedEntry = entryRepository.save(entry);
                diary.addEntry(savedEntry);
                diaryRepo.save(diary);
                return savedEntry;
            } else {
                throw new DiaryLimitReachedException();
            }

        } else {
            throw new DiaryNotFoundException();
        }
    }

    @Override
    public List<EntryResponseModel> getAllEntries(String diaryId) throws DiaryNotFoundException {
        ArrayList<EntryResponseModel> entriesModels = new ArrayList<>();
        List<Entry> entries = getAllEntriesInDiary(diaryId);
        for (Entry entry : entries) {
            EntryResponseModel entryResponseModel = new EntryResponseModel();
            entryResponseModel.setTitle(entry.getTitle());
            entryResponseModel.setBody(entry.getBody());
            entryResponseModel.setId(entry.getId());
            entryResponseModel.setLocalDateTime(entry.getLocalDateTime());
            entriesModels.add(entryResponseModel);
        }
        return entriesModels;
    }

    private List<Entry> getAllEntriesInDiary(String diaryId) throws DiaryNotFoundException {
        Optional<Diary> diaryOptional = diaryRepo.findById(diaryId);
        if (diaryOptional.isPresent()) {
            return diaryOptional.get().getEntries();
        } else {
            throw new DiaryNotFoundException();
        }

    }

    @Override
    public DiaryDto getDiary(String diaryId) throws DiaryNotFoundException {
        DiaryDto diaryDto = new DiaryDto();
        Diary diary = getADiary(diaryId);
        diaryDto = diaryDto.packDto(diary);

        return diaryDto;
    }

    private Diary getADiary(String diaryId) throws DiaryNotFoundException {
        Optional<Diary> diaryOptional = diaryRepo.findById(diaryId);
        if (diaryOptional.isPresent()) {
            return diaryOptional.get();
        } else {
            throw new DiaryNotFoundException();
        }
    }

    @Override
    public List<DiaryDto> getAllDiaries(String userId) throws UserNotFoundException {
        List<DiaryDto> userDiaryModels = new ArrayList<>();
        List<Diary> diaries = getAllDiariesBelongingToAUser(userId);
        for (Diary diary : diaries) {
            DiaryDto diaryDto = new DiaryDto();
            diaryDto = diaryDto.packDto(diary);
            userDiaryModels.add(diaryDto);
        }
        return userDiaryModels;
    }

    private List<Diary> getAllDiariesBelongingToAUser(String userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get().getDiaries();
        } else {
            throw new UserNotFoundException();
        }
    }
}
