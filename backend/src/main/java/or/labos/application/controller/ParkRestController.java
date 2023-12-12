package or.labos.application.controller;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

        if (!parkOptional.isPresent()) {
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

        ResponseDto<Object> responseDtos = new ResponseDto<>();
        responseDtos.setStatus(HttpStatus.OK);
        responseDtos.setMessage("Fetched animals for park with ID " + parkId);
        responseDtos.setResponse(animalDtos);

        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/{parkId}/counties")
    public ResponseEntity<?> getCountiesByPark(@PathVariable Integer parkId) {

        Set<CountyEntity> counties = parkService.getCountiesByParkId(parkId);

        List<CountyDto> countyDtos = counties.stream()
                .map(county -> modelMapper.map(county, CountyDto.class))
                .collect(Collectors.toList());

        ResponseDto<Object> responseDtos = new ResponseDto<>();
        responseDtos.setStatus(HttpStatus.OK);
        responseDtos.setMessage("Fetched counties for park with ID " + parkId);
        responseDtos.setResponse(countyDtos);

        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<CreateParkResponse> addNewPark(@Valid @RequestBody CreateParkRequest createParkRequest) {

        if(parkService.parkExists(createParkRequest.getParkName())){
            throw new EntityExistsException("Park " + createParkRequest.getParkName() + " already exists");
        }

        ParkEntity newPark = parkService.createPark(createParkRequest);
        CreateParkResponse parkResponseDto = modelMapper.map(newPark, CreateParkResponse.class);
        // Add HATEOAS links...
        return new ResponseEntity<>(parkResponseDto, HttpStatus.CREATED);
    }

}
