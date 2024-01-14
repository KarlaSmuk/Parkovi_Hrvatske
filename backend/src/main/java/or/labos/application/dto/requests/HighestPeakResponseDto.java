package or.labos.application.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighestPeakResponseDto extends RepresentationModel<HighestPeakResponseDto> {

    private Integer peakID;
    private String peakName;
    private Integer peakHeight;
}
