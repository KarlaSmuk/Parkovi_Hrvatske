package or.labos.application.dto.requests;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighestPeakResponseDto extends RepresentationModel<HighestPeakResponseDto> {

    @JsonldId
    private Integer peakID;
    private String peakName;
    private Integer peakHeight;
}
