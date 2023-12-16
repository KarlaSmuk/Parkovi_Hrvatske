package or.labos.application.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import or.labos.application.dto.requests.*;
import or.labos.application.entity.AnimalEntity;
import or.labos.application.entity.CountyEntity;
import or.labos.application.entity.HighestPeakEntity;
import or.labos.application.entity.ParkEntity;
import or.labos.application.service.ParkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

        List<ParkResponse> parkResponseDtos = parks.stream()
                .map(park -> modelMapper.map(park, ParkResponse.class))
                .collect(Collectors.toList());

        parkResponseDtos.forEach(parkResponseDto -> {
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkResponseDto.getParkId())).withRel("counties").withType("GET"));
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkResponseDto.getParkId())).withRel("animals").withType("GET"));

        });

        Response<Object> responseDtos = new Response<>(HttpStatus.OK, "Fetched all parks", parkResponseDtos);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{parkId}")
    public ResponseEntity<?> getParkByID(@PathVariable Integer parkId) {

        Optional<ParkEntity> parkOptional = Optional.ofNullable(parkService.getParkByID(parkId));

        if (parkOptional.isEmpty()) {
            throw new EntityNotFoundException("Park not found with ID: " + parkId);
        }

        ParkEntity park = parkOptional.get();

        ParkResponse parkResponseDto = modelMapper.map(park, ParkResponse.class);

        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withSelfRel());
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkId)).withRel("counties").withType("GET"));
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkId)).withRel("animals").withType("GET"));

        Response<Object> responseDtos = new Response<>(HttpStatus.OK, "Fetched park with ID " + parkId, parkResponseDto);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{parkId}/animals")
    public ResponseEntity<?> getAnimalsByPark(@PathVariable Integer parkId) {

        Set<AnimalEntity> animals = parkService.getAnimalsByParkId(parkId);

        List<AnimalResponse> animalDtos = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalResponse.class))
                .collect(Collectors.toList());


        Response<Object> responseDtos = new Response<>(HttpStatus.OK, "Fetched animals for park with ID " + parkId, animalDtos);

        responseDtos.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkId)).withSelfRel());
        responseDtos.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withRel("previous"));


        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{parkId}/counties")
    public ResponseEntity<?> getCountiesByPark(@PathVariable Integer parkId) {

        Set<CountyEntity> counties = parkService.getCountiesByParkId(parkId);

        List<CountyResponse> countyDtos = counties.stream()
                .map(county -> modelMapper.map(county, CountyResponse.class))
                .toList();


        Response<Object> responseDto = new Response<>(HttpStatus.OK, "Fetched counties for park with ID " + parkId, countyDtos);

        responseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkId)).withSelfRel());
        responseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withRel("previous"));

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{parkId}/peak")
    public ResponseEntity<?> getPeakByParkID(@PathVariable Integer parkId) {

        HighestPeakEntity highestPeakEntity = parkService.getPeakByID(parkId);

        if(highestPeakEntity == null){
            throw new EntityNotFoundException("Park with ID " + parkId + " doesnt have peak");
        }

        HighestPeakResponse highestPeakDto = modelMapper.map(highestPeakEntity, HighestPeakResponse.class);

        highestPeakDto.add(linkTo(methodOn(ParkRestController.class).getPeakByParkID(parkId)).withSelfRel());
        highestPeakDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withRel("previous"));

        Response<Object> responseDto = new Response<>(HttpStatus.OK, "Fetched peak for park with ID " + parkId, highestPeakDto);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<?> addNewPark(@RequestBody @Validated(CreateParkRequest.class)  CreateParkRequest createParkRequest) {


            if (parkService.parkExists(createParkRequest.getParkName())) {
                throw new EntityExistsException("Park " + createParkRequest.getParkName() + " already exists");
            }
            ParkEntity newPark = parkService.createPark(new ParkEntity(), createParkRequest);

            ParkResponse parkResponseDto = modelMapper.map(newPark, ParkResponse.class);

            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());

            Response<Object> responseDto = new Response<>(HttpStatus.CREATED, "New park created", parkResponseDto);

            return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{parkID}")
    public ResponseEntity<?> updatePark(@PathVariable Integer parkID, @Valid @RequestBody CreateParkRequest createParkRequest){

        ParkEntity newPark;

        if(!parkService.existsByID(parkID)){
            throw new EntityNotFoundException("Park with ID " + parkID + " doesnt exists");
        }

        newPark = parkService.createPark(parkService.getParkByID(parkID), createParkRequest);

        ParkResponse parkResponseDto = modelMapper.map(newPark, ParkResponse.class);

        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());

        Response<Object> responseDto = new Response<>(HttpStatus.CREATED, "Park updated", parkResponseDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{parkID}")
    public ResponseEntity<?> deletePark(@PathVariable Integer parkID){

        if(!parkService.existsByID(parkID)){
            throw new EntityNotFoundException("Park with ID " + parkID + " doesnt exists");
        }

        parkService.deleteById(parkID);

        return ResponseEntity.ok(new Response<>(HttpStatus.OK, "Park deleted.", null));
    }

}
