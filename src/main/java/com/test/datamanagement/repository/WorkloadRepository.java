package com.test.datamanagement.repository;

import com.test.datamanagement.entity.DBConfig;
import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import com.test.datamanagement.model.CompleteWorkload;
import com.test.datamanagement.model.RequestWorkload;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class WorkloadRepository implements WorkloadRepositoryTemplate {

  @Autowired
  MongoTemplate mongoTemplate;

  public Workload save(Workload workload) {
    return mongoTemplate.save(workload);
  }
  public List<Workload> findAllByTestConfig(TestConfig testConfig) {
    Criteria criteria = Criteria.where("testConfig").is(testConfig);
    Query query = new Query(criteria);
    return mongoTemplate.find(query, Workload.class);
  }
  public List<CompleteWorkload> findAllByProperties(RequestWorkload reqObject) {
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

    AggregationResults <Workload> results = mongoTemplate.aggregate(aggregation, "workload", Workload.class);

    List<Document> raw = results.getRawResults().getList("results", Document.class);
    List<Workload> mapped = results.getMappedResults();

    List<CompleteWorkload> res = new ArrayList<>();
    for (int i = 0; i < mapped.size(); i++) {
      CompleteWorkload curr = new CompleteWorkload(mapped.get(i), raw.get(i));
      res.add(curr);
    }
    return res;
  }
  private Criteria createCriteria(RequestWorkload entity) {
    Criteria criteria = new Criteria();

    if (entity.getWorkloadType() != null)
      criteria.and("workloadType").is(entity.getWorkloadType());
    if (entity.getUpdateType() != null)
      criteria.and("updateType").is(entity.getUpdateType());

    if (entity.getConcurrencyLevel() != -1)
      criteria.and("testConfig.concurrencyLevel").is(entity.getRecordCounts());
    if (entity.getRecordCounts() != -1)
      criteria.and("testConfig.recordCounts").is(entity.getRecordCounts());
    if (entity.getCommandLine() != null)
      criteria.and("testConfig.commandLine").is(entity.getCommandLine());

    if (entity.getType() != null)
      criteria.and("dbConfig.type").is(entity.getType());
    if (entity.getPlatform() != null)
      criteria.and("dbConfig.platform").is(entity.getPlatform());
    if (entity.getNumOfNodes() != -1)
      criteria.and("dbConfig.numOfNodes").is(entity.getNumOfNodes());
    if (entity.getIsMultiRegional() != null)
      criteria.and("dbConfig.isMultiRegional").is(entity.getIsMultiRegional());
    if (entity.getIsCoLocated() != null)
      criteria.and("dbConfig.isCoLocated").is(entity.getIsCoLocated());

    if (entity.getDatabase() != null)
      criteria.and("dbConfig.dbOption").is(entity.getDatabase());

    return criteria;
  }

  public void deleteById(String id) {
    Query workloadQuery = new Query(Criteria.where("_id").is(id));
    Workload deletedWorkload = mongoTemplate.findAndRemove(workloadQuery, Workload.class);

    if (deletedWorkload != null) {
      // Check if the associated TestConfig is exclusively associated with this Workload
      if (deletedWorkload.getTestConfig() != null &&
          isTestConfigExclusive(deletedWorkload.getTestConfig().getId())) {
        Query testConfigQuery = new Query(Criteria.where("_id").is(deletedWorkload.getTestConfig().getId()));
        mongoTemplate.remove(testConfigQuery, TestConfig.class);
      }

      // Check if the associated DBConfig is exclusively associated with this TestConfig
      if (deletedWorkload.getTestConfig() != null &&
          deletedWorkload.getTestConfig().getDbConfig() != null &&
          isDbConfigExclusive(deletedWorkload.getTestConfig().getDbConfig().getId())) {
        Query dbConfigQuery = new Query(Criteria.where("_id").is(deletedWorkload.getTestConfig().getDbConfig().getId()));
        mongoTemplate.remove(dbConfigQuery, DBConfig.class);
      }
    }
  }

  private boolean isTestConfigExclusive(String testConfigId) {
    Query query = new Query(Criteria.where("testConfig.id").is(testConfigId));
    long count = mongoTemplate.count(query, Workload.class);

    // If count is 1, it means the TestConfig is exclusively associated with a single Workload
    return count == 0;
  }

  private boolean isDbConfigExclusive(String dbConfigId) {
    Query query = new Query(Criteria.where("dbConfig.id").is(dbConfigId));
    long count = mongoTemplate.count(query, TestConfig.class);

    // If count is 1, it means the DBConfig is exclusively associated with a single TestConfig
    return count == 0;
  }

}
