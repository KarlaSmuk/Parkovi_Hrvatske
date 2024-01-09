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

    @Query("SELECT p FROM ParkEntity p " +
            "JOIN p.parkCounties c " +
            "WHERE UPPER(c.countyName) LIKE CONCAT('%', UPPER(:value), '%')")
    List<ParkEntity> findAllByCountyIgnoreCase(@Param("value") String value);

    List<ParkEntity> findByAtractionIgnoreCase(String value);

    List<ParkEntity> findByEventIgnoreCase(String value);

    @Query("SELECT p FROM ParkEntity p " +
            "JOIN p.parkAnimals u " +
            "WHERE UPPER(u.animalName) LIKE CONCAT('%', UPPER(:value), '%')")
    List<ParkEntity> findByParkAnimalsNameIgnoreCase(@Param("value")  String value);

    @Query("SELECT p FROM ParkEntity p " +
            "JOIN p.parkAnimals u " +
            "WHERE UPPER(u.speciesOfAnimal) LIKE CONCAT('%', UPPER(:value), '%')")
    List<ParkEntity> findByParkAnimalsSpeciesIgnoreCase(@Param("value")  String value);

    @Query("SELECT DISTINCT p FROM ParkEntity p " +
            "JOIN p.parkAnimals pa " +
            "JOIN p.parkCounties pc " +
            "LEFT JOIN p.peakOfPark pp " +
            "WHERE UPPER(p.parkName) LIKE CONCAT('%', UPPER(:value), '%') " +
            "OR UPPER(p.typeOfPark.typeOfParkName) LIKE CONCAT('%', UPPER(:value), '%') " +
            "OR CAST(p.yearOfFoundation as string ) LIKE CONCAT('%', :value, '%') " +
            "OR CAST(p.area as string ) LIKE CONCAT('%', :value, '%') " +
            "OR UPPER(pp.peakName) LIKE CONCAT('%', UPPER(:value), '%') " +
            "OR CAST(pp.peakHeight as string) LIKE CONCAT('%', :value, '%') " +
            "OR UPPER(pc.countyName) LIKE CONCAT('%', UPPER(:value), '%') " +
            "OR UPPER(p.atraction) LIKE CONCAT('%', UPPER(:value), '%') " +
            "OR UPPER(p.event) LIKE CONCAT('%', UPPER(:value), '%') " +
            "OR UPPER(pa.animalName) LIKE CONCAT('%', UPPER(:value), '%') " +
            "OR UPPER(pa.speciesOfAnimal) LIKE CONCAT('%', UPPER(:value), '%') ")
    List<ParkEntity> findByAllAttributesWithoutPeak(String value);

    Boolean existsByParkName(String parkName);

    Boolean existsByParkID(Integer parkID);

}
