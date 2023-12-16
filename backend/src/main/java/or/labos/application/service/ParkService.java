package or.labos.application.service;

import or.labos.application.dto.requests.CreateParkRequest;
import or.labos.application.entity.AnimalEntity;
import or.labos.application.entity.CountyEntity;
import or.labos.application.entity.HighestPeakEntity;
import or.labos.application.entity.ParkEntity;

import java.util.List;
import java.util.Set;


public interface ParkService {

    List<ParkEntity> listAll();

    List<ParkEntity> findByParkNameIgnoreCase(String parkName);

    List<ParkEntity> findByYearOfFoundation(String value);

    List<ParkEntity> findByTypeOfParkTypeOfParkNameIgnoreCase(String value);

    List<ParkEntity> findByAreaEquals(String value);

    List<ParkEntity> findByPeakOfParkPeakNameIgnoreCase(String value);

    List<ParkEntity> findByPeakOfParkPeakHeight(String value);

    List<ParkEntity> findByAtractionIgnoreCase(String value);

    List<ParkEntity> findByEventIgnoreCase(String value);

    List<ParkEntity> findAllByCountyIgnoreCase(String value);

    List<ParkEntity> findByParkAnimalsNameIgnoreCase(String value);

    List<ParkEntity> findByParkAnimalsSpeciesIgnoreCase(String value);

    List<ParkEntity> findByAllAttributesWithoutPeak(String value);

    //rest

    ParkEntity getParkByID(Integer parkId);

    Set<AnimalEntity> getAnimalsByParkId(Integer parkId);

    Set<CountyEntity> getCountiesByParkId(Integer parkId);

    Boolean parkExists(String parkName);

    Boolean existsByID(Integer parkID);

    ParkEntity createPark(ParkEntity park, CreateParkRequest createParkRequest);

    void deleteById(Integer parkID);

    HighestPeakEntity getPeakByID(Integer parkID);
}
