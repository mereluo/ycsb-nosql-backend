package com.test.datamanagement.service.impl;
import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import com.test.datamanagement.model.CompleteWorkload;
import com.test.datamanagement.model.RequestWorkload;
import com.test.datamanagement.repository.WorkloadRepository;
import com.test.datamanagement.service.WorkloadService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WorkloadServiceImpl implements WorkloadService {

  private final WorkloadRepository workloadRepository;
  public WorkloadServiceImpl(WorkloadRepository workloadRepository) {
    this.workloadRepository = workloadRepository;
  }
//  @Override
//  public List<Workload> findAllEntity() {
//    return workloadRepository.findAll();
//  }
//  @Override
//  public Optional<Workload> findById(Long id) {
//    return workloadRepository.findById(id);
//  }
  public List<Workload> findAllByTestConfig(TestConfig testConfig) {
    return workloadRepository.findAllByTestConfig(testConfig);
  }
  public List<CompleteWorkload> findAllByProperties(RequestWorkload reqObject) {
    return workloadRepository.findAllByProperties(reqObject);
  }
  @Override
  public Workload saveEntity(Workload workload) {
    List<Workload> entity = findAllByTestConfig(workload.getTestConfig());
    if (!entity.isEmpty()) {
      for (Workload load : entity) {
        if (load.equals(workload)) return load;
      }
    }
    return workloadRepository.save(workload);
  }
//  @Override
//  public Workload updateEntity(Workload workload) {
//    return workloadRepository.save(workload);
//  }
  @Override
  public Workload deleteById(String id) {
    return workloadRepository.deleteById(id);
  }

}
