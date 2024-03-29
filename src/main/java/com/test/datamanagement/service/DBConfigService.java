package com.test.datamanagement.service;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import java.util.List;
import java.util.Optional;

public interface DBConfigService {
  List<DBConfig> findAllEntity();
  List<DBConfig> findAllByDatabaseOption(DatabaseOption databaseOption);
  Optional<DBConfig> findById(String id);
  List<DBConfig> findAllByDescription(String description);
  DBConfig saveEntity(DBConfig dbConfig);
  DBConfig updateEntity(DBConfig dbConfig);
  void deleteEntity(String id);
}
