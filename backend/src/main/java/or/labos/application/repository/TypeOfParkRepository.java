package or.labos.application.repository;

import or.labos.application.entity.TypeOfParkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfParkRepository extends JpaRepository<TypeOfParkEntity, Integer> {

    TypeOfParkEntity findByTypeOfParkName(String name);
}
