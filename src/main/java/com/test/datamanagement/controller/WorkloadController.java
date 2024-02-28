package com.test.datamanagement.controller;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import com.test.datamanagement.model.CompleteWorkload;
import com.test.datamanagement.model.RequestWorkload;
import com.test.datamanagement.service.DBConfigService;
import com.test.datamanagement.service.DBOptionService;
import com.test.datamanagement.service.TestConfigService;
import com.test.datamanagement.service.WorkloadService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/workload")
public class WorkloadController {

  private final DBOptionService dbOptionService;
  private final DBConfigService dbConfigService;
  private final TestConfigService testConfigService;
  private final WorkloadService workloadService;

  public WorkloadController(WorkloadService workloadService, DBOptionService dbOptionService,
      DBConfigService dbConfigService, TestConfigService testConfigService) {
    this.workloadService = workloadService;
    this.dbOptionService = dbOptionService;
    this.dbConfigService = dbConfigService;
    this.testConfigService = testConfigService;
  }

  @PostMapping
  public Workload saveEntity(@RequestBody Workload workload) {
    return workloadService.saveEntity(workload);
  }

  @PostMapping("/save")
  public Workload create(@RequestBody CompleteWorkload entity) {
    DatabaseOption dbOption = new DatabaseOption(entity.getDatabase());
    dbOption = dbOptionService.saveEntity(dbOption);

    DBConfig dbConfig = entity.getDBConfig(dbOption);
    dbConfig = dbConfigService.saveEntity(dbConfig);

    TestConfig testConfig = entity.getTestConfig(dbConfig);
    testConfig = testConfigService.saveEntity(testConfig);

    Workload workload = entity.getWorkload(testConfig);
    return workloadService.saveEntity(workload);
  }

  @PostMapping("/search")
  public List<Workload> retrieve(@RequestBody RequestWorkload entity) {
    return workloadService.findAllByProperties(entity);
  }
}