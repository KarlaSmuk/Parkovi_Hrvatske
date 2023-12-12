package or.labos.application.service;


import or.labos.application.entity.CountyEntity;

import java.util.List;
import java.util.Optional;

public interface CountyService {

    List<CountyEntity> listAll();

    Optional<CountyEntity> findByCountyID(Integer countyID);


}
