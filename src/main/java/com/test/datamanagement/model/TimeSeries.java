package com.test.datamanagement.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimeSeries implements Serializable {
  Map<String, TimeSeriesData> data;

  public TimeSeries() {
    this.data = new HashMap<>();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeSeries that = (TimeSeries) o;
    return Objects.equals(getData(), that.getData());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getData());
  }
}