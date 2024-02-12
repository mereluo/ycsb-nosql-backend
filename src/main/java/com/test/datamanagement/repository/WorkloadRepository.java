package com.test.datamanagement.repository;

import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkloadRepository extends MongoRepository<Workload, Long> {
//  Workload findFirstByTestConfig(TestConfig testConfig);
  List<Workload> findAllByTestConfig(TestConfig testConfig);
}
