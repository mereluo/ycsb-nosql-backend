package com.test.datamanagement.entity;

import com.test.datamanagement.model.TimeSeries;

import java.util.HashMap;
import java.util.Map;
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
  private Map<String, Double> userDefinedFields = new HashMap<>();
  // A column that uses Json or other dt to store Time series
  @JdbcTypeCode(SqlTypes.JSON)
  private TimeSeries timeSeries;

  @DocumentReference
  private TestConfig testConfig;

}