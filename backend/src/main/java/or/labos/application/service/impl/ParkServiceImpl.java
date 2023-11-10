package or.labos.application.service.impl;

import or.labos.application.entity.ParkEntity;
import or.labos.application.repository.ParkRepository;
import or.labos.application.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private ParkRepository parkRepo;

    @Override
    public List<ParkEntity> listAll(){
        return parkRepo.findAll();
    }

    @Override
    public List<ParkEntity> findByParkNameIgnoreCase(String parkName){
        return parkRepo.findByParkNameIgnoreCase(parkName);
    }

    @Override
    public List<ParkEntity> findByYearOfFoundation(String value) {
        return parkRepo.findByYearOfFoundation(Integer.valueOf(value));
    }

    @Override
    public List<ParkEntity> findByTypeOfParkTypeOfParkNameIgnoreCase(String value) {
        return parkRepo.findByTypeOfParkTypeOfParkNameIgnoreCase(value);
    }

    @Override
    public List<ParkEntity> findByAreaEquals(String value) {
        return parkRepo.findByAreaEquals(Double.valueOf(value));
    }

    @Override
    public List<ParkEntity> findByPeakOfParkPeakNameIgnoreCase(String value) {
        return parkRepo.findByPeakOfParkPeakNameIgnoreCase(value);
    }

    @Override
    public List<ParkEntity> findByPeakOfParkPeakHeight(String value) {
        return parkRepo.findByPeakOfParkPeakHeight(Integer.valueOf(value));
    }

    @Override
    public List<ParkEntity> findByAtractionIgnoreCase(String value) {
        return parkRepo.findByAtractionIgnoreCase(value);
    }

    @Override
    public List<ParkEntity> findByEventIgnoreCase(String value) {
        return parkRepo.findByEventIgnoreCase(value);
    }


    @Override
    public List<ParkEntity> findAllByCountyIgnoreCase(String value) {
        return parkRepo.findAllByCountyIgnoreCase(value);
    }

    @Override
    public List<ParkEntity> findByParkAnimalsNameIgnoreCase(String value) {
        return parkRepo.findByParkAnimalsNameIgnoreCase(value);
    }

    @Override
    public List<ParkEntity> findByParkAnimalsSpeciesIgnoreCase(String value) {
        return parkRepo.findByParkAnimalsSpeciesIgnoreCase(value);
    }

    @Override
    public List<ParkEntity> findByAllAttributesWithoutPeak(String value) {
        return parkRepo.findByAllAttributesWithoutPeak(value);
    }
}
