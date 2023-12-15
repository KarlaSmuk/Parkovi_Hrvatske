package or.labos.application.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import or.labos.application.dto.*;
import or.labos.application.dto.requests.CreateParkRequest;
import or.labos.application.dto.requests.CreateParkResponse;
import or.labos.application.entity.AnimalEntity;
import or.labos.application.entity.CountyEntity;
import or.labos.application.entity.ParkEntity;
import or.labos.application.service.ParkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/parks")
public class ParkRestController {

    @Autowired
    private ParkService parkService;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("")
    public ResponseEntity<?> listAllParks() {
        List<ParkEntity> parks = parkService.listAll();

        if(parks.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<ParkResponseDto> parkResponseDtos = parks.stream()
                .map(park -> modelMapper.map(park, ParkResponseDto.class))
                .collect(Collectors.toList());

        parkResponseDtos.forEach(parkResponseDto -> {
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkResponseDto.getParkId())).withRel("counties").withType("GET"));
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkResponseDto.getParkId())).withRel("animals").withType("GET"));

        });

        ResponseDto<Object> responseDtos = new ResponseDto<>();
        responseDtos.setStatus(HttpStatus.OK);
        responseDtos.setMessage("Fetched all parks");
        responseDtos.setResponse(parkResponseDtos);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{parkId}")
    public ResponseEntity<?> getParkByID(@PathVariable Integer parkId) {

        Optional<ParkEntity> parkOptional = Optional.ofNullable(parkService.getParkByID(parkId));

        if (parkOptional.isEmpty()) {
            throw new EntityNotFoundException("Park not found with ID: " + parkId);
        }

        ParkEntity park = parkOptional.get();

        ParkResponseDto parkResponseDto = modelMapper.map(park, ParkResponseDto.class);
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withSelfRel());
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkId)).withRel("counties").withType("GET"));
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkId)).withRel("animals").withType("GET"));

        ResponseDto<Object> responseDtos = new ResponseDto<>();
        responseDtos.setStatus(HttpStatus.OK);
        responseDtos.setMessage("Fetched animals for park with ID " + parkId);
        responseDtos.setResponse(parkResponseDto);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{parkId}/animals")
    public ResponseEntity<?> getAnimalsByPark(@PathVariable Integer parkId) {

        Set<AnimalEntity> animals = parkService.getAnimalsByParkId(parkId);

        List<AnimalDto> animalDtos = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());

        animalDtos.forEach(animalResponseDto -> {
            animalResponseDto.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkId)).withSelfRel());
            animalResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withRel("previous"));
        });

        ResponseDto<Object> responseDtos = new ResponseDto<>(HttpStatus.OK, "Fetched animals for park with ID " + parkId, animalDtos);
        /*responseDtos.setStatus(HttpStatus.OK);
        responseDtos.setMessage("Fetched animals for park with ID " + parkId);
        responseDtos.setResponse(animalDtos);*/

        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{parkId}/counties")
    public ResponseEntity<?> getCountiesByPark(@PathVariable Integer parkId) {

        Set<CountyEntity> counties = parkService.getCountiesByParkId(parkId);

        List<CountyDto> countyDtos = counties.stream()
                .map(county -> modelMapper.map(county, CountyDto.class))
                .collect(Collectors.toList());

        ResponseDto<Object> responseDto = new ResponseDto<>(HttpStatus.OK, "Fetched counties for park with ID " + parkId, countyDtos);
        /*responseDtos.setStatus(HttpStatus.OK);
        responseDtos.setMessage("Fetched counties for park with ID " + parkId);
        responseDtos.setResponse(countyDtos);*/

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> addNewPark(@Valid @RequestBody CreateParkRequest createParkRequest) {

        if(parkService.parkExists(createParkRequest.getParkName())){
            throw new EntityExistsException("Park " + createParkRequest.getParkName() + " already exists");
        }

        ParkEntity newPark = parkService.createPark(createParkRequest);
        CreateParkResponse parkResponseDto = modelMapper.map(newPark, CreateParkResponse.class);

        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());


        ResponseDto<Object> responseDto = new ResponseDto<>(HttpStatus.CREATED, "New park created", parkResponseDto);

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{parkID}")
    public ResponseEntity<?> updatePark(@PathVariable Integer parkID, @Valid @RequestBody CreateParkRequest createParkRequest){

        ParkEntity newPark;

        if(!parkService.existsByID(parkID)){
            newPark = parkService.createPark(createParkRequest);
        }else{
            newPark = parkService.updatePark(parkID, createParkRequest);

        }

        System.out.println(newPark);

        CreateParkResponse parkResponseDto = modelMapper.map(newPark, CreateParkResponse.class);

        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());


        ResponseDto<Object> responseDto = new ResponseDto<>(HttpStatus.CREATED, "Park updated", parkResponseDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{parkID}")
    public ResponseEntity<?> deletePark(@PathVariable Integer parkID){

        if(!parkService.existsByID(parkID)){
            return ResponseEntity.notFound().build();
        }

        parkService.deleteById(parkID);

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, "Park deleted.", null));
    }

}
