package or.labos.application.service;

import or.labos.application.entity.ParkEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

}
