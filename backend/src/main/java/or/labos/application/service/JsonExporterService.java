package or.labos.application.service;

import or.labos.application.dto.ParkToFileDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JsonExporterService {

    String export(List<ParkToFileDto> parks);
}
