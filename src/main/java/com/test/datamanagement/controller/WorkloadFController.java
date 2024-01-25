package com.test.datamanagement.controller;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.WorkloadF;
import com.test.datamanagement.model.CompleteWorkload;
import com.test.datamanagement.model.RequestWorkload;
import com.test.datamanagement.service.DBConfigService;
import com.test.datamanagement.service.DBOptionService;
import com.test.datamanagement.service.TestConfigService;
import com.test.datamanagement.service.WorkloadFService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/workloadF")
public class WorkloadFController {

  private final DBOptionService dbOptionService;
  private final DBConfigService dbConfigService;
  private final TestConfigService testConfigService;
  private final WorkloadFService workloadFService;

  public WorkloadFController(WorkloadFService workloadFService, DBOptionService dbOptionService,
      DBConfigService dbConfigService, TestConfigService testConfigService) {
    this.workloadFService = workloadFService;
    this.dbOptionService = dbOptionService;
    this.dbConfigService = dbConfigService;
    this.testConfigService = testConfigService;
  }
  @GetMapping("")
  public List<WorkloadF> findAllEntity() {
    return workloadFService.findAllEntity();
  }

  @PostMapping("/save")
  public WorkloadF create(@RequestBody CompleteWorkload entity) {
    DatabaseOption dbOption = new DatabaseOption(entity.getDatabase());
    dbOption = dbOptionService.saveEntity(dbOption);

    DBConfig dbConfig = entity.getDBConfig(dbOption);
    dbConfig = dbConfigService.saveEntity(dbConfig);

    TestConfig testConfig = entity.getTestConfig(dbConfig);
    testConfig = testConfigService.saveEntity(testConfig);

    WorkloadF workloadF = entity.getWorkloadF(testConfig);
    return workloadFService.saveEntity(workloadF);
  }

  @PostMapping("/retrieve")
  public WorkloadF retrieve(@RequestBody RequestWorkload entity) {
    // database
    DatabaseOption dbOption = dbOptionService.findFirstByDatabase(entity.getDatabase());
    // dbConfig
    DBConfig foundDbConfig = null;
    DBConfig targetDbConfig = entity.getDBConfig(dbOption);
    List<DBConfig> dbConfigLists = dbConfigService.findAllByDatabaseOption(dbOption);
    for (DBConfig curr : dbConfigLists) {
      if (curr.equals(targetDbConfig)) {
        foundDbConfig = curr;
        break;
      }
    }
    // testConfig
    TestConfig foundTestConfig = null;
    TestConfig targetTestConfig = entity.getTestConfig(foundDbConfig);
    List<TestConfig> testConfigs = testConfigService.findAllByDbConfig(foundDbConfig);
    for (TestConfig curr : testConfigs) {
      if (curr.equals(targetTestConfig)) {
        foundTestConfig = curr;
        break;
      }
    }
    // workload
    return workloadFService.findFirstByTestConfig(foundTestConfig);
  }
}
