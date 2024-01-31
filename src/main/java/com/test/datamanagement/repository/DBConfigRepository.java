package com.test.datamanagement.repository;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBConfigRepository extends MongoRepository<DBConfig, Long> {
  DBConfig findFirstByDescription(String description);
  List<DBConfig> findAllByDbOption(DatabaseOption databaseOption);
}
