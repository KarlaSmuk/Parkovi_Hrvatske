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

    private String parkName;
    private String typeOfParkName;
    private Integer yearOfFoundation;
    private Double area;
    private HighestPeakDto peak;
    private List<CountyDto> counties;
    private String atraction;
    private String event;
    private List<AnimalRequestResponse> animals;
}
