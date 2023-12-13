package or.labos.application.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighestPeakResponse {

    private Integer peakID;
    private String peakName;
    private Integer peakHeight;
}
