package or.labos.application.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighestPeakResponse extends RepresentationModel<HighestPeakResponse> {

    private Integer peakID;
    private String peakName;
    private Integer peakHeight;
}
