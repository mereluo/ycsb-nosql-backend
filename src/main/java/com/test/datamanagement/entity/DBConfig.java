package com.test.datamanagement.entity;

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
}
