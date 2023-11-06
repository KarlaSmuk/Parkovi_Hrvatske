package or.labos.application.service;



import or.labos.application.entity.ParkEntity;
import or.labos.application.repository.ParkRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class ParkService {

    @Autowired
    private ParkRepository parkRepo;

    public List<ParkEntity> listAll(){
        return parkRepo.findAll();
    }

    public List<ParkEntity> findByParkNameIgnoreCase(String parkName){
        return parkRepo.findByParkNameIgnoreCase(parkName);
    }

    public List<ParkEntity> findByYearOfFoundation(String value) {
        return parkRepo.findByYearOfFoundation(Integer.valueOf(value));
    }

    public List<ParkEntity> findByTypeOfParkTypeOfParkNameIgnoreCase(String value) {
        return parkRepo.findByTypeOfParkTypeOfParkNameIgnoreCase(value);
    }

    public List<ParkEntity> findByAreaEquals(Double value) {
        return parkRepo.findByAreaEquals(value);
    }

    public List<ParkEntity> findByPeakOfParkPeakNameIgnoreCase(String value) {
        return parkRepo.findByPeakOfParkPeakNameIgnoreCase(value);
    }

    public List<ParkEntity> findByPeakOfParkPeakHeight(String value) {
        return parkRepo.findByPeakOfParkPeakHeight(Integer.valueOf(value));
    }

    public List<ParkEntity> findByAtractionIgnoreCase(String value) {
        return parkRepo.findByAtractionIgnoreCase(value);
    }

    public List<ParkEntity> findByEventIgnoreCase(String value) {
        return parkRepo.findByEventIgnoreCase(value);
    }

    public List<ParkEntity> findAllByCountyIgnoreCase(String value) {
        return parkRepo.findAllByCountyIgnoreCase(value);
    }

    public List<ParkEntity> findByParkAnimalsNameIgnoreCase(String value) {
        return parkRepo.findByParkAnimalsNameIgnoreCase(value);
    }

    public List<ParkEntity> findByParkAnimalsSpeciesIgnoreCase(String value) {
        return parkRepo.findByParkAnimalsSpeciesIgnoreCase(value);
    }

    public List<ParkEntity> findByAllAttributesWithoutPeak(String value) {
        return parkRepo.findByAllAttributesWithoutPeak(value);
    }

    public Collection<? extends ParkEntity> findByAllAttributesWithPeak(String value) {
        return parkRepo.findByAllAttributesWithPeak(value);
    }
}
