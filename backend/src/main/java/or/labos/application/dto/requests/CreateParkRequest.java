package or.labos.application.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import or.labos.application.dto.AnimalDto;
import or.labos.application.dto.CountyDto;
import or.labos.application.dto.HighestPeakDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateParkRequest {

    @NotNull
    private String parkName;
    @NotNull
    private String typeOfParkName;
    @NotNull
    private Integer yearOfFoundation;
    @NotNull
    private Double area;
    @NotNull
    private HighestPeakDto peak;
    @NotNull
    private List<CountyDto> counties;
    @NotNull
    private String atraction;
    @NotNull
    private String event;
    @NotNull
    private List<AnimalDto> animals;
}
