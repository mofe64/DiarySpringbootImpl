package com.nubari.diary.services;

import com.nubari.diary.DTOs.EntryRequestModel;
import com.nubari.diary.DTOs.EntryResponseModel;
import com.nubari.diary.exceptions.EntryNotFoundException;
import com.nubari.diary.models.Entry;
import com.nubari.diary.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public void saveEntry(EntryRequestModel entryRequestModel) {
        Entry entryToSave = new Entry();
        entryToSave.setTitle(entryRequestModel.getTitle());
        entryToSave.setBody(entryRequestModel.getBody());
        entryToSave.setLocalDateTime(LocalDateTime.now());
        saveEntry(entryToSave);
    }

    private void saveEntry(Entry entry) {
        entryRepository.save(entry);
    }

    @Override
    public EntryResponseModel findEntryById(String id) throws EntryNotFoundException {
        Entry entryFound = findAnEntryById(id);
        EntryResponseModel entryResponseModel = new EntryResponseModel();
        entryResponseModel.setTitle(entryFound.getTitle());
        entryResponseModel.setBody(entryFound.getBody());
        entryResponseModel.setLocalDateTime(entryFound.getLocalDateTime());
        return entryResponseModel;
    }

    private Entry findAnEntryById(String id) throws EntryNotFoundException {
        Optional<Entry> entry = entryRepository.findById(id);
        if (entry.isPresent()) {
            return entry.get();
        } else {
            throw new EntryNotFoundException();
        }
    }

    @Override
    public List<Entry> getAllEntries() {
        return getAllEntriesFromRepo();
    }

    private List<Entry> getAllEntriesFromRepo() {
        return entryRepository.findAll();
    }

    @Override
    public void updateEntry(String id, EntryRequestModel entryRequestModel) throws EntryNotFoundException {
        Entry entryToUpdate = findAnEntryById(id);
        if (entryRequestModel.getTitle() != null && entryRequestModel.getTitle() != "") {
            entryToUpdate.setTitle(entryRequestModel.getTitle());
        }
        if (entryRequestModel.getBody() != null && entryRequestModel.getBody() != "") {
            entryToUpdate.setBody(entryRequestModel.getBody());
        }
        entryToUpdate.setLocalDateTime(LocalDateTime.now());
        entryRepository.save(entryToUpdate);
    }

    @Override
    public void deleteEntry(String id) throws EntryNotFoundException {
        deleteEntryFromRepo(id);
    }

    private void deleteEntryFromRepo(String id) throws EntryNotFoundException {
        Optional<Entry> entryToDelete = entryRepository.findById(id);
        if (entryToDelete.isPresent()) {
            entryRepository.delete(entryToDelete.get());
        } else {
            throw new EntryNotFoundException();
        }
    }
}
