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
    private Double area;
    private String peakName;
    private Integer peakHeight;
    //private List<String> counties;
    private String county;
    private String atraction;
    private String event;
    //private List<Map<String, String>> animals;//name and species
    private String animal;
    private String species;

    public ParkDto(String parkName, String typeOfPark, Integer yearOfFoundation, Double area, String county, String atraction, String event, String animal, String species) {
        this.parkName = parkName;
        this.typeOfPark = typeOfPark;
        this.yearOfFoundation = yearOfFoundation;
        this.area = area;
        this.county = county;
        this.atraction = atraction;
        this.event = event;
        this.animal = animal;
        this.species = species;
    }

}
