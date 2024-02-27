package com.test.datamanagement.model;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import com.test.datamanagement.entity.TestConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestWorkload {
  // workload
  private String workloadType;
  private String updateType;

  // testConfig fields
  private int concurrencyLevel; // 64, 128, 256 maybe an enum?
  private int recordCounts;
  private String commandLine;

  // dbConfig fields
  private String type; // ycsb, ycsb-t, or ycsb-r
  private String platform;
  private int numOfNodes; // number of CPUs: 5000 -> 5 CPUs
  private boolean isMultiRegional; // multi or single
  private boolean isCoLocated; // whether primary and client are in the same location
  private String locationDetails;
  private String description;

  // databaseOption fields
  private String database;

  public void setIsCoLocated(boolean isCoLocated) {
    this.isCoLocated = isCoLocated;
  }

  public DBConfig getDBConfig(DatabaseOption dbOption) {
    return new DBConfig(type, platform, numOfNodes, isMultiRegional, description,
        isCoLocated, locationDetails, dbOption);
  }
  public TestConfig getTestConfig(DBConfig dbConfig) {
    return new TestConfig(concurrencyLevel, recordCounts, commandLine, dbConfig);
  }
}
