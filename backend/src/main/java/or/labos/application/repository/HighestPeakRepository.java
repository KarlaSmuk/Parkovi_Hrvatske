package or.labos.application.repository;

import or.labos.application.entity.HighestPeakEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HighestPeakRepository extends JpaRepository<HighestPeakEntity, Integer> {

    Optional<HighestPeakEntity> findByPeakName(String peakName);
}
