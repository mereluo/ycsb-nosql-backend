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
    if (entity.getWorkloadDescription() != null && !entity.getWorkloadDescription().isEmpty())
      criteria.and("workloadDescription").is(entity.getWorkloadDescription());

    if (entity.getConcurrencyLevel() != -1)
      criteria.and("testConfig.concurrencyLevel").is(entity.getConcurrencyLevel());
    if (entity.getRecordCounts() != -1)
      criteria.and("testConfig.recordCounts").is(entity.getRecordCounts());
    if (entity.getCommandLine() != null && !entity.getCommandLine().isEmpty())
      criteria.and("testConfig.commandLine").is(entity.getCommandLine());

    if (entity.getType() != null)
      criteria.and("dbConfig.type").is(entity.getType());
    if (entity.getPlatform() != null && !entity.getPlatform().isEmpty())
      criteria.and("dbConfig.platform").is(entity.getPlatform());
    if (entity.getNumOfNodes() != -1)
      criteria.and("dbConfig.numOfNodes").is(entity.getNumOfNodes());
    if (entity.getIsMultiRegional() != null)
      criteria.and("dbConfig.isMultiRegional").is(entity.getIsMultiRegional());
    if (entity.getIsCoLocated() != null)
      criteria.and("dbConfig.isCoLocated").is(entity.getIsCoLocated());
    if (entity.getLocationDetails() != null && !entity.getLocationDetails().isEmpty())
      criteria.and("dbConfig.locationDetails").is(entity.getLocationDetails());

    if (entity.getDatabase() != null && !entity.getDatabase().isEmpty())
      criteria.and("dbConfig.dbOption").is(entity.getDatabase());

    return criteria;
  }

  public Workload deleteById(String id) {
    Query workloadQuery = new Query(Criteria.where("_id").is(id));
    return mongoTemplate.findAndRemove(workloadQuery, Workload.class);
  }

}
