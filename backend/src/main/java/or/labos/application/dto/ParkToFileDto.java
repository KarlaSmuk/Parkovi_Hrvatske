package or.labos.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkToFileDto {

    private String park;
    private String tip;
    private Integer godina_osnutka;
    private Double povrsina;
    private Map<String, Object> vrh;
    private Set<String> zupanija;
    private String atrakcija;
    private String dogadjaj_godine;
    private Set<Map<String, String>> zivotinje;

}
