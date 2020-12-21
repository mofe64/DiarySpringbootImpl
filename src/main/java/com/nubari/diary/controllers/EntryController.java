package com.nubari.diary.controllers;

import com.nubari.diary.DTOs.APIResponseModel;
import com.nubari.diary.DTOs.EntryRequestModel;
import com.nubari.diary.DTOs.EntryResponseModel;
import com.nubari.diary.exceptions.EntryNotFoundException;
import com.nubari.diary.models.Entry;
import com.nubari.diary.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entries/")
public class EntryController {
    @Autowired
    private EntryService entryService;

    // @RequestParam String id
    @PostMapping("create")
    public ResponseEntity<?> createNewEntry(@RequestBody EntryRequestModel entryRequestModel) {
//        System.out.println(entryRequestModel.getTitle());
//        System.out.println(id);
        entryService.saveEntry(entryRequestModel);
        return new ResponseEntity<>(new APIResponseModel(true, "New Entry created"), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllEntries() {
        List<Entry> entries = entryService.getAllEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAnEntry(@RequestParam String id) {
        try {
            EntryResponseModel entry = entryService.findEntryById(id);
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } catch (EntryNotFoundException entryNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No Entry Found With That Id"), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("edit")
    public ResponseEntity<?> updateAnEntry(@RequestBody EntryRequestModel entryRequestModel, @RequestParam String id) {
        try {
            entryService.updateEntry(id, entryRequestModel);
            return new ResponseEntity<>(new APIResponseModel(true, "Entry successfully updated"), HttpStatus.OK);
        } catch (EntryNotFoundException entryNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No Entry found with that Id"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteAnEntry(@RequestParam String id) {
        try {
            entryService.deleteEntry(id);
            return new ResponseEntity<>(new APIResponseModel(true, "Entry successfully deleted"), HttpStatus.NO_CONTENT);
        } catch (EntryNotFoundException entryNotFoundException) {
            return new ResponseEntity<>(new APIResponseModel(false, "No Entry found with that Id"), HttpStatus.BAD_REQUEST);
        }
    }

}
