package com.test.datamanagement.repository;

import com.test.datamanagement.entity.DatabaseOption;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseOptionRepository extends MongoRepository<DatabaseOption, Long> {
  DatabaseOption findFirstByDatabase(String name);
}
