package in.anirudhjwala.module2RestApi.repositories;

import in.anirudhjwala.module2RestApi.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> { }
