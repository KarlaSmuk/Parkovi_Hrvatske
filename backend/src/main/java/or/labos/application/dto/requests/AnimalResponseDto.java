package or.labos.application.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalResponseDto {

    private Integer animalID;
    private String animalName;
    private String speciesOfAnimal;
}
