package com.test.datamanagement.service.impl;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import com.test.datamanagement.repository.DBConfigRepository;
import com.test.datamanagement.service.DBConfigService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DBConfigServiceImpl implements DBConfigService {
  private final DBConfigRepository dbConfigRepository;

  public DBConfigServiceImpl(DBConfigRepository dbConfigRepository) {
    this.dbConfigRepository = dbConfigRepository;
  }
  @Override
  public List<DBConfig> findAllEntity() {
    return dbConfigRepository.findAll();
  }

  public List<DBConfig> findAllByDatabaseOption(DatabaseOption databaseOption) {
    return dbConfigRepository.findAllByDbOption(databaseOption);
  }
  @Override
  public Optional<DBConfig> findById(String id) {
    return dbConfigRepository.findById(id);
  }
  @Override
  public List<DBConfig> findAllByDescription(String description) {
    return dbConfigRepository.findAllByDescription(description);
  }
  @Override
  public DBConfig saveEntity(DBConfig dbConfig) {
    List<DBConfig> entity = findAllByDatabaseOption(dbConfig.getDbOption());
    if (!entity.isEmpty()) {
      for(DBConfig ele : entity) {
        if (ele.equals(dbConfig)) return ele;
      }
    }
    return dbConfigRepository.save(dbConfig);
  }
  @Override
  public DBConfig updateEntity(DBConfig dbConfig) {
    return dbConfigRepository.save(dbConfig);
  }
  @Override
  public void deleteEntity(String id) {
    dbConfigRepository.deleteById(id);
  }

}
