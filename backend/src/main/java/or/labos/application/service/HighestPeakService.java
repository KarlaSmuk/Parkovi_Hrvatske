package or.labos.application.service;


import or.labos.application.entity.HighestPeakEntity;

import java.util.List;
import java.util.Optional;

public interface HighestPeakService {

    List<HighestPeakEntity> listAll();

    Optional<HighestPeakEntity> findByPeakID(Integer peakID);

}
