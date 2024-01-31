package com.test.datamanagement.service.impl;
import com.test.datamanagement.entity.TestConfig;
import com.test.datamanagement.entity.Workload;
import com.test.datamanagement.repository.WorkloadRepository;
import com.test.datamanagement.service.WorkloadService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WorkloadServiceImpl implements WorkloadService {

  private final WorkloadRepository workloadRepository;
  public WorkloadServiceImpl(WorkloadRepository workloadRepository) {
    this.workloadRepository = workloadRepository;
  }
  @Override
  public List<Workload> findAllEntity() {
    return workloadRepository.findAll();
  }
  @Override
  public Optional<Workload> findById(Long id) {
    return workloadRepository.findById(id);
  }

  public Workload findFirstByTestConfig(TestConfig testConfig) {
    return workloadRepository.findFirstByTestConfig(testConfig);
  }
  @Override
  public Workload saveEntity(Workload workload) {
    Workload entity = findFirstByTestConfig(workload.getTestConfig());
    if (entity != null && entity.equals(workload)) {
      return entity;
    }
    return workloadRepository.save(workload);
  }
  @Override
  public Workload updateEntity(Workload workload) {
    return workloadRepository.save(workload);
  }
  @Override
  public void deleteEntity(Long id) {
    workloadRepository.deleteById(id);
  }

}
