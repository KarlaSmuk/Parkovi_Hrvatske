package or.labos.application.controller;

import or.labos.application.dto.AnimalDto;
import or.labos.application.dto.ResponseDto;
import or.labos.application.entity.AnimalEntity;
import or.labos.application.service.AnimalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("")
    public ResponseEntity<?> listAllParks() {

        List<AnimalEntity> animals = animalService.listAll();

        if(animals.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<AnimalDto> animalDtos = animals.stream()
                .map(park -> modelMapper.map(park, AnimalDto.class))
                .toList();

        ResponseDto<Object> responseDtos = new ResponseDto<>();
        responseDtos.setStatus(HttpStatus.OK);
        responseDtos.setMessage("Fetched all animals");
        responseDtos.setResponse(animalDtos);

        return ResponseEntity.ok(responseDtos);
    }
}
