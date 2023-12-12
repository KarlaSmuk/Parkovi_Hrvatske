package or.labos.application.service;

import or.labos.application.entity.AnimalEntity;

import java.util.List;
import java.util.Optional;

public interface AnimalService {

    List<AnimalEntity> listAll();

    Optional<AnimalEntity> findByAnimalID(Integer peakID);

}
