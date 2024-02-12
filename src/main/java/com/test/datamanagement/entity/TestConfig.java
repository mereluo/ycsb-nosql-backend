package com.test.datamanagement.entity;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter @Setter @NoArgsConstructor
@Document("test_config")
public class TestConfig {
  @Id
  private String id;

  private int concurrencyLevel; // 64, 128, 256
  private int recordCounts;
  private String commandLine;

  @DocumentReference
  private DBConfig dbConfig;

  public TestConfig(int concurrencyLevel, int recordCounts, String commandLine, DBConfig dbConfig) {
    this.concurrencyLevel = concurrencyLevel;
    this.recordCounts = recordCounts;
    this.commandLine = commandLine;
    this.dbConfig = dbConfig;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestConfig that = (TestConfig) o;
    return getConcurrencyLevel() == that.getConcurrencyLevel()
        && getRecordCounts() == that.getRecordCounts() && Objects.equals(getCommandLine(),
        that.getCommandLine()) && Objects.equals(getDbConfig(), that.getDbConfig());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getConcurrencyLevel(), getRecordCounts(), getCommandLine(), getDbConfig());
  }
}
