package com.test.datamanagement.entity;

import com.test.datamanagement.model.TimeSeries;

import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter @Setter @NoArgsConstructor
@Document("workload")
public class Workload {
  @Id
  private String id;
  private String workloadType;
  private String updateType;
  private String workloadDescription;
  private Map<String, Double> userDefinedFields;
  // A column that uses Json or other dt to store Time series
  @JdbcTypeCode(SqlTypes.JSON)
  private TimeSeries timeSeries;

  @DocumentReference
  private TestConfig testConfig;

  public Workload(String workloadType, String updateType, String workloadDescription, Map<String, Double> userDefinedFields,
      TimeSeries timeSeries, TestConfig testConfig) {
    this.workloadType = workloadType;
    this.updateType = updateType;
    this.userDefinedFields = userDefinedFields;
    this.timeSeries = timeSeries;
    this.testConfig = testConfig;
    this.workloadDescription = workloadDescription;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Workload workload = (Workload) o;
    return Objects.equals(getWorkloadType(), workload.getWorkloadType())
        && Objects.equals(getUpdateType(), workload.getUpdateType())
        && Objects.equals(getUserDefinedFields(), workload.getUserDefinedFields())
        && Objects.equals(getTimeSeries(), workload.getTimeSeries())
        && Objects.equals(getTestConfig(), workload.getTestConfig());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getWorkloadType(), getUpdateType(), getUserDefinedFields(), getTimeSeries(),
        getTestConfig());
  }
}