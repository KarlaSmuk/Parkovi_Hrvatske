package or.labos.application.service.impl;

import or.labos.application.entity.HighestPeakEntity;
import or.labos.application.repository.HighestPeakRepository;
import or.labos.application.service.HighestPeakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HighestPeakServiceImpl implements HighestPeakService {

    @Autowired
    private HighestPeakRepository highestPeakRepository;

    @Override
    public List<HighestPeakEntity> listAll() {
        return highestPeakRepository.findAll();
    }

    @Override
    public Optional<HighestPeakEntity> findByPeakID(Integer peakID) {
        return highestPeakRepository.findById(peakID);
    }

}
