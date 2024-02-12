package com.test.datamanagement.entity;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter @Setter @NoArgsConstructor
@Document("database_option")
public class DatabaseOption {
  @Id
  private String id;
  private String database;

  public DatabaseOption(String database) {
    this.database = database;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatabaseOption that = (DatabaseOption) o;
    return Objects.equals(getId(), that.getId()) && Objects.equals(getDatabase(),
        that.getDatabase());
  }
  @Override
  public int hashCode() {
    return Objects.hash(getDatabase());
  }
}
