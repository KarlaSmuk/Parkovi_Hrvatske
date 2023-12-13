package or.labos.application.repository;

import or.labos.application.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, Integer> {
    Optional<AnimalEntity> findByAnimalName(String animalName);

    @Query("SELECT MAX(a.animalID) FROM AnimalEntity a")
    Integer getMaxId();
}
