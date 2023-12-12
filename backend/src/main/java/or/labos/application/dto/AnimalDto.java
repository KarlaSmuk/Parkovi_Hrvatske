package or.labos.application.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto extends RepresentationModel<AnimalDto> {

    //private Integer animalID;
    private String animalName;
    private String speciesOfAnimal;

}
