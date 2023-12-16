package or.labos.application.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighestPeakDto extends  RepresentationModel<HighestPeakDto> {

    private String peakName;
    private Integer peakHeight;

}
