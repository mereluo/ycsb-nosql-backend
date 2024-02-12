package com.test.datamanagement.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Getter @Setter
public class TimeSeriesData implements Serializable {
  private List<Integer> time;
  private List<Double> latency;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TimeSeriesData that = (TimeSeriesData) o;
    return Objects.equals(getTime(), that.getTime()) && Objects.equals(
        getLatency(), that.getLatency());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTime(), getLatency());
  }
}
