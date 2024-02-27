package com.test.datamanagement.repository;

import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import com.test.datamanagement.model.RequestWorkload;
import java.util.List;

public interface WorkloadRepositoryTemplate {
  List<Workload> findAllByDatabase(RequestWorkload reqObject);
  Workload save(Workload workload);
}
