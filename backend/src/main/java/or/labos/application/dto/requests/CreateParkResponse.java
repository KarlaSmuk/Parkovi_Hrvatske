package or.labos.application.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import or.labos.application.dto.CountyDto;
import or.labos.application.dto.HighestPeakDto;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateParkResponse {

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
