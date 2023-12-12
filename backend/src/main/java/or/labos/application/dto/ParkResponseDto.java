package or.labos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String typeOfPark;
    private Integer yearOfFoundation;
    private Double area;
    private HighestPeakDto peak;
    private String atraction;
    private String event;
    //private List<Link> links;//animals i counties
}
