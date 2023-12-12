package or.labos.application.service;

import or.labos.application.dto.ParkToFileDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface JsonExporterService {

    String export(List<ParkToFileDto> parks) throws IOException;
}
