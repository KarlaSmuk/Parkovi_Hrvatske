package or.labos.application.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkResponseDto extends RepresentationModel<ParkResponseDto>{

    private Integer parkId;
    private String parkName;
    private String typeOfParkName;
    private Integer yearOfFoundation;
    private Double area;
    private HighestPeakResponseDto peak;
    private List<CountyResponseDto> counties;
    private String atraction;
    private String event;
    private List<AnimalResponseDto> animals;

}
