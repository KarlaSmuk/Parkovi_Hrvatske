package or.labos.application.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import or.labos.application.dto.ParkToFileDto;
import or.labos.application.service.JsonExporterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonExporterServiceImpl implements JsonExporterService {
    @Override
    public String export(List<ParkToFileDto> parks) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(parks);
    }
}
