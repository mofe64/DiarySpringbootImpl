package com.nubari.diary.repositories;

import com.nubari.diary.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EntryRepository extends MongoRepository<Entry, String> {
    public Optional<Entry> findEntryByTitle(String title);
    public List<Entry> findEntryByBodyContaining(String keyword);
}
