package com.test.datamanagement.model;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompleteWorkload {

  // workload fields
  private String workloadType;
  private String updateType;
  private Map<String, Double> userDefinedFields;
  private TimeSeries timeSeries;

  // testConfig fields
  private int concurrencyLevel; // 64, 128, 256 maybe an enum?
  private int recordCounts;
  private String commandLine;

  // dbConfig fields
  private String type; // ycsb, ycsb-t, or ycsb-r
  private String platform;
  private int numOfNodes;
  private int numOfRegions;
  private String description;

  // databaseOption fields
  private String database;

  public DBConfig getDBConfig(DatabaseOption dbOption) {
    return new DBConfig(type, platform, numOfNodes, numOfRegions, description, dbOption);
  }
  public TestConfig getTestConfig(DBConfig dbConfig) {
    return new TestConfig(concurrencyLevel, recordCounts, commandLine, dbConfig);
  }
  public Workload getWorkload(TestConfig testConfig) {
    return new Workload(workloadType, updateType, userDefinedFields, timeSeries, testConfig);
  }

}
