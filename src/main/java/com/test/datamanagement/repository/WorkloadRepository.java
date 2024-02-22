package com.test.datamanagement.repository;

import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkloadRepository extends MongoRepository<Workload, Long> {
  List<Workload> findAllByTestConfig(TestConfig testConfig);

//  @Aggregation({
//      "{$lookup: { from: 'test_config', localField: 'testConfig', foreignField: '_id', as: 'testConfig' }}",
//      "{$unwind: '$testConfig'}",
//      "{$lookup: { from: 'db_config', localField: 'testConfig.dbConfig', foreignField: '_id', as: 'dbConfig' }}",
//      "{$unwind: '$dbConfig'}",
//      "{$lookup: { from: 'database_option', localField: 'dbConfig.dbOption', foreignField: '_id', as: 'databaseOption' }}",
//      "{$unwind: '$databaseOption'}",
//      "{$match: { 'databaseOption.database': ?0 }}"
//  })
  @Aggregation({
      "{$lookup: { from: 'test_config', localField: 'testConfig', foreignField: '_id', as: 'testConfig' }}",
      "{$unwind: '$testConfig'}",
      "{$lookup: { from: 'db_config', localField: 'testConfig.dbConfig', foreignField: '_id', as: 'dbConfig' }}",
      "{$unwind: '$dbConfig'}",
      "{$match: { 'dbConfig.dbOption': ?0 }}"
  })
  List<Workload> findAllByDatabase(String database);
}
