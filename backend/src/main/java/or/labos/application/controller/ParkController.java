package or.labos.application.controller;

import or.labos.application.dto.ParkDto;
import or.labos.application.entity.AnimalEntity;
import or.labos.application.entity.CountyEntity;
import or.labos.application.entity.ParkEntity;
import or.labos.application.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/park")
public class ParkController {

    @Autowired
    private ParkService parkService;

    @CrossOrigin
    @GetMapping(value= "")
    public ResponseEntity<List<ParkDto>> listParks(){

        /*List<ParkEntity> parks = parkService.listAll();


        List<ParkDto> listParksDto = parks.stream()
                .map(park -> {
                    if(!Objects.isNull(park.getPeakOfPark())){
                        return new ParkDto(park.getParkName(), park.getTypeOfPark().getTypeOfParkName(), park.getYearOfFoundation(),park.getPeakOfPark().getPeakName(), park.getPeakOfPark().getPeakHeight(), park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),park.getAtraction(), park.getEvent(), Collections.singletonList(park.getParkAnimals().stream().collect(Collectors.toMap(AnimalEntity::getAnimalName, AnimalEntity::getSpeciesOfAnimal))));

                    }else{
                        return new ParkDto(park.getParkName(), park.getTypeOfPark().getTypeOfParkName(), park.getYearOfFoundation(), park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),park.getAtraction(), park.getEvent(), Collections.singletonList(park.getParkAnimals().stream().collect(Collectors.toMap(AnimalEntity::getAnimalName, AnimalEntity::getSpeciesOfAnimal))));

                    }
                })
                .toList();

        return ResponseEntity
                .ok()
                .body(listParksDto);*/

        List<ParkEntity> parks = parkService.listAll();

        List<ParkDto> listParksDto = parks.stream()
                .map(park -> {

                    List<Map<String, String>> animals = park.getParkAnimals()
                            .stream()
                            .map(animal -> {
                                Map<String, String> animalMap = new TreeMap<>();
                                animalMap.put("name", animal.getAnimalName());
                                animalMap.put("species", animal.getSpeciesOfAnimal());
                                return animalMap;
                            })
                            .collect(Collectors.toList());

                    if (!Objects.isNull(park.getPeakOfPark())) {
                        return new ParkDto(
                                park.getParkName(),
                                park.getTypeOfPark().getTypeOfParkName(),
                                park.getYearOfFoundation(),
                                park.getArea(),
                                park.getPeakOfPark().getPeakName(),
                                park.getPeakOfPark().getPeakHeight(),
                                park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),
                                park.getAtraction(),
                                park.getEvent(),
                                animals
                        );
                    } else {
                        return new ParkDto(
                                park.getParkName(),
                                park.getTypeOfPark().getTypeOfParkName(),
                                park.getYearOfFoundation(),
                                park.getArea(),
                                park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),
                                park.getAtraction(),
                                park.getEvent(),
                                animals
                        );
                    }
                })
                .toList();

        return ResponseEntity.ok().body(listParksDto);
    }

    @CrossOrigin
    @GetMapping(value = "/{parkName}")
    public ResponseEntity<ParkDto> fetch(@PathVariable(value = "parkName") String parkName){

        ParkEntity park = parkService.findByParkName(parkName);

        ParkDto parkDto;

        if(!Objects.isNull(park.getPeakOfPark())) parkDto = new ParkDto(park.getParkName(), park.getTypeOfPark().getTypeOfParkName(), park.getYearOfFoundation(), park.getArea(), park.getPeakOfPark().getPeakName(), park.getPeakOfPark().getPeakHeight(), park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),park.getAtraction(), park.getEvent(), Collections.singletonList(park.getParkAnimals().stream().collect(Collectors.toMap(AnimalEntity::getAnimalName, AnimalEntity::getSpeciesOfAnimal))));
        else parkDto = new ParkDto(park.getParkName(), park.getTypeOfPark().getTypeOfParkName(), park.getYearOfFoundation(), park.getArea(), park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),park.getAtraction(), park.getEvent(), Collections.singletonList(park.getParkAnimals().stream().collect(Collectors.toMap(AnimalEntity::getAnimalName, AnimalEntity::getSpeciesOfAnimal))));


        if(parkName != null) return ResponseEntity.ok(parkDto);
        else return ResponseEntity.badRequest().build();
    }


}
