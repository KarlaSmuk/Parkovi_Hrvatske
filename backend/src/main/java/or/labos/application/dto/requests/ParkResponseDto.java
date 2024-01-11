package or.labos.application.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonldType("https://schema.org/TouristDestination")
public class ParkResponseDto extends RepresentationModel<ParkResponseDto>{

    @JsonldId
    private Integer parkId;
    @JsonldProperty("https://schema.org/name")
    private String parkName;
    @JsonldProperty("https://schema.org/additionalType")
    private String typeOfParkName;
    private Integer yearOfFoundation;
    private Double area;
    private HighestPeakResponseDto peak;
    private List<CountyResponseDto> counties;
    @JsonldProperty("https://schema.org/includesAttraction")
    private String atraction;
    @JsonldProperty("https://schema.org/event")
    private String event;
    private List<AnimalResponseDto> animals;


}
