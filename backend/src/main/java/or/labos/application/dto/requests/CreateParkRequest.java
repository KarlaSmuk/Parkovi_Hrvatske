package or.labos.application.dto.requests;

import lombok.*;
import or.labos.application.dto.AnimalDto;
import or.labos.application.dto.CountyDto;
import or.labos.application.dto.HighestPeakDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateParkRequest {

    private String parkName;
    private String typeOfParkName;
    private Integer yearOfFoundation;
    private Double area;
    private HighestPeakDto peak;
    private List<CountyDto> counties;
    private String atraction;
    private String event;
    private List<AnimalDto> animals;
}
