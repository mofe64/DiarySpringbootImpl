package com.nubari.diary.services;

import com.nubari.diary.DTOs.EntryRequestModel;
import com.nubari.diary.DTOs.EntryResponseModel;
import com.nubari.diary.exceptions.EntryNotFoundException;
import com.nubari.diary.models.Entry;

import java.util.List;

public interface EntryService {
    public void saveEntry(EntryRequestModel entryRequestModel);
    public EntryResponseModel findEntryById(String id) throws EntryNotFoundException;
    public List<Entry> getAllEntries();
    public void updateEntry(String id, EntryRequestModel entryRequestModel) throws EntryNotFoundException;
    public void deleteEntry(String id) throws EntryNotFoundException;

}
