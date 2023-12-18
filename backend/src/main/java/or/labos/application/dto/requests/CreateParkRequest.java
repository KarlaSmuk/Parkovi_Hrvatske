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

    @NotNull(message = "Park name cannot be null")
    private String parkName;

    @NotNull(message = "Type of park name cannot be null")
    private String typeOfParkName;

    @NotNull(message = "Year of foundation cannot be null")
    private Integer yearOfFoundation;

    @NotNull(message = "Area cannot be null")
    private Double area;

    @NotNull(message = "Peak cannot be null")
    private HighestPeakDto peak;

    @NotNull(message = "Counties cannot be null")
    private List<CountyDto> counties;

    @NotNull(message = "Atraction cannot be null")
    private String atraction;

    @NotNull(message = "Event cannot be null")
    private String event;

    @NotNull(message = "Animals cannot be null")
    private List<AnimalDto> animals;
}
