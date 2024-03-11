package com.test.datamanagement.model;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.DatabaseOption;
import com.test.datamanagement.entity.TestConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RequestWorkload {
  // workload
  private String workloadType;
  private String updateType;

  // testConfig fields
  private int recordCounts = -1;
  private int concurrencyLevel = -1; // 64, 128, 256 maybe an enum?
  private String commandLine;

  // dbConfig fields
  private String type; // ycsb, ycsb-t, or ycsb-r
  private String platform;
  private int numOfNodes = -1; // number of CPUs: 5000 -> 5 CPUs
  private String isMultiRegional; // multi or single
  private String isCoLocated; // whether primary and client are in the same location
  private String locationDetails;
  private String description;

  // databaseOption fields
  private String database;

  @Override
  public String toString() {
    return "RequestWorkload{" +
        "workloadType='" + workloadType + '\'' +
        ", updateType='" + updateType + '\'' +
        ", concurrencyLevel=" + concurrencyLevel +
        ", recordCounts=" + recordCounts +
        ", commandLine='" + commandLine + '\'' +
        ", type='" + type + '\'' +
        ", platform='" + platform + '\'' +
        ", numOfNodes=" + numOfNodes +
        ", isMultiRegional='" + isMultiRegional + '\'' +
        ", isCoLocated='" + isCoLocated + '\'' +
        ", locationDetails='" + locationDetails + '\'' +
        ", description='" + description + '\'' +
        ", database='" + database + '\'' +
        '}';
  }
}
