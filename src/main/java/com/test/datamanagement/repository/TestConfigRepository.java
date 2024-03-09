package com.test.datamanagement.repository;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.TestConfig;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestConfigRepository extends MongoRepository<TestConfig, String> {
  List<TestConfig> findAllByCommandLine(String commandLine);
  List<TestConfig> findAllByDbConfig(DBConfig config);
}
