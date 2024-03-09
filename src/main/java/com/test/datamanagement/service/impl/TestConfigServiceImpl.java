package com.test.datamanagement.service.impl;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.repository.TestConfigRepository;
import com.test.datamanagement.service.TestConfigService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TestConfigServiceImpl implements TestConfigService {
  private final TestConfigRepository testConfigRepository;

  public TestConfigServiceImpl(TestConfigRepository testConfigRepository) {
    this.testConfigRepository = testConfigRepository;
  }
  @Override
  public List<TestConfig> findAllEntity() {
    return testConfigRepository.findAll();
  }

  public List<TestConfig> findAllByDbConfig(DBConfig dbConfig) {
    return testConfigRepository.findAllByDbConfig(dbConfig);
  }
  @Override
  public Optional<TestConfig> findById(String id) {
    return testConfigRepository.findById(id);
  }
  @Override
  public List<TestConfig> findByCommandLine(String commandLine) {
    return testConfigRepository.findAllByCommandLine(commandLine);
  }
  @Override
  public TestConfig saveEntity(TestConfig testConfig) {
    List<TestConfig> entity = findByCommandLine(testConfig.getCommandLine());
    if (!entity.isEmpty()) {
      for (TestConfig element : entity) {
        if (element.equals(testConfig)) return element;
      }
    }
    return testConfigRepository.save(testConfig);
  }
  @Override
  public TestConfig updateEntity(TestConfig testConfig) {
    return testConfigRepository.save(testConfig);
  }
  @Override
  public void deleteEntity(String id) {
    testConfigRepository.deleteById(id);
  }

}
