package or.labos.application.controller;

import or.labos.application.dto.ParkDto;
import or.labos.application.entity.AnimalEntity;
import or.labos.application.entity.CountyEntity;
import or.labos.application.entity.ParkEntity;
import or.labos.application.service.ParkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/")
public class ParkController {

    @Autowired
    private ParkService parkService;

    private final ModelMapper modelMapper = new ModelMapper();

    @CrossOrigin
    @GetMapping(value= "/parks")
    public ResponseEntity<List<ParkDto>> listParks(){

        List<ParkEntity> parks = parkService.listAll();

        return getListResponseEntity(parks, new ArrayList<>());
    }

    @CrossOrigin
    @GetMapping(value = "/search/{attribute}/{value}")
    public ResponseEntity<List<ParkDto>> fetch(@PathVariable(value = "attribute") String attribute, @PathVariable(value = "value") String value){

        List<ParkEntity> parks = parkService.findByAttribute(attribute, value);

        assert parks != null;
        return getListResponseEntity(parks, new ArrayList<>());

    }

    private ResponseEntity<List<ParkDto>> getListResponseEntity(List<ParkEntity> parks, List<ParkDto> parkDtos) {
        parks.forEach(park -> {
            if (park.getPeakOfPark() != null) {
                park.getParkAnimals().forEach(animalEntity ->
                        park.getParkCounties().forEach(countyEntity -> {
                            ParkDto parkDto = modelMapper.map(park, ParkDto.class);
                            parkDto.setPeakName(park.getPeakOfPark().getPeakName());
                            parkDto.setPeakHeight(park.getPeakOfPark().getPeakHeight());
                            parkDto.setAnimal(animalEntity.getAnimalName());
                            parkDto.setSpecies(animalEntity.getSpeciesOfAnimal());
                            parkDto.setCounty(countyEntity.getCountyName());
                            parkDtos.add(parkDto);
                        })
                );
            } else {
                park.getParkAnimals().forEach(animalEntity ->
                        park.getParkCounties().forEach(countyEntity -> {
                            ParkDto parkDto = modelMapper.map(park, ParkDto.class);
                            parkDto.setAnimal(animalEntity.getAnimalName());
                            parkDto.setSpecies(animalEntity.getSpeciesOfAnimal());
                            parkDto.setCounty(countyEntity.getCountyName());
                            parkDtos.add(parkDto);
                        })
                );
            }
        });

        return ResponseEntity.ok().body(parkDtos);
    }

}
