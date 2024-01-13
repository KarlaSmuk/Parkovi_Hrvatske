package or.labos.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ioinformarics.oss.jackson.module.jsonld.JsonldModule;
import ioinformarics.oss.jackson.module.jsonld.JsonldResource;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/parks")
public class ParkRestController {

    @Autowired
    private ParkService parkService;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> listAllParks() {
        List<ParkEntity> parks = parkService.listAll();

        if(parks.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<JsonldResource> parkResponseDtos = parks.stream()
                .map(park -> JsonldResource.Builder.create().build(modelMapper.map(park, ParkResponseDto.class)))
                .collect(Collectors.toList());

        /*parkResponseDtos.forEach(parkResponseDto -> {
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkResponseDto.getParkId())).withRel("counties").withType("GET"));
            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkResponseDto.getParkId())).withRel("animals").withType("GET"));

        });*/


        Response<Object> responseDtos = new Response<>(HttpStatus.OK, "Fetched all parks", parkResponseDtos);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping(value = "/{parkId}", produces="application/json")
    public ResponseEntity<?> getParkByID(@PathVariable Integer parkId) throws JsonProcessingException {

        Optional<ParkEntity> parkOptional = Optional.ofNullable(parkService.getParkByID(parkId));

        if (parkOptional.isEmpty()) {
            throw new EntityNotFoundException("Park not found");
        }

        ParkEntity park = parkOptional.get();

        ParkResponseDto parkResponseDto = modelMapper.map(park, ParkResponseDto.class);

        /*parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withSelfRel());
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkId)).withRel("counties").withType("GET"));
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkId)).withRel("animals").withType("GET"));
        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getPeakByParkID(parkId)).withRel("peak").withType("GET"));
        */

        JsonldResource jsonldResource = JsonldResource.Builder.create().build(parkResponseDto);
        Response<Object> responseDtos = new Response<>(HttpStatus.OK, "Fetched park with ID " + parkId, jsonldResource);

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping(value = "/{parkId}/animals", produces = "application/json")
    public ResponseEntity<?> getAnimalsByPark(@PathVariable Integer parkId) {

        Set<AnimalEntity> animals = parkService.getAnimalsByParkId(parkId);

        List<AnimalResponseDto> animalDtos = animals.stream()
                .map(animal -> modelMapper.map(animal, AnimalResponseDto.class))
                .toList();


       /* CollectionModel<AnimalResponseDto> animalCollection = CollectionModel.of(animalDtos);
        animalCollection.add(linkTo(methodOn(ParkRestController.class).getAnimalsByPark(parkId)).withSelfRel());
        animalCollection.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withRel("previous"));
*/
        Response<Object> responseDtos = new Response<>(HttpStatus.OK, "Fetched animals for park with ID " + parkId, animalDtos);


        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping(value = "/{parkId}/counties", produces = "application/json")
    public ResponseEntity<?> getCountiesByPark(@PathVariable Integer parkId) {

        Set<CountyEntity> counties = parkService.getCountiesByParkId(parkId);

        List<CountyResponseDto> countyDtos = counties.stream()
                .map(county -> modelMapper.map(county, CountyResponseDto.class))
                .collect(Collectors.toList());

        Response<Object> responseDto = new Response<>(HttpStatus.OK, "Fetched counties for park with ID " + parkId, countyDtos);

        /*responseDto.add(linkTo(methodOn(ParkRestController.class).getCountiesByPark(parkId)).withSelfRel());
        responseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withRel("previous"));
        */

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/{parkId}/peak", produces = "application/json")
    public ResponseEntity<?> getPeakByParkID(@PathVariable Integer parkId) {

        if(!parkService.existsByID(parkId)){
            throw new EntityNotFoundException("Park not found");
        }

        HighestPeakEntity highestPeakEntity = parkService.getPeakByID(parkId);

        if(highestPeakEntity == null){
            throw new EntityNotFoundException("Peak not found");
        }

        HighestPeakResponseDto highestPeakDto = modelMapper.map(highestPeakEntity, HighestPeakResponseDto.class);

        /*
        highestPeakDto.add(linkTo(methodOn(ParkRestController.class).getPeakByParkID(parkId)).withSelfRel());
        highestPeakDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkId)).withRel("previous"));
        */

        Response<Object> responseDto = new Response<>(HttpStatus.OK, "Fetched peak for park with ID " + parkId, highestPeakDto);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<?> addNewPark(@RequestBody @Valid CreateParkRequest createParkRequest) throws JsonProcessingException {


            if (parkService.parkExists(createParkRequest.getParkName())) {
                throw new EntityExistsException("Park already exists");
            }
            ParkEntity newPark = parkService.createPark(new ParkEntity(), createParkRequest);

            ParkResponseDto parkResponseDto = modelMapper.map(newPark, ParkResponseDto.class);

            parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());

            Response<Object> responseDto = new Response<>(HttpStatus.CREATED, "New park created", parkResponseDto);

            return ResponseEntity.ok(responseDto);
    }

    @PutMapping(value = "/{parkID}", produces = "application/json")
    public ResponseEntity<?> updatePark(@PathVariable Integer parkID, @Valid @RequestBody CreateParkRequest createParkRequest) throws JsonProcessingException {

        ParkEntity newPark;

        if(!parkService.existsByID(parkID)){
            throw new EntityNotFoundException("Park not found");
        }

        newPark = parkService.createPark(parkService.getParkByID(parkID), createParkRequest);

        ParkResponseDto parkResponseDto = modelMapper.map(newPark, ParkResponseDto.class);

        parkResponseDto.add(linkTo(methodOn(ParkRestController.class).getParkByID(parkResponseDto.getParkId())).withSelfRel());

        Response<Object> responseDto = new Response<>(HttpStatus.CREATED, "Park updated", parkResponseDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping(value = "/{parkID}", produces = "application/json")
    public ResponseEntity<?> deletePark(@PathVariable Integer parkID){

        if(!parkService.existsByID(parkID)){
            throw new EntityNotFoundException("Park not found");
        }

        parkService.deleteById(parkID);

        return ResponseEntity.ok(new Response<>(HttpStatus.OK, "Park deleted", null));
    }

}
