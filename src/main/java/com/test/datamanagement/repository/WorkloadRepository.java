package com.test.datamanagement.repository;

import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import com.test.datamanagement.model.RequestWorkload;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class WorkloadRepository implements WorkloadRepositoryTemplate {

  @Autowired
  MongoTemplate mongoTemplate;

  public Workload save(Workload workload) {
    return mongoTemplate.save(workload);
  }
  public List<Workload> findAllByDatabase(RequestWorkload reqObject) {
    LookupOperation lookTestConfig = LookupOperation.newLookup()
        .from("test_config").localField("testConfig")
        .foreignField("_id").as("testConfig");

    LookupOperation lookDBConfig = LookupOperation.newLookup()
        .from("db_config").localField("testConfig.dbConfig")
        .foreignField("_id").as("dbConfig");

    Aggregation aggregation = Aggregation.newAggregation(
        lookTestConfig,
        Aggregation.unwind("testConfig"),
        lookDBConfig,
        Aggregation.unwind("dbConfig"),
        Aggregation.match(createCriteria(reqObject))
    );
    return mongoTemplate.aggregate(aggregation, "workload", Workload.class).getMappedResults();
  }

  private Criteria createCriteria(RequestWorkload reqObject) {
    Criteria criteria = new Criteria();
//
//    if (reqObject.getWorkloadType() != null)
//      criteria.and("workloadType").is(reqObject.getWorkloadType());
    if (reqObject.getUpdateType() != null)
      criteria.and("updateType").is(reqObject.getUpdateType());

//    if (reqObject.getConcurrencyLevel() == -1)
//      criteria.and("testConfig.concurrencyLevel").is(reqObject.getRecordCounts());

    System.out.println(reqObject.isCoLocated());
    criteria.and("dbConfig.isCoLocated").is(reqObject.isCoLocated());

    if (reqObject.getDatabase() != null) {
      criteria.and("dbConfig.dbOption").is(reqObject.getDatabase());
    }

    // Add more criteria for other fields as needed

    return criteria;
  }


}
