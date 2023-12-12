package or.labos.application.repository;

import or.labos.application.entity.CountyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountyRepository extends JpaRepository<CountyEntity, Integer> {
    Optional<CountyEntity> findByCountyName(String countyName);
}
