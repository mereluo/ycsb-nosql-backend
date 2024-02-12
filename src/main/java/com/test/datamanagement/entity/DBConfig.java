package com.test.datamanagement.entity;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter @Setter @NoArgsConstructor
@Document("db_config")
public class DBConfig {
  @Id
  private String id;

  private String type; // ycsb, ycsb-t, or ycsb-r
  private String platform;
  private int numOfNodes;
  private int numOfRegions;
  private String description;

  @DocumentReference(lookup = "{ 'database': ?#{#target} }")
  private DatabaseOption dbOption;

  public DBConfig(String type, String platform, int numOfNodes, int numOfRegions,
      String description, DatabaseOption dbOption) {
    this.type = type;
    this.platform = platform;
    this.numOfNodes = numOfNodes;
    this.numOfRegions = numOfRegions;
    this.description = description;
    this.dbOption = dbOption;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DBConfig dbConfig = (DBConfig) o;
    return getNumOfNodes() == dbConfig.getNumOfNodes()
        && getNumOfRegions() == dbConfig.getNumOfRegions() && Objects.equals(getType(),
        dbConfig.getType()) && Objects.equals(getPlatform(), dbConfig.getPlatform())
        && Objects.equals(getDescription(), dbConfig.getDescription())
        && Objects.equals(getDbOption(), dbConfig.getDbOption());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType(), getPlatform(), getNumOfNodes(), getNumOfRegions(),
        getDescription(), getDbOption());
  }
}
