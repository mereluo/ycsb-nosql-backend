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
  private int numOfNodes; // number of CPUs: 5000 -> 5 CPUs
  private String isMultiRegional; // multi or single
  private String isCoLocated; // whether primary and client are in the same location
  private String locationDetails;
  private String description;


  @DocumentReference(lookup = "{ 'database': ?#{#target} }")
  private DatabaseOption dbOption;

  public DBConfig(String type, String platform, int numOfNodes, String isMultiRegional,
      String description, String isCoLocated, String locationDetails, DatabaseOption dbOption) {
    this.type = type;
    this.platform = platform;
    this.numOfNodes = numOfNodes;
    this.isMultiRegional = isMultiRegional;
    this.description = description;
    this.isCoLocated = isCoLocated;
    this.locationDetails = locationDetails;
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
    return getNumOfNodes() == dbConfig.getNumOfNodes() && Objects.equals(getType(),
        dbConfig.getType()) && Objects.equals(getPlatform(), dbConfig.getPlatform())
        && Objects.equals(getIsMultiRegional(), dbConfig.getIsMultiRegional())
        && Objects.equals(getIsCoLocated(), dbConfig.getIsCoLocated())
        && Objects.equals(getDescription(), dbConfig.getDescription())
        && Objects.equals(getDbOption(), dbConfig.getDbOption());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType(), getPlatform(), getNumOfNodes(), getIsMultiRegional(),
        getIsCoLocated(), getDescription(), getDbOption());
  }
}
