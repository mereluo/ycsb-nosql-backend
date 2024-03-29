package com.test.datamanagement.service;

import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import com.test.datamanagement.model.CompleteWorkload;
import com.test.datamanagement.model.RequestWorkload;
import java.util.List;
import java.util.Optional;

public interface WorkloadService {
//  List<Workload> findAllEntity();
//  Optional<Workload> findById(Long id);
  Workload saveEntity(Workload workload);
  List<Workload> findAllByTestConfig(TestConfig testConfig);
  List<CompleteWorkload> findAllByProperties(RequestWorkload reqObject);
//  Workload updateEntity(Workload workload);
  Workload deleteById(String id);
}
