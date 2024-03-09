package com.test.datamanagement.service;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.TestConfig;
import java.util.List;
import java.util.Optional;

public interface TestConfigService {
  List<TestConfig> findAllEntity();
  List<TestConfig> findAllByDbConfig(DBConfig dbConfig);
  Optional<TestConfig> findById(String id);
  List<TestConfig> findByCommandLine(String commandLine);
  TestConfig saveEntity(TestConfig testConfig);
  TestConfig updateEntity(TestConfig testConfig);
  void deleteEntity(String id);
}
