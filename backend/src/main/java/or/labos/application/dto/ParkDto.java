package or.labos.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkDto {

    private String parkName;
    private String typeOfPark;
    private Integer yearOfFoundation;
    private String peakName;
    private Integer peakHeight;
    private List<String> counties;
    private String atraction;
    private String event;
    private List<Map<String, String>> animals;//name and species

    public ParkDto(String parkName, String typeOfPark, Integer yearOfFoundation, List<String> counties, String atraction, String event, List<Map<String, String>> animals) {
        this.parkName = parkName;
        this.typeOfPark = typeOfPark;
        this.yearOfFoundation = yearOfFoundation;
        this.counties = counties;
        this.atraction = atraction;
        this.event = event;
        this.animals = animals;
    }




}
