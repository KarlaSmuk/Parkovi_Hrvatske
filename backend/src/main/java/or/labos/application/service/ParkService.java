package or.labos.application.service;



import or.labos.application.entity.ParkEntity;
import or.labos.application.repository.ParkRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ParkService {

    @Autowired
    private ParkRepository parkRepo;

    public List<ParkEntity> listAll(){
        return parkRepo.findAll();
    }

    public ParkEntity findByParkName(String parkName){
        return parkRepo.findByParkName(parkName);
    }


}
