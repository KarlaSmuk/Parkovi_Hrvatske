package or.labos.application.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountyResponseDto {

    private Integer countyID;
    private String countyName;
}
