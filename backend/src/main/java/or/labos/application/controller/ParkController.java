package or.labos.application.controller;

import or.labos.application.dto.ParkDto;
import or.labos.application.entity.AnimalEntity;
import or.labos.application.entity.CountyEntity;
import or.labos.application.entity.ParkEntity;
import or.labos.application.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/park")
public class ParkController {

    @Autowired
    private ParkService parkService;

    @GetMapping(value= "")
    public ResponseEntity<List<ParkDto>> listParks(){

        List<ParkEntity> parks = parkService.listAll();

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
                .body(listParksDto);
    }

    @GetMapping(value = "/one")
    public ResponseEntity<ParkDto> fetch(@RequestBody String parkName){

        ParkEntity park = parkService.fetch(parkName);

        ParkDto parkDto;

        if(!Objects.isNull(park.getPeakOfPark())) parkDto = new ParkDto(park.getParkName(), park.getTypeOfPark().getTypeOfParkName(), park.getYearOfFoundation(),park.getPeakOfPark().getPeakName(), park.getPeakOfPark().getPeakHeight(), park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),park.getAtraction(), park.getEvent(), Collections.singletonList(park.getParkAnimals().stream().collect(Collectors.toMap(AnimalEntity::getAnimalName, AnimalEntity::getSpeciesOfAnimal))));
        else parkDto = new ParkDto(park.getParkName(), park.getTypeOfPark().getTypeOfParkName(), park.getYearOfFoundation(), park.getParkCounties().stream().map(CountyEntity::getCountyName).toList(),park.getAtraction(), park.getEvent(), Collections.singletonList(park.getParkAnimals().stream().collect(Collectors.toMap(AnimalEntity::getAnimalName, AnimalEntity::getSpeciesOfAnimal))));


        if(parkName != null) return ResponseEntity.ok(parkDto);
        else return ResponseEntity.badRequest().build();
    }


}
