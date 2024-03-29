package or.labos.application.service.impl;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import or.labos.application.dto.AnimalDto;
import or.labos.application.dto.CountyDto;
import or.labos.application.dto.requests.CreateParkRequest;
import or.labos.application.entity.*;
import or.labos.application.repository.*;
import or.labos.application.service.ParkService;
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

    @Override
    public List<ParkEntity> listAll() {
        return parkRepo.findAll();
    }

    @Override
    public ParkEntity getParkByID(Integer parkId) {
        return parkRepo.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park not found"));
    }

    public Set<AnimalEntity> getAnimalsByParkId(Integer parkId) {
        ParkEntity park = parkRepo.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park not found"));
        return park.getParkAnimals();
    }

    @Override
    public Set<CountyEntity> getCountiesByParkId(Integer parkId) {
        ParkEntity park = parkRepo.findById(parkId)
                .orElseThrow(() -> new EntityNotFoundException("Park not found"));
        return park.getParkCounties();
    }

    @Override
    public HighestPeakEntity getPeakByID(Integer parkID) {

        ParkEntity park = parkRepo.findById(parkID)
                .orElseThrow(() -> new EntityNotFoundException("Peak not found"));
        return park.getPeakOfPark();
    }

    @Override
    public List<ParkEntity> findByAttribute(String attribute, String value) {
        return switch (attribute) {
            case "parkName" -> parkRepo.findByParkNameIgnoreCaseContains(value);
            case "typeOfPark" -> parkRepo.findByTypeOfParkTypeOfParkNameIgnoreCaseContains(value);
            case "yearOfFoundation" -> parkRepo.findByYearOfFoundation(Integer.valueOf(value));
            case "area" -> parkRepo.findByAreaEquals(Double.valueOf(value));
            case "peakName" -> parkRepo.findByPeakOfParkPeakNameIgnoreCaseContains(value);
            case "peakHeight" -> parkRepo.findByPeakOfParkPeakHeight(Integer.valueOf(value));
            case "countyName" -> parkRepo.findAllByCountyIgnoreCase(value);
            case "atraction" -> parkRepo.findByAtractionIgnoreCaseContains(value);
            case "event" -> parkRepo.findByEventIgnoreCaseContains(value);
            case "animalName" -> parkRepo.findByParkAnimalsNameIgnoreCase(value);
            case "speciesOfAnimal" -> parkRepo.findByParkAnimalsSpeciesIgnoreCase(value);
            case "wildcard" -> parkRepo.findByAllAttributes(value);
            default -> null;
        };
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
    public ParkEntity createPark(ParkEntity parkEntity, CreateParkRequest createParkRequest) {

        parkEntity.setParkName(createParkRequest.getParkName());
        parkEntity.setYearOfFoundation(createParkRequest.getYearOfFoundation());
        parkEntity.setArea(createParkRequest.getArea());
        parkEntity.setAtraction(createParkRequest.getAtraction());
        parkEntity.setEvent(createParkRequest.getEvent());

        //type - add exisiting
        TypeOfParkEntity typeOfPark = typeOfParkRepository.findByTypeOfParkName(createParkRequest.getTypeOfParkName());
        parkEntity.setTypeOfPark(typeOfPark);

        // animals - add exisiting or create new one
        Set<AnimalEntity> animalEntities = new HashSet<>();
        for (AnimalDto animalDto : createParkRequest.getAnimals()) {
            AnimalEntity animal = animalRepository.findByAnimalName(animalDto.getAnimalName())
                    .orElseGet(() -> {
                        AnimalEntity newAnimal = new AnimalEntity();
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
                    .orElseThrow(() -> new EntityNotFoundException("County not found"));
            countyEntities.add(county);
        }

        parkEntity.setParkCounties(countyEntities);

        // peaks - create new one
        HighestPeakEntity peak = highestPeakRepository.findByPeakName(createParkRequest.getPeak().getPeakName())
                .map(existingPeak -> {

                    //update
                    if(parkEntity.getParkID() != null){
                       if(!existingPeak.getPark().getParkID().equals(parkEntity.getParkID())){
                            throw new IllegalStateException("Peak already assigned to another park");
                        }
                    }else{ // create
                        if (existingPeak.getPeakName().equals(createParkRequest.getPeak().getPeakName())) {
                            throw new IllegalStateException("Peak already assigned to another park");
                        }
                    }

                    return existingPeak;
                })
                .orElseGet(() -> {
                    HighestPeakEntity newPeak = new HighestPeakEntity();
                    newPeak.setPeakName(createParkRequest.getPeak().getPeakName());
                    newPeak.setPeakHeight(createParkRequest.getPeak().getPeakHeight());
                    return highestPeakRepository.save(newPeak);
                });
        parkEntity.setPeakOfPark(peak);

        return parkRepo.save(parkEntity);
    }


    @Override
    @Transactional
    public void deleteById(Integer parkID) {

        ParkEntity parkEntity = parkRepo.findById(parkID)
                .orElseThrow(() -> new EntityNotFoundException("Park not found"));

        parkRepo.delete(parkEntity);
    }
}
