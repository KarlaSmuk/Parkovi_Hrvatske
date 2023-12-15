package or.labos.application.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import or.labos.application.dto.AnimalDto;
import or.labos.application.dto.CountyDto;
import or.labos.application.dto.requests.CreateParkRequest;
import or.labos.application.entity.*;
import or.labos.application.repository.*;
import or.labos.application.service.ParkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private ParkRepository parkRepo;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CountyRepository countyRepository;

    @Autowired
    private TypeOfParkRepository typeOfParkRepository;

    @Autowired
    private HighestPeakRepository highestPeakRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private Random random = new Random();

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

    @Override
    public ParkEntity getParkByID(Integer parkId) {
        return parkRepo.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park not found with ID: " + parkId));
    }

    public Set<AnimalEntity> getAnimalsByParkId(Integer parkId) {
        ParkEntity park = parkRepo.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park not found with ID: " + parkId));
        return park.getParkAnimals();
    }

    @Override
    public Set<CountyEntity> getCountiesByParkId(Integer parkId) {
        ParkEntity park = parkRepo.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park not found with ID: " + parkId));
        return park.getParkCounties();
    }

    @Override
    public Boolean parkExists(String parkName) {
        return parkRepo.existsByParkName(parkName);
    }

    @Override
    public Boolean existsByID(Integer parkID) {
        return parkRepo.existsByParkID(parkID);
    }

    @Override
    @Transactional
    public ParkEntity createPark(CreateParkRequest createParkRequest) {

        //to entity
        ParkEntity parkEntity = new ParkEntity();

        parkEntity.setParkID(parkRepo.getMaxId() + 1);
        parkEntity.setParkName(createParkRequest.getParkName());
        parkEntity.setYearOfFoundation(createParkRequest.getYearOfFoundation());
        parkEntity.setArea(createParkRequest.getArea());
        parkEntity.setAtraction(createParkRequest.getAtraction());
        parkEntity.setEvent(createParkRequest.getEvent());

        //type - add exisiting
        TypeOfParkEntity typeOfPark = typeOfParkRepository.findByTypeOfParkName(createParkRequest.getTypeOfPark());
        parkEntity.setTypeOfPark(typeOfPark);

        // animals - add exisiting or create new one
        Set<AnimalEntity> animalEntities = new HashSet<>();
        for (AnimalDto animalDto : createParkRequest.getAnimals()) {
            AnimalEntity animal = animalRepository.findByAnimalName(animalDto.getAnimalName())
                    .orElseGet(() -> {
                        AnimalEntity newAnimal = new AnimalEntity();
                        newAnimal.setAnimalID(animalRepository.getMaxId() + 1);
                        newAnimal.setAnimalName(animalDto.getAnimalName());
                        newAnimal.setSpeciesOfAnimal(animalDto.getSpeciesOfAnimal());
                        return animalRepository.save(newAnimal);
                    });
            animal.getHasAnimal().add(parkEntity);
            animalEntities.add(animal);
        }

        parkEntity.setParkAnimals(animalEntities);

        // counties - add exisiting
        Set<CountyEntity> countyEntities = new HashSet<>();
        for (CountyDto countyDto : createParkRequest.getCounties()) {
            CountyEntity county = countyRepository.findByCountyName(countyDto.getCountyName())
                    .orElseThrow(() -> new EntityNotFoundException("County not found: " + countyDto.getCountyName()));
            countyEntities.add(county);
        }

        parkEntity.setParkCounties(countyEntities);

        // peaks - add exisiting and create new one
        HighestPeakEntity peak = highestPeakRepository.findByPeakName(createParkRequest.getPeak().getPeakName())
                .orElseGet(() -> {
                    HighestPeakEntity newPeak = new HighestPeakEntity();
                    newPeak.setPeakID(highestPeakRepository.getMaxId() + 1);
                    newPeak.setPeakName(createParkRequest.getPeak().getPeakName());
                    newPeak.setPeakHeight(createParkRequest.getPeak().getPeakHeight());
                    return highestPeakRepository.save(newPeak);
                });
        parkEntity.setPeakOfPark(peak);

        return parkRepo.save(parkEntity);
    }

    @Override
    @Transactional
    public ParkEntity updatePark(Integer parkID, CreateParkRequest createParkRequest){

        ParkEntity park = parkRepo.findByParkID(parkID);

                    park.setParkName(createParkRequest.getParkName());
                    TypeOfParkEntity typeOfPark = typeOfParkRepository.findByTypeOfParkName(createParkRequest.getTypeOfPark());
                    park.setTypeOfPark(typeOfPark);
                    park.setYearOfFoundation(createParkRequest.getYearOfFoundation());
                    park.setArea(createParkRequest.getArea());
                    HighestPeakEntity peak = highestPeakRepository.findByPeakName(createParkRequest.getPeak().getPeakName())
                            .orElseGet(() -> {
                                HighestPeakEntity newPeak = new HighestPeakEntity();
                                newPeak.setPeakID(highestPeakRepository.getMaxId() + 1);
                                newPeak.setPeakName(createParkRequest.getPeak().getPeakName());
                                newPeak.setPeakHeight(createParkRequest.getPeak().getPeakHeight());
                                return highestPeakRepository.save(newPeak);
                            });
                    park.setPeakOfPark(peak);
                    Set<CountyEntity> countyEntities = new HashSet<>();
                    for (CountyDto countyDto : createParkRequest.getCounties()) {
                        CountyEntity county = countyRepository.findByCountyName(countyDto.getCountyName())
                                .orElseThrow(() -> new EntityNotFoundException("County not found: " + countyDto.getCountyName()));
                        countyEntities.add(county);
                    }
                    park.setParkCounties(countyEntities);
                    park.setParkCounties(countyEntities);
                    park.setAtraction(createParkRequest.getAtraction());
                    park.setEvent(createParkRequest.getEvent());
                    Set<AnimalEntity> animalEntities = new HashSet<>();
                    for (AnimalDto animalDto : createParkRequest.getAnimals()) {
                        AnimalEntity animal = animalRepository.findByAnimalName(animalDto.getAnimalName())
                                .orElseGet(() -> {
                                    AnimalEntity newAnimal = new AnimalEntity();
                                    newAnimal.setAnimalID(animalRepository.getMaxId() + 1);
                                    newAnimal.setAnimalName(animalDto.getAnimalName());
                                    newAnimal.setSpeciesOfAnimal(animalDto.getSpeciesOfAnimal());
                                    return animalRepository.save(newAnimal);
                                });
                        animal.getHasAnimal().add(park);
                        animalEntities.add(animal);
                    }
                    return parkRepo.save(park);
    }

    @Override
    @Transactional
    public void deleteById(Integer parkID){
        ParkEntity parkEntity = parkRepo.findByParkID(parkID);

        parkRepo.delete(parkEntity);
    }
}
