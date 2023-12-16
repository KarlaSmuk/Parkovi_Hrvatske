package or.labos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import or.labos.application.dto.requests.AnimalResponse;
import or.labos.application.dto.requests.CountyResponse;
import or.labos.application.dto.requests.HighestPeakResponse;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Map;

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
    private HighestPeakResponse peak;
    private List<CountyResponse> counties;
    private String atraction;
    private String event;
    private List<AnimalResponse> animals;

}
