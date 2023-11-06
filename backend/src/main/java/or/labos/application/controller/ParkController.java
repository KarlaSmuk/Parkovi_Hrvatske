package or.labos.application.controller;

import or.labos.application.dto.ParkDto;
import or.labos.application.entity.ParkEntity;
import or.labos.application.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/")
public class ParkController {

    @Autowired
    private ParkService parkService;

    @CrossOrigin
    @GetMapping(value= "/parks")
    public ResponseEntity<List<ParkDto>> listParks(){

        List<ParkEntity> parks = parkService.listAll();


        List<ParkDto> parkDtos = new ArrayList<>();

        parks.forEach(park -> park.getParkAnimals().forEach(animalEntity -> park.getParkCounties().forEach(countyEntity -> {
            if (!Objects.isNull(park.getPeakOfPark())) {
                parkDtos.add(new ParkDto(
                        park.getParkName(),
                        park.getTypeOfPark().getTypeOfParkName(),
                        park.getYearOfFoundation(),
                        park.getArea(),
                        park.getPeakOfPark().getPeakName(),
                        park.getPeakOfPark().getPeakHeight(),
                        countyEntity.getCountyName(),
                        park.getAtraction(),
                        park.getEvent(),
                        animalEntity.getAnimalName(),
                        animalEntity.getSpeciesOfAnimal()
                ));
            } else {
                parkDtos.add(new ParkDto(
                        park.getParkName(),
                        park.getTypeOfPark().getTypeOfParkName(),
                        park.getYearOfFoundation(),
                        park.getArea(),
                        countyEntity.getCountyName(),
                        park.getAtraction(),
                        park.getEvent(),
                        animalEntity.getAnimalName(),
                        animalEntity.getSpeciesOfAnimal()
                ));
            }
        })));

        return ResponseEntity.ok().body(parkDtos);
    }

    @CrossOrigin
    @GetMapping(value = "/search/{attribute}/{value}")
    public ResponseEntity<List<ParkDto>> fetch(@PathVariable(value = "attribute") String attribute, @PathVariable(value = "value") String value){

        List<ParkEntity> parks = null;

        switch (attribute) {
            case "parkName":
                parks = parkService.findByParkNameIgnoreCase(value);
                break;
            case "typeOfPark":
                parks =  parkService.findByTypeOfParkTypeOfParkNameIgnoreCase(value);
                break;
            case "yearOfFoundation":
                parks =  parkService.findByYearOfFoundation(value);
                break;
            case "area":
                parks =  parkService.findByAreaEquals(Double.valueOf(value));
                break;
            case "peakName":
                parks =  parkService.findByPeakOfParkPeakNameIgnoreCase(value);
                break;
            case "peakHeight":
                parks =  parkService.findByPeakOfParkPeakHeight(value);
                break;
            case "countyName":
                parks =  parkService.findAllByCountyIgnoreCase(value);
                break;
            case "atraction":
                parks =  parkService.findByAtractionIgnoreCase(value);
                break;
            case "event":
                parks =  parkService.findByEventIgnoreCase(value);
                break;
            case "animalName":
                parks =  parkService.findByParkAnimalsNameIgnoreCase(value);
                break;
            case "speciesOfAnimal":
                parks =  parkService.findByParkAnimalsSpeciesIgnoreCase(value);
                break;
            case "wildcard" :
                parks = parkService.findByAllAttributesWithoutPeak(value);
                parks.addAll(parkService.findByAllAttributesWithPeak(value));
                break;
            default:
                break;
        }
        
        List<ParkDto> parkDtos = new ArrayList<>();

        parks.forEach(park -> park.getParkAnimals().forEach(animalEntity -> park.getParkCounties().forEach(countyEntity -> {
            if (!Objects.isNull(park.getPeakOfPark())) {
                parkDtos.add(new ParkDto(
                        park.getParkName(),
                        park.getTypeOfPark().getTypeOfParkName(),
                        park.getYearOfFoundation(),
                        park.getArea(),
                        park.getPeakOfPark().getPeakName(),
                        park.getPeakOfPark().getPeakHeight(),
                        countyEntity.getCountyName(),
                        park.getAtraction(),
                        park.getEvent(),
                        animalEntity.getAnimalName(),
                        animalEntity.getSpeciesOfAnimal()
                ));
            } else {
                parkDtos.add(new ParkDto(
                        park.getParkName(),
                        park.getTypeOfPark().getTypeOfParkName(),
                        park.getYearOfFoundation(),
                        park.getArea(),
                        countyEntity.getCountyName(),
                        park.getAtraction(),
                        park.getEvent(),
                        animalEntity.getAnimalName(),
                        animalEntity.getSpeciesOfAnimal()
                ));
            }
        })));

        return ResponseEntity.ok().body(parkDtos);

    }

    /*@CrossOrigin
    @PostMapping(value = "/{attribute}/{value}")
    public ResponseEntity<List<ParkDto>> fetch(@PathVariable(value = "attribute") String atribute, @PathVariable(value = "value") String value){

        System.out.println(atribute + " " + value);

        List<ParkEntity> parks =  parkService.findByAtribute(atribute, value);

        List<ParkDto> parkDtos = new ArrayList<>();

        parks.forEach(park -> {
            park.getParkAnimals().forEach(animalEntity -> {
                park.getParkCounties().forEach(countyEntity -> {
                    if (!Objects.isNull(park.getPeakOfPark())) {
                        parkDtos.add(new ParkDto(
                                park.getParkName(),
                                park.getTypeOfPark().getTypeOfParkName(),
                                park.getYearOfFoundation(),
                                park.getArea(),
                                park.getPeakOfPark().getPeakName(),
                                park.getPeakOfPark().getPeakHeight(),
                                countyEntity.getCountyName(),
                                park.getAtraction(),
                                park.getEvent(),
                                animalEntity.getAnimalName(),
                                animalEntity.getSpeciesOfAnimal()
                        ));
                    } else {
                        parkDtos.add(new ParkDto(
                                park.getParkName(),
                                park.getTypeOfPark().getTypeOfParkName(),
                                park.getYearOfFoundation(),
                                park.getArea(),
                                countyEntity.getCountyName(),
                                park.getAtraction(),
                                park.getEvent(),
                                animalEntity.getAnimalName(),
                                animalEntity.getSpeciesOfAnimal()
                        ));
                    }
                });
            });
        });

        return ResponseEntity.ok().body(parkDtos);

    }*/




}
