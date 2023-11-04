package or.labos.application.repository;

import or.labos.application.entity.ParkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkRepository extends JpaRepository<ParkEntity, Integer> {
    ParkEntity findByParkName(String parkName);
}
