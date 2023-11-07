package or.labos.application.repository;

import or.labos.application.entity.ParkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkRepository extends JpaRepository<ParkEntity, Integer> {

    List<ParkEntity> findByParkNameIgnoreCase(String parkName);
    List<ParkEntity> findByYearOfFoundation(Integer yearOfFoundation);

    List<ParkEntity> findByTypeOfParkTypeOfParkNameIgnoreCase(String value);

    List<ParkEntity> findByAreaEquals(Double value);

    List<ParkEntity> findByPeakOfParkPeakNameIgnoreCase(String value);

    List<ParkEntity> findByPeakOfParkPeakHeight(Integer value);


    @Query("SELECT DISTINCT p FROM ParkEntity p JOIN FETCH p.parkCounties c WHERE UPPER(c.countyName) = UPPER(:value)")
    List<ParkEntity> findAllByCountyIgnoreCase(@Param("value") String value);

    List<ParkEntity> findByAtractionIgnoreCase(String value);

    List<ParkEntity> findByEventIgnoreCase(String value);

    @Query("SELECT p FROM ParkEntity p JOIN FETCH p.parkAnimals u WHERE UPPER(u.animalName) = upper(:value)")
    List<ParkEntity> findByParkAnimalsNameIgnoreCase(@Param("value")  String value);

    @Query("SELECT p FROM ParkEntity p JOIN FETCH p.parkAnimals u WHERE UPPER(u.speciesOfAnimal) = upper(:value)")
    List<ParkEntity> findByParkAnimalsSpeciesIgnoreCase(@Param("value")  String value);

    @Query("SELECT DISTINCT p FROM ParkEntity p " +
            "JOIN FETCH p.parkAnimals pa " +
            "JOIN FETCH p.parkCounties pc " +
            "LEFT JOIN p.peakOfPark pp " +
            "WHERE UPPER(p.parkName) = upper(:value) " +
            "OR UPPER(p.typeOfPark.typeOfParkName) = upper(:value) " +
            "OR CAST(p.yearOfFoundation as string ) = :value " +
            "OR CAST(p.area as string ) = :value " +
            "OR  UPPER(pp.peakName) = upper(:value) " +
            "OR  CAST(pp.peakHeight as string) = :value " +
            "OR UPPER(pc.countyName) = upper(:value) " +
            "OR UPPER(p.atraction) = upper(:value) " +
            "OR UPPER(p.event) = upper(:value) " +
            "OR UPPER(pa.animalName) = UPPER(:value) " +
            "OR UPPER(pa.speciesOfAnimal) = UPPER(:value) " )
    List<ParkEntity> findByAllAttributesWithoutPeak(String value);

}
