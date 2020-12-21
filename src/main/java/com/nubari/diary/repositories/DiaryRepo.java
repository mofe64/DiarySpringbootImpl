package com.nubari.diary.repositories;

import com.nubari.diary.models.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepo extends MongoRepository<Diary, String> {
}
