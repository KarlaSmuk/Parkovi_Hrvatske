package or.labos.application.service.impl;

import or.labos.application.entity.AnimalEntity;
import or.labos.application.repository.AnimalRepository;
import or.labos.application.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public List<AnimalEntity> listAll() {
        return animalRepository.findAll();
    }

    public Optional<AnimalEntity> findByAnimalID(Integer animalID) {
        return animalRepository.findById(animalID);
    }

}
