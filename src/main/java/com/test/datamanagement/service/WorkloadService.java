package com.test.datamanagement.service;

import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import java.util.List;
import java.util.Optional;

public interface WorkloadService {
  List<Workload> findAllEntity();
  Optional<Workload> findById(Long id);
  Workload saveEntity(Workload workload);
  Workload findFirstByTestConfig(TestConfig testConfig);
  List<Workload> findAllByTestConfig(TestConfig testConfig);
  Workload updateEntity(Workload workload);
  void deleteEntity(Long id);
}
