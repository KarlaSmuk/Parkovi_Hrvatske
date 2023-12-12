package or.labos.application.service.impl;

import or.labos.application.entity.CountyEntity;
import or.labos.application.repository.CountyRepository;
import or.labos.application.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountyServiceImpl implements CountyService {

    @Autowired
    private CountyRepository countyRepository;

    @Override
    public List<CountyEntity> listAll() {
        return countyRepository.findAll();
    }

    @Override
    public Optional<CountyEntity> findByCountyID(Integer countyID) {
        return countyRepository.findById(countyID);
    }


}
