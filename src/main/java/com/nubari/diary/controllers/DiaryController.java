package com.nubari.diary.controllers;

import com.nubari.diary.DTOs.APIResponseModel;
import com.nubari.diary.DTOs.DiaryDto;
import com.nubari.diary.DTOs.EntryRequestModel;
import com.nubari.diary.DTOs.EntryResponseModel;
import com.nubari.diary.exceptions.DiaryLimitReachedException;
import com.nubari.diary.exceptions.DiaryNotFoundException;
import com.nubari.diary.exceptions.UserNotFoundException;
import com.nubari.diary.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary/")
public class DiaryController {
    @Autowired
    DiaryService diaryService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("create/{userId}")
    public ResponseEntity<?> createDiary(@RequestBody DiaryDto diaryDto, @PathVariable String userId) {
        try {
            diaryService.createDiary(diaryDto, userId);
            return new ResponseEntity<>(new APIResponseModel(true, "Diary created successfully"), HttpStatus.CREATED);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No user found with that Id"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("entry/create/{diaryId}")
    public ResponseEntity<?> addEntry(@RequestBody EntryRequestModel entryRequestModel, @PathVariable String diaryId) {
        try {
            EntryResponseModel entryResponseModel = diaryService.addEntry(entryRequestModel, diaryId);
            return new ResponseEntity<>(entryResponseModel, HttpStatus.OK);
        } catch (DiaryLimitReachedException diaryLimitReachedException) {
            return new ResponseEntity<>(new APIResponseModel(false, "This dairy's limit has been reached " +
                    "No further entries are allowed"), HttpStatus.METHOD_NOT_ALLOWED);
        } catch (DiaryNotFoundException diaryNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No diary found with that Id"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("{dairyID}/entry/all")
    public ResponseEntity<?> getAllEntriesInADiary(@PathVariable String dairyID) {
        try {
            List<EntryResponseModel> entries = diaryService.getAllEntries(dairyID);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (DiaryNotFoundException diaryNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No diary found with that Id"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{diaryId}")
    public ResponseEntity<?> getDiary(@PathVariable String diaryId) {
        try {
            DiaryDto diaryDto = diaryService.getDiary(diaryId);
            return new ResponseEntity<>(diaryDto, HttpStatus.OK);
        } catch (DiaryNotFoundException diaryNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No Diary found with that Id"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}/all")
    public ResponseEntity<?> getAllDiariesBelongingToAUser(@PathVariable String userId) {
        try {
            List<DiaryDto> diaryDtos = diaryService.getAllDiaries(userId);
            return new ResponseEntity<>(diaryDtos, HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No user found with that id"),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
